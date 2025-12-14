package com.nova.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.ext.BasePService;
import com.baomidou.mybatisplus.ext.IPService;
import com.baomidou.mybatisplus.ext.Pager;
import com.baomidou.mybatisplus.ext.QueryParamUtils;
import com.nova.domain.dto.res.SysMenuResDto;
import com.nova.domain.model.LoginUserEntity;
import com.nova.domain.model.SysMenuEntity;
import com.nova.domain.model.SysUserEntity;
import com.nova.mapper.SysMenuMapper;
import com.nova.mapper.SysRoleMenuMapper;
import com.nova.mapper.SysUserRoleMapper;
import com.nova.service.SysMenuService;
import com.nova.common.exception.AppException;
import com.nova.common.exception.ErrorCode;
import com.nova.common.util.ConditionUtil;
import com.nova.common.util.MapUtil;
import com.nova.common.util.SecurityUtil;
import com.google.common.base.CaseFormat;
import com.nova.common.convertor.SysMenuReqDtoConvertor;
import com.nova.common.convertor.SysMenuResDtoConvertor;
import com.nova.domain.dto.req.SysMenuReqDto;
import com.nova.domain.po.SysMenuPo;
import com.nova.domain.po.SysRoleMenuPo;
import com.nova.domain.po.SysUserRolePo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Slf4j
@Service("sysMenuAppService")
@Transactional(rollbackFor = Exception.class)
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuReqDtoConvertor sysMenuReqDtoConvertor;

    @Autowired
    private SysMenuResDtoConvertor sysMenuResDtoConvertor;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    private IPService<SysMenuPo> sysMenuPoService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public SysMenuServiceImpl(SysMenuMapper sysMenuMapper) {
        this.sysMenuPoService = new BasePService(sysMenuMapper);
    }


    @Override
    public SysMenuResDto save(SysMenuReqDto reqDto) {
        checkCodeRepeat(reqDto.getCode());
        SysMenuPo po = sysMenuReqDtoConvertor.dto2Po(reqDto);
        sysMenuPoService.save(po);
        SysMenuResDto resDto = sysMenuResDtoConvertor.po2Dto(po);
        return resDto;
    }

    public void checkCodeRepeat(String code) {
        List<SysMenuPo> list = sysMenuPoService.getList(new MapUtil().put("qp-code-eq", code), SysMenuPo.class);
        ConditionUtil.validCollectionIsEmpty(list, "菜单编码重复");
    }

    @Override
    public boolean deleteByIds(String ids) {
        try {
        List<Long> idLongList =
        Arrays.asList(ids.split(",")).stream().map(s -> NumberUtils.createLong(s.trim())).collect(Collectors.toList());
        return sysMenuPoService.removeByIds(idLongList);
        } catch (Exception e) {
        throw new AppException("参数错误：" + ids, ErrorCode.sys("417"), e);
        }
     }

    @Override
    public Pager<SysMenuResDto> doPager(Map<String, Object> params) {
        Pager<SysMenuPo> poPager = sysMenuPoService.queryPage(params, SysMenuPo.class);
        Pager<SysMenuResDto> resDtoPager = sysMenuResDtoConvertor.convertPoPager2ResDtoPager(poPager);
        return resDtoPager;
    }

    @Override
    public SysMenuResDto selectOne(Long id) {
        SysMenuPo po = sysMenuPoService.getById(id);
        SysMenuResDto resDto = sysMenuResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public SysMenuResDto selectOne(Map<String, Object> params) {
        QueryWrapper queryWrapper = QueryParamUtils.queryWrapper4eq(SysMenuPo::new, params);
        SysMenuPo po = sysMenuPoService.getOne(queryWrapper);
        SysMenuResDto resDto = sysMenuResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean updateProps(Long id, Map<String, Object> params) {
        UpdateWrapper<SysMenuPo> updateWrapper = QueryParamUtils.updateWrapper4Map(SysMenuPo::new, id, params);
        return sysMenuPoService.update(new SysMenuPo(), updateWrapper);
        }

    @Override
    public boolean updateProps(Long id, SysMenuReqDto reqDto) {
        SysMenuPo po = sysMenuReqDtoConvertor.dto2Po(reqDto);
        po.setId(id);
        return sysMenuPoService.updateById(po);
    }


    @Override
    public boolean updateAllProps(Long id, SysMenuReqDto reqDto) {
        UpdateWrapper<SysMenuPo> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", id);
        Field[] fields = SysMenuReqDto.class.getDeclaredFields();
        Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).forEach(field -> {
        try {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), SysMenuReqDto.class);
        Method getMethod = propertyDescriptor.getReadMethod();
        String fileNameCamel = getMethod.getName().substring(3);
        String fileNameUnderline = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileNameCamel);
        updateWrapper.set(fileNameUnderline, getMethod.invoke(reqDto));
        } catch (Exception ex) {
        log.warn("属性不存在get方法："+field.getName(),ex);
        }
        });
        return sysMenuPoService.update(new SysMenuPo(), updateWrapper);
    }

    @Override
    public List<SysMenuResDto> getMenuTree() {
        List<SysMenuPo> menuPos = sysMenuPoService.getList(new MapUtil(), SysMenuPo.class);
        if (CollectionUtils.isEmpty(menuPos)) {
            return null;
        }

        return dealMenuTree(menuPos);
    }

    @Override
    public List<SysMenuResDto> getPermissonMenuTree() {
        LoginUserEntity loginUser = SecurityUtil.getLoginUser();
        SysUserEntity sysUser = loginUser.getSysUser();
        String code = sysUser.getCode();
        if (code.equals("admin")) {
            return getMenuTree();
        } else {
            List<SysUserRolePo> sysUserRolePos = sysUserRoleMapper.selectList(Wrappers.<SysUserRolePo>lambdaQuery()
                    .eq(SysUserRolePo::getUserCode, code));
            if (CollectionUtils.isEmpty(sysUserRolePos)) {
                return null;
            }
            List<String> roleCodes = sysUserRolePos.stream()
                    .map(SysUserRolePo::getRoleCode)
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(roleCodes)) {
                return null;
            }
            List<SysRoleMenuPo> sysRoleMenuPos = sysRoleMenuMapper.selectList(Wrappers.<SysRoleMenuPo>lambdaQuery()
                    .in(SysRoleMenuPo::getRoleCode, roleCodes));
            if (CollectionUtils.isEmpty(sysRoleMenuPos)) {
                return null;
            }
            String menuCodes = sysRoleMenuPos.stream()
                    .map(SysRoleMenuPo::getMenuCode)
                    .collect(Collectors.joining(","));
            List<SysMenuPo> menuList = sysMenuPoService
                    .getList(new MapUtil().put("qp-code-in", menuCodes), SysMenuPo.class);

            return dealMenuTree(menuList);
        }
    }

    @Override
    public List<SysMenuResDto> getRoleMenus(String roleCode) {
        List<SysRoleMenuPo> sysRoleMenuPos = sysRoleMenuMapper.selectList(Wrappers.<SysRoleMenuPo>lambdaQuery()
                .eq(SysRoleMenuPo::getRoleCode, roleCode));
        if (CollectionUtils.isEmpty(sysRoleMenuPos)) {
            return new ArrayList<>();
        }
        String menuCodes = sysRoleMenuPos.stream()
                .map(SysRoleMenuPo::getMenuCode)
                .collect(Collectors.joining(","));
        List<SysMenuPo> menuPos = sysMenuPoService.getList(new MapUtil().put("qp-code-in", menuCodes), SysMenuPo.class);
        return sysMenuResDtoConvertor.poList2DtoList(menuPos);
    }

    @Override
    public List<SysMenuResDto> getParentList() {
        List<SysMenuPo> menuPos = sysMenuMapper.selectList(Wrappers.<SysMenuPo>lambdaQuery()
                .isNull(SysMenuPo::getParentCode).or().eq(SysMenuPo::getParentCode, ""));
        return sysMenuResDtoConvertor.poList2DtoList(menuPos);
    }

    private List<SysMenuResDto> dealMenuTree(List<SysMenuPo> menuPos) {
        List<SysMenuEntity> sysMenuEntities = sysMenuResDtoConvertor.poList2EntityList(menuPos);
        List<SysMenuEntity> parentMenus = sysMenuEntities.stream()
                .filter(entity -> StringUtils.isEmpty(entity.getParentCode()))
                .sorted(Comparator.comparing(SysMenuEntity::getSort).reversed())
                .collect(Collectors.toList());
        List<SysMenuEntity> childrens = sysMenuEntities.stream()
                .filter(entity -> StringUtils.isNotEmpty(entity.getParentCode()))
                .sorted(Comparator.comparing(SysMenuEntity::getSort).reversed())
                .collect(Collectors.toList());
        parentMenus.stream().forEach(parent -> {
            List<SysMenuEntity> menuEntityList = new ArrayList<>();
            childrens.stream().forEach(children -> {
                if (parent.getCode().equals(children.getParentCode())) {
                    menuEntityList.add(children);
                }
            });
            parent.setHasChildren(false);
            if (!CollectionUtils.isEmpty(menuEntityList)) {
                parent.setHasChildren(true);
                parent.setChildrens(menuEntityList);
            }
        });

        return sysMenuResDtoConvertor.entityList2DtoList(parentMenus);
    }
}