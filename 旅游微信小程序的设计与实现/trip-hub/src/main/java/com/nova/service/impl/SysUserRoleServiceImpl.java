package com.nova.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.ext.BasePService;
import com.baomidou.mybatisplus.ext.IPService;
import com.baomidou.mybatisplus.ext.Pager;
import com.baomidou.mybatisplus.ext.QueryParamUtils;
import com.nova.domain.dto.req.SysUserRoleReqDto;
import com.nova.domain.dto.res.SysUserRoleResDto;
import com.nova.mapper.SysUserRoleMapper;
import com.nova.service.SysUserRoleService;
import com.nova.common.exception.AppException;
import com.nova.common.exception.ErrorCode;
import com.google.common.base.CaseFormat;
import com.nova.common.convertor.SysUserRoleReqDtoConvertor;
import com.nova.common.convertor.SysUserRoleResDtoConvertor;
import com.nova.domain.po.SysUserRolePo;
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
@Service("sysUserRoleAppService")
@Transactional(rollbackFor = Exception.class)
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    private SysUserRoleReqDtoConvertor sysUserRoleReqDtoConvertor;

    @Autowired
    private SysUserRoleResDtoConvertor sysUserRoleResDtoConvertor;

    private IPService<SysUserRolePo> sysUserRolePoService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public SysUserRoleServiceImpl(SysUserRoleMapper sysUserRoleMapper) {
        this.sysUserRolePoService = new BasePService(sysUserRoleMapper);
    }


    @Override
    public SysUserRoleResDto save(SysUserRoleReqDto reqDto) {
        SysUserRolePo po = sysUserRoleReqDtoConvertor.dto2Po(reqDto);
        sysUserRolePoService.save(po);
        SysUserRoleResDto resDto = sysUserRoleResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean deleteByIds(String ids) {
        try {
        List<Long> idLongList =
        Arrays.asList(ids.split(",")).stream().map(s -> NumberUtils.createLong(s.trim())).collect(Collectors.toList());
        return sysUserRolePoService.removeByIds(idLongList);
        } catch (Exception e) {
        throw new AppException("参数错误：" + ids, ErrorCode.sys("417"), e);
        }
     }

    @Override
    public Pager<SysUserRoleResDto> doPager(Map<String, Object> params) {
        Pager<SysUserRolePo> poPager = sysUserRolePoService.queryPage(params, SysUserRolePo.class);
        Pager<SysUserRoleResDto> resDtoPager = sysUserRoleResDtoConvertor.convertPoPager2ResDtoPager(poPager);
        return resDtoPager;
    }

    @Override
    public SysUserRoleResDto selectOne(Long id) {
        SysUserRolePo po = sysUserRolePoService.getById(id);
        SysUserRoleResDto resDto = sysUserRoleResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public SysUserRoleResDto selectOne(Map<String, Object> params) {
        QueryWrapper queryWrapper = QueryParamUtils.queryWrapper4eq(SysUserRolePo::new, params);
        SysUserRolePo po = sysUserRolePoService.getOne(queryWrapper);
        SysUserRoleResDto resDto = sysUserRoleResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean updateProps(Long id, Map<String, Object> params) {
        UpdateWrapper<SysUserRolePo> updateWrapper = QueryParamUtils.updateWrapper4Map(SysUserRolePo::new, id, params);
        return sysUserRolePoService.update(new SysUserRolePo(), updateWrapper);
        }

    @Override
    public boolean updateProps(Long id, SysUserRoleReqDto reqDto) {
        SysUserRolePo po = sysUserRoleReqDtoConvertor.dto2Po(reqDto);
        po.setId(id);
        return sysUserRolePoService.updateById(po);
    }


    @Override
    public boolean updateAllProps(Long id, SysUserRoleReqDto reqDto) {
        UpdateWrapper<SysUserRolePo> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", id);
        Field[] fields = SysUserRoleReqDto.class.getDeclaredFields();
        Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).forEach(field -> {
        try {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), SysUserRoleReqDto.class);
        Method getMethod = propertyDescriptor.getReadMethod();
        String fileNameCamel = getMethod.getName().substring(3);
        String fileNameUnderline = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileNameCamel);
        updateWrapper.set(fileNameUnderline, getMethod.invoke(reqDto));
        } catch (Exception ex) {
        log.warn("属性不存在get方法："+field.getName(),ex);
        }
        });
        return sysUserRolePoService.update(new SysUserRolePo(), updateWrapper);
    }

}