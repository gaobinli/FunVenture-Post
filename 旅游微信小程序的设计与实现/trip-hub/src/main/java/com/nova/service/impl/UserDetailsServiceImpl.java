package com.nova.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nova.domain.model.LoginUserEntity;
import com.nova.domain.model.SysUserEntity;
import com.nova.mapper.SysUserMapper;
import com.nova.mapper.UserMapper;
import com.nova.service.SysMenuService;
import com.nova.service.SysPermissionService;
import com.nova.common.util.ConditionUtil;
import com.nova.domain.po.UserPo;
import com.nova.common.convertor.SysUserResDtoConvertor;
import com.nova.domain.po.SysUserPo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Data
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysMenuService sysMenuService;
    private final SysUserMapper sysUserMapper;
    private final UserMapper userMapper;
    private final SysPermissionService sysPermissionService;

    @Autowired
    private SysUserResDtoConvertor sysUserResDtoConvertor;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUserEntity sysUserEntity = new SysUserEntity();;
        if (username.contains("admin")) {
            username = username.split("-")[1];
            SysUserPo sysUserPo = sysUserMapper.selectOne(Wrappers.<SysUserPo>lambdaQuery().eq(SysUserPo::getCode, username));
            sysUserEntity = sysUserResDtoConvertor.po2Entity(sysUserPo);
            ConditionUtil.checkNotNull(sysUserEntity, "亲！登录用户：" + username + " 不存在");
        } else {
            username = username.split("-")[1];
            UserPo userPo = userMapper.selectOne(Wrappers.<UserPo>lambdaQuery().eq(UserPo::getPhone, username));
            ConditionUtil.checkNotNull(userPo, "亲！登录用户：" + username + " 不存在");
            ConditionUtil.checkArgument(userPo.getEnable(), "账号已被禁用");
            sysUserEntity.setName(userPo.getNickName());
            sysUserEntity.setId(userPo.getId().intValue());
            sysUserEntity.setPassword(userPo.getPassword());
            sysUserEntity.setPhone(userPo.getPhone());
            sysUserEntity.setEnable(userPo.getEnable());
        }

        ConditionUtil.checkNotNull(sysUserEntity, "亲！登录用户：" + username + " 不存在");
        ConditionUtil.checkArgument(sysUserEntity.getEnable(), "亲！账号已被禁用");

        return createLoginUser(sysUserEntity);
    }

    public UserDetails createLoginUser(SysUserEntity sysUserEntity) {
        Set<String> perms = new HashSet<>();
        perms.add("*:*:*");
//        return new LoginUserEntity(sysUserEntity, sysPermissionService.getMenuPermission(sysUserEntity.getCode()));
        return new LoginUserEntity(sysUserEntity, perms);
    }
}
