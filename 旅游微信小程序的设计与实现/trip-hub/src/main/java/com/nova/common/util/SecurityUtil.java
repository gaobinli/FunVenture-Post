package com.nova.common.util;

import com.nova.domain.model.LoginUserEntity;
import com.nova.common.exception.AppException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityUtil {

    /**
     * 获取用户姓名
     **/
    public static String getUserName() {
        try {
            LoginUserEntity loginUserEntity = (LoginUserEntity) getAuthentication().getPrincipal();
            return loginUserEntity.getSysUser().getName();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 获取用户
     **/
    public static LoginUserEntity getLoginUser() {
        try {
            return (LoginUserEntity) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new AppException("获取用户信息异常");
        }
    }

    /**
     * 密码比较
     * @param rawPassword
     * @param encodePassword
     * @return
     */
    public static boolean matches(String rawPassword, String encodePassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodePassword);
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
