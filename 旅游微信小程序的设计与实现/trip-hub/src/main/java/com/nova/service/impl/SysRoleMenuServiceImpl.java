package com.nova.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.ext.BasePService;
import com.baomidou.mybatisplus.ext.IPService;
import com.baomidou.mybatisplus.ext.Pager;
import com.baomidou.mybatisplus.ext.QueryParamUtils;
import com.nova.domain.dto.req.SysRoleMenuReqDto;
import com.nova.domain.dto.res.SysRoleMenuResDto;
import com.nova.mapper.SysMenuMapper;
import com.nova.mapper.SysRoleMenuMapper;
import com.nova.service.SysRoleMenuService;
import com.nova.common.exception.AppException;
import com.nova.common.exception.ErrorCode;
import com.google.common.base.CaseFormat;
import com.nova.common.convertor.SysRoleMenuReqDtoConvertor;
import com.nova.common.convertor.SysRoleMenuResDtoConvertor;
import com.nova.domain.po.SysRoleMenuPo;
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
@Service("sysRoleMenuAppService")
@Transactional(rollbackFor = Exception.class)
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuReqDtoConvertor sysRoleMenuReqDtoConvertor;

    @Autowired
    private SysRoleMenuResDtoConvertor sysRoleMenuResDtoConvertor;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    private IPService<SysRoleMenuPo> sysRoleMenuPoService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public SysRoleMenuServiceImpl(SysRoleMenuMapper sysRoleMenuMapper) {
        this.sysRoleMenuPoService = new BasePService(sysRoleMenuMapper);
    }


    @Override
    public SysRoleMenuResDto save(SysRoleMenuReqDto reqDto) {
        SysRoleMenuPo po = sysRoleMenuReqDtoConvertor.dto2Po(reqDto);
        sysRoleMenuPoService.save(po);
        SysRoleMenuResDto resDto = sysRoleMenuResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public void saveList(String roleCode, List<SysRoleMenuReqDto> reqDto) {
        reqDto.stream().forEach(dto -> {
            dto.setRoleCode(roleCode);
            dto.setMenuCode(dto.getCode());
        });
        sysRoleMenuPoService.remove(Wrappers.<SysRoleMenuPo>lambdaQuery().eq(SysRoleMenuPo::getRoleCode, roleCode));
        List<SysRoleMenuPo> sysRoleMenuPos = sysRoleMenuReqDtoConvertor.dtoList2PoList(reqDto);
        sysRoleMenuPoService.saveBatch(sysRoleMenuPos);
    }

    @Override
    public boolean deleteByIds(String ids) {
        try {
        List<Long> idLongList =
        Arrays.asList(ids.split(",")).stream().map(s -> NumberUtils.createLong(s.trim())).collect(Collectors.toList());
        return sysRoleMenuPoService.removeByIds(idLongList);
        } catch (Exception e) {
        throw new AppException("参数错误：" + ids, ErrorCode.sys("417"), e);
        }
     }

    @Override
    public Pager<SysRoleMenuResDto> doPager(Map<String, Object> params) {
        Pager<SysRoleMenuPo> poPager = sysRoleMenuPoService.queryPage(params, SysRoleMenuPo.class);
        Pager<SysRoleMenuResDto> resDtoPager = sysRoleMenuResDtoConvertor.convertPoPager2ResDtoPager(poPager);
        return resDtoPager;
    }

    @Override
    public SysRoleMenuResDto selectOne(Long id) {
        SysRoleMenuPo po = sysRoleMenuPoService.getById(id);
        SysRoleMenuResDto resDto = sysRoleMenuResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public SysRoleMenuResDto selectOne(Map<String, Object> params) {
        QueryWrapper queryWrapper = QueryParamUtils.queryWrapper4eq(SysRoleMenuPo::new, params);
        SysRoleMenuPo po = sysRoleMenuPoService.getOne(queryWrapper);
        SysRoleMenuResDto resDto = sysRoleMenuResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean updateProps(Long id, Map<String, Object> params) {
        UpdateWrapper<SysRoleMenuPo> updateWrapper = QueryParamUtils.updateWrapper4Map(SysRoleMenuPo::new, id, params);
        return sysRoleMenuPoService.update(new SysRoleMenuPo(), updateWrapper);
        }

    @Override
    public boolean updateProps(Long id, SysRoleMenuReqDto reqDto) {
        SysRoleMenuPo po = sysRoleMenuReqDtoConvertor.dto2Po(reqDto);
        po.setId(id);
        return sysRoleMenuPoService.updateById(po);
    }


    @Override
    public boolean updateAllProps(Long id, SysRoleMenuReqDto reqDto) {
        UpdateWrapper<SysRoleMenuPo> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", id);
        Field[] fields = SysRoleMenuReqDto.class.getDeclaredFields();
        Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).forEach(field -> {
        try {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), SysRoleMenuReqDto.class);
        Method getMethod = propertyDescriptor.getReadMethod();
        String fileNameCamel = getMethod.getName().substring(3);
        String fileNameUnderline = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileNameCamel);
        updateWrapper.set(fileNameUnderline, getMethod.invoke(reqDto));
        } catch (Exception ex) {
        log.warn("属性不存在get方法："+field.getName(),ex);
        }
        });
        return sysRoleMenuPoService.update(new SysRoleMenuPo(), updateWrapper);
    }

}