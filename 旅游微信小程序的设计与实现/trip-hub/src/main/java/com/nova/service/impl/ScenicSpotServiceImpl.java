package com.nova.service.impl;


import com.nova.domain.dto.res.ScenicSpotResDto;
import com.nova.mapper.ScenicSpotMapper;
import com.nova.service.ScenicSpotService;
import com.nova.common.convertor.ScenicSpotReqDtoConvertor;
import com.nova.common.convertor.ScenicSpotResDtoConvertor;
import com.nova.domain.dto.req.ScenicSpotReqDto;
import com.nova.domain.po.ScenicSpotPo;
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
@Service("scenicSpotAppService")
@Transactional(rollbackFor = Exception.class)
public class ScenicSpotServiceImpl implements ScenicSpotService {

    @Autowired
    private ScenicSpotReqDtoConvertor scenicSpotReqDtoConvertor;

    @Autowired
    private ScenicSpotResDtoConvertor scenicSpotResDtoConvertor;

    private IPService<ScenicSpotPo> scenicSpotPoService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public ScenicSpotServiceImpl(ScenicSpotMapper scenicSpotMapper) {
        this.scenicSpotPoService = new BasePService(scenicSpotMapper);
    }


    @Override
    public ScenicSpotResDto save(ScenicSpotReqDto reqDto) {
        ScenicSpotPo po = scenicSpotReqDtoConvertor.dto2Po(reqDto);
        scenicSpotPoService.save(po);
        ScenicSpotResDto resDto = scenicSpotResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public List<ScenicSpotResDto> getList() {
        List<ScenicSpotPo> list = scenicSpotPoService.list();
        return scenicSpotResDtoConvertor.poList2DtoList(list);
    }

    @Override
    public boolean deleteByIds(String ids) {
        try {
        List<Long> idLongList =
        Arrays.asList(ids.split(",")).stream().map(s -> NumberUtils.createLong(s.trim())).collect(Collectors.toList());
        return scenicSpotPoService.removeByIds(idLongList);
        } catch (Exception e) {
        throw new AppException("参数错误：" + ids, ErrorCode.sys("417"), e);
        }
     }

    @Override
    public Pager<ScenicSpotResDto> doPager(Map<String, Object> params) {
        Pager<ScenicSpotPo> poPager = scenicSpotPoService.queryPage(params, ScenicSpotPo.class);
        Pager<ScenicSpotResDto> resDtoPager = scenicSpotResDtoConvertor.convertPoPager2ResDtoPager(poPager);
        return resDtoPager;
    }

    @Override
    public ScenicSpotResDto selectOne(Long id) {
        ScenicSpotPo po = scenicSpotPoService.getById(id);
        ScenicSpotResDto resDto = scenicSpotResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public ScenicSpotResDto selectOne(Map<String, Object> params) {
        QueryWrapper queryWrapper = QueryParamUtils.queryWrapper4eq(ScenicSpotPo::new, params);
        ScenicSpotPo po = scenicSpotPoService.getOne(queryWrapper);
        ScenicSpotResDto resDto = scenicSpotResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean updateProps(Long id, Map<String, Object> params) {
        UpdateWrapper<ScenicSpotPo> updateWrapper = QueryParamUtils.updateWrapper4Map(ScenicSpotPo::new, id, params);
        return scenicSpotPoService.update(new ScenicSpotPo(), updateWrapper);
        }

    @Override
    public boolean updateProps(Long id, ScenicSpotReqDto reqDto) {
        ScenicSpotPo po = scenicSpotReqDtoConvertor.dto2Po(reqDto);
        po.setId(id);
        return scenicSpotPoService.updateById(po);
    }


    @Override
    public boolean updateAllProps(Long id, ScenicSpotReqDto reqDto) {
        UpdateWrapper<ScenicSpotPo> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", id);
        Field[] fields = ScenicSpotReqDto.class.getDeclaredFields();
        Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).forEach(field -> {
        try {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), ScenicSpotReqDto.class);
        Method getMethod = propertyDescriptor.getReadMethod();
        String fileNameCamel = getMethod.getName().substring(3);
        String fileNameUnderline = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileNameCamel);
        updateWrapper.set(fileNameUnderline, getMethod.invoke(reqDto));
        } catch (Exception ex) {
        log.warn("属性不存在get方法："+field.getName(),ex);
        }
        });
        return scenicSpotPoService.update(new ScenicSpotPo(), updateWrapper);
    }

}