package com.nova.common.config;

import com.alibaba.fastjson.JSONObject;
import com.nova.common.util.ServletUtils;
import com.nova.domain.ResultDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException e) {
        ResultDTO error = ResultDTO.error(String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                "请求访问：" + request.getRequestURI() + "，认证失败，无法访问系统资源");
        ServletUtils.renderString(response, JSONObject.toJSONString(error));
    }

}