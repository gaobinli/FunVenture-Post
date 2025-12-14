package com.nova.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nova.mapper.SysMenuMapper;
import com.nova.mapper.SysRoleMapper;
import com.nova.mapper.SysRoleMenuMapper;
import com.nova.mapper.SysUserRoleMapper;
import com.nova.service.SysPermissionService;
import com.nova.domain.po.SysMenuPo;
import com.nova.domain.po.SysRoleMenuPo;
import com.nova.domain.po.SysUserRolePo;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    private final SysRoleMapper sysRoleMapper;
    private final SysMenuMapper sysMenuMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 获取系统用户权限
     * @param code
     * @return
     */
    @Override
    public Set<String> getMenuPermission(String code) {
        Set<String> perms = new HashSet<>();
        // 管理员拥有所有权限
        if (code.equals("admin")) {
            perms.add("*:*:*");
            return perms;
        }

        List<SysUserRolePo> sysUserRolePos = sysUserRoleMapper
                .selectList(Wrappers.<SysUserRolePo>lambdaQuery().eq(SysUserRolePo::getUserCode, code));
        if (CollectionUtils.isEmpty(sysUserRolePos)) {
            return perms;
        }

        List<String> rolesCodes = sysUserRolePos.stream().map(SysUserRolePo::getRoleCode).distinct().collect(Collectors.toList());
        List<SysRoleMenuPo> sysRoleMenuPos = sysRoleMenuMapper
                .selectList(Wrappers.<SysRoleMenuPo>lambdaQuery().in(SysRoleMenuPo::getRoleCode, rolesCodes));
        if (CollectionUtils.isEmpty(sysRoleMenuPos)) {
            return perms;
        }

        List<String> menuCodes = sysRoleMenuPos.stream().map(SysRoleMenuPo::getMenuCode).distinct().collect(Collectors.toList());
        List<SysMenuPo> sysMenuPos = sysMenuMapper.selectList(Wrappers.<SysMenuPo>lambdaQuery().in(SysMenuPo::getCode, menuCodes));
        if (CollectionUtils.isEmpty(sysMenuPos)) {
            return perms;
        }

        return sysMenuPos.stream().map(SysMenuPo::getPath).collect(Collectors.toSet());
    }
}
