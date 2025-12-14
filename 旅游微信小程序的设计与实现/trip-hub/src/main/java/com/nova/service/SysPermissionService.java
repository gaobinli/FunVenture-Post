package com.nova.service;

import java.util.Set;

public interface SysPermissionService {

    /**
     * 获取系统用户权限
     * @param code
     * @return
     */
    Set<String> getMenuPermission(String code);
}
