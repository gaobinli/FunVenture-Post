package com.nova.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.ext.BasePService;
import com.baomidou.mybatisplus.ext.IPService;
import com.baomidou.mybatisplus.ext.Pager;
import com.baomidou.mybatisplus.ext.QueryParamUtils;
import com.nova.domain.dto.res.SysRoleResDto;
import com.nova.mapper.SysRoleMapper;
import com.nova.mapper.SysRoleMenuMapper;
import com.nova.mapper.SysUserMapper;
import com.nova.mapper.SysUserRoleMapper;
import com.nova.service.SysRoleService;
import com.nova.common.exception.AppException;
import com.nova.common.exception.ErrorCode;
import com.nova.common.util.ConditionUtil;
import com.nova.common.util.MapUtil;
import com.google.common.base.CaseFormat;
import com.nova.domain.po.SysRoleMenuPo;
import com.nova.common.convertor.SysRoleReqDtoConvertor;
import com.nova.common.convertor.SysRoleResDtoConvertor;
import com.nova.domain.dto.req.SysRoleReqDto;
import com.nova.domain.po.SysRolePo;
import com.nova.domain.po.SysUserPo;
import com.nova.domain.po.SysUserRolePo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Slf4j
@Service("sysRoleAppService")
@Transactional(rollbackFor = Exception.class)
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleReqDtoConvertor sysRoleReqDtoConvertor;

    @Autowired
    private SysRoleResDtoConvertor sysRoleResDtoConvertor;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    private IPService<SysRolePo> sysRolePoService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public SysRoleServiceImpl(SysRoleMapper sysRoleMapper) {
        this.sysRolePoService = new BasePService(sysRoleMapper);
    }


    @Override
    public SysRoleResDto save(SysRoleReqDto reqDto) {
        checkCodeRepeat(reqDto.getCode());
        SysRolePo po = sysRoleReqDtoConvertor.dto2Po(reqDto);
        sysRolePoService.save(po);
        SysRoleResDto resDto = sysRoleResDtoConvertor.po2Dto(po);
        List<String> menuCodes = reqDto.getMenuCodes();
        String roleCode = reqDto.getCode();
        sysRoleMenuMapper.delete(Wrappers.<SysRoleMenuPo>lambdaQuery().eq(SysRoleMenuPo::getRoleCode, roleCode));
        menuCodes.stream().forEach(menuCode -> {
            SysRoleMenuPo sysRoleMenuPo = new SysRoleMenuPo();
            sysRoleMenuPo.setRoleCode(roleCode);
            sysRoleMenuPo.setMenuCode(menuCode);
            sysRoleMenuMapper.insert(sysRoleMenuPo);
        });
        return resDto;
    }

    @Override
    public List<SysRoleResDto> selectAllRoleList() {
        List<SysRolePo> list = sysRolePoService.getList(new MapUtil(), SysRolePo.class);
        return sysRoleResDtoConvertor.poList2DtoList(list);
    }

    @Override
    public List<SysRoleResDto> getRoleList(Long userId) {
        List<SysRoleResDto> result = new ArrayList<>();
        SysUserPo sysUserPo = sysUserMapper.selectById(userId);
        List<SysUserRolePo> userRoleList = sysUserRoleMapper.selectList(Wrappers.<SysUserRolePo>lambdaQuery()
                .eq(SysUserRolePo::getUserCode, sysUserPo.getCode()));
        if (CollectionUtils.isEmpty(userRoleList)) {
            return result;
        }
        String roleCodes = userRoleList.stream()
                .map(SysUserRolePo::getRoleCode)
                .collect(Collectors.joining(","));
        List<SysRolePo> list = sysRolePoService.getList(new MapUtil().put("qp-code-in", roleCodes), SysRolePo.class);
        result = sysRoleResDtoConvertor.poList2DtoList(list);

        return result;
    }

    public void checkCodeRepeat(String code) {
        List<SysRolePo> list = sysRolePoService.getList(new MapUtil().put("qp-code-eq", code), SysRolePo.class);
        ConditionUtil.validCollectionIsEmpty(list, "角色编码不可重复");
    }

    @Override
    public boolean deleteByIds(String ids) {
        try {
        List<Long> idLongList =
        Arrays.asList(ids.split(",")).stream().map(s -> NumberUtils.createLong(s.trim())).collect(Collectors.toList());
        return sysRolePoService.removeByIds(idLongList);
        } catch (Exception e) {
        throw new AppException("参数错误：" + ids, ErrorCode.sys("417"), e);
        }
     }

    @Override
    public Pager<SysRoleResDto> doPager(Map<String, Object> params) {
        Pager<SysRolePo> poPager = sysRolePoService.queryPage(params, SysRolePo.class);
        Pager<SysRoleResDto> resDtoPager = sysRoleResDtoConvertor.convertPoPager2ResDtoPager(poPager);
        return resDtoPager;
    }

    @Override
    public SysRoleResDto selectOne(Long id) {
        SysRolePo po = sysRolePoService.getById(id);
        SysRoleResDto resDto = sysRoleResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public SysRoleResDto selectOne(Map<String, Object> params) {
        QueryWrapper queryWrapper = QueryParamUtils.queryWrapper4eq(SysRolePo::new, params);
        SysRolePo po = sysRolePoService.getOne(queryWrapper);
        SysRoleResDto resDto = sysRoleResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean updateProps(Long id, Map<String, Object> params) {
        UpdateWrapper<SysRolePo> updateWrapper = QueryParamUtils.updateWrapper4Map(SysRolePo::new, id, params);
        return sysRolePoService.update(new SysRolePo(), updateWrapper);
        }

    @Override
    public boolean updateProps(Long id, SysRoleReqDto reqDto) {
        SysRolePo po = sysRoleReqDtoConvertor.dto2Po(reqDto);
        po.setId(id);
        List<String> menuCodes = reqDto.getMenuCodes();
        String roleCode = reqDto.getCode();
        sysRoleMenuMapper.delete(Wrappers.<SysRoleMenuPo>lambdaQuery().eq(SysRoleMenuPo::getRoleCode, roleCode));
        menuCodes.stream().forEach(menuCode -> {
            SysRoleMenuPo sysRoleMenuPo = new SysRoleMenuPo();
            sysRoleMenuPo.setRoleCode(roleCode);
            sysRoleMenuPo.setMenuCode(menuCode);
            sysRoleMenuMapper.insert(sysRoleMenuPo);
        });
        return sysRolePoService.updateById(po);
    }


    @Override
    public boolean updateAllProps(Long id, SysRoleReqDto reqDto) {
        UpdateWrapper<SysRolePo> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", id);
        Field[] fields = SysRoleReqDto.class.getDeclaredFields();
        Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).forEach(field -> {
        try {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), SysRoleReqDto.class);
        Method getMethod = propertyDescriptor.getReadMethod();
        String fileNameCamel = getMethod.getName().substring(3);
        String fileNameUnderline = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileNameCamel);
        updateWrapper.set(fileNameUnderline, getMethod.invoke(reqDto));
        } catch (Exception ex) {
        log.warn("属性不存在get方法："+field.getName(),ex);
        }
        });
        return sysRolePoService.update(new SysRolePo(), updateWrapper);
    }

}