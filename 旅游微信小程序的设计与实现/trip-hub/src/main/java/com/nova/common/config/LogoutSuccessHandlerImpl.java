package com.nova.common.config;

import com.alibaba.fastjson.JSON;
import com.nova.domain.model.LoginUserEntity;
import com.nova.service.TokenService;
import com.nova.common.util.ServletUtils;
import com.nova.domain.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication)
            throws IOException, ServletException {
        LoginUserEntity loginUser = tokenService.getLoginUser(request);
        if (loginUser != null) {
            tokenService.delLoginUser(loginUser.getToken());
        }
        ServletUtils.renderString(response, JSON.toJSONString(ResultDTO.ok("退出成功")));
    }
}
