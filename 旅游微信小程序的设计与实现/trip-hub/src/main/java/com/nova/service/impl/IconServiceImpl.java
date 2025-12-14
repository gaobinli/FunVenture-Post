package com.nova.service.impl;


import com.nova.domain.dto.res.IconResDto;
import com.nova.mapper.IconMapper;
import com.nova.service.IconService;
import com.nova.common.convertor.IconReqDtoConvertor;
import com.nova.common.convertor.IconResDtoConvertor;
import com.nova.domain.dto.req.IconReqDto;
import com.nova.domain.po.IconPo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.base.CaseFormat;
import com.nova.common.exception.AppException;
import com.nova.common.exception.ErrorCode;
import com.baomidou.mybatisplus.ext.BasePService;
import com.baomidou.mybatisplus.ext.IPService;
import com.baomidou.mybatisplus.ext.Pager;
import com.baomidou.mybatisplus.ext.QueryParamUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Slf4j
@Service("iconAppService")
@Transactional(rollbackFor = Exception.class)
public class IconServiceImpl implements IconService {

    @Autowired
    private IconReqDtoConvertor iconReqDtoConvertor;

    @Autowired
    private IconResDtoConvertor iconResDtoConvertor;

    private IPService<IconPo> iconPoService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public IconServiceImpl(IconMapper iconMapper) {
        this.iconPoService = new BasePService(iconMapper);
    }


    @Override
    public IconResDto save(IconReqDto reqDto) {
        IconPo po = iconReqDtoConvertor.dto2Po(reqDto);
        iconPoService.save(po);
        IconResDto resDto = iconResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public List<IconResDto> getList() {
        List<IconPo> list = iconPoService.list(Wrappers.<IconPo>lambdaQuery()
                .orderByDesc(IconPo::getSort));
        return iconResDtoConvertor.poList2DtoList(list);
    }

    @Override
    public boolean deleteByIds(String ids) {
        try {
        List<Long> idLongList =
        Arrays.asList(ids.split(",")).stream().map(s -> NumberUtils.createLong(s.trim())).collect(Collectors.toList());
        return iconPoService.removeByIds(idLongList);
        } catch (Exception e) {
        throw new AppException("参数错误：" + ids, ErrorCode.sys("417"), e);
        }
     }

    @Override
    public Pager<IconResDto> doPager(Map<String, Object> params) {
        Pager<IconPo> poPager = iconPoService.queryPage(params, IconPo.class);
        Pager<IconResDto> resDtoPager = iconResDtoConvertor.convertPoPager2ResDtoPager(poPager);
        return resDtoPager;
    }

    @Override
    public IconResDto selectOne(Long id) {
        IconPo po = iconPoService.getById(id);
        IconResDto resDto = iconResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public IconResDto selectOne(Map<String, Object> params) {
        QueryWrapper queryWrapper = QueryParamUtils.queryWrapper4eq(IconPo::new, params);
        IconPo po = iconPoService.getOne(queryWrapper);
        IconResDto resDto = iconResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean updateProps(Long id, Map<String, Object> params) {
        UpdateWrapper<IconPo> updateWrapper = QueryParamUtils.updateWrapper4Map(IconPo::new, id, params);
        return iconPoService.update(new IconPo(), updateWrapper);
        }

    @Override
    public boolean updateProps(Long id, IconReqDto reqDto) {
        IconPo po = iconReqDtoConvertor.dto2Po(reqDto);
        po.setId(id);
        return iconPoService.updateById(po);
    }


    @Override
    public boolean updateAllProps(Long id, IconReqDto reqDto) {
        UpdateWrapper<IconPo> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", id);
        Field[] fields = IconReqDto.class.getDeclaredFields();
        Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).forEach(field -> {
        try {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), IconReqDto.class);
        Method getMethod = propertyDescriptor.getReadMethod();
        String fileNameCamel = getMethod.getName().substring(3);
        String fileNameUnderline = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileNameCamel);
        updateWrapper.set(fileNameUnderline, getMethod.invoke(reqDto));
        } catch (Exception ex) {
        log.warn("属性不存在get方法："+field.getName(),ex);
        }
        });
        return iconPoService.update(new IconPo(), updateWrapper);
    }

}