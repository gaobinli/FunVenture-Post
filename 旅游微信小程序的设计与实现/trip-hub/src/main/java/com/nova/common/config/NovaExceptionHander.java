package com.nova.common.config;

import com.nova.common.exception.AppExceptionHandler;
import com.nova.domain.ResultDTO;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;

@RestControllerAdvice
public class NovaExceptionHander extends AppExceptionHandler {
    public NovaExceptionHander() {
        super("Nova");
    }

    @ExceptionHandler({ServletException.class})
    public ResultDTO servletException(ServletException e) {
        return ResultDTO.error("sys." + "Nova" + ".404", e.getMessage());
    }

    @ExceptionHandler({InternalAuthenticationServiceException.class})
    public ResultDTO internalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        return ResultDTO.error("sys." + "Nova" + ".500", e.getMessage());
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResultDTO badCredentialsException(BadCredentialsException e) {
        return ResultDTO.error("sys." + "Nova" + ".500", "亲！密码错误");
    }
}