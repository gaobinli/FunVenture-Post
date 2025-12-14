package com.nova.service;

import com.nova.domain.model.LoginUserEntity;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {

    LoginUserEntity getLoginUser(HttpServletRequest request);

    void verifyToken(LoginUserEntity loginUser);

    void refreshToken(LoginUserEntity loginUser);

    String createToken(LoginUserEntity loginUser);

    void delLoginUser(String token);
}
