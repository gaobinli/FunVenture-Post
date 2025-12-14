package com.nova.common.exception;

import com.nova.domain.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

public class AppExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String appName;

    public AppExceptionHandler(String appName) {
        this.appName = appName;
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(AppException.class)
    public ResultDTO handleRRException(AppException e) {
        logger.error(e.getMessage(), e);
        return ResultDTO.error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResultDTO handlerNoFoundException(Exception e) {
        logger.error(e.getMessage(), e);
        return ResultDTO.error("sys." + this.appName + ".404", "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResultDTO handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return ResultDTO.error("biz." + this.appName + "." + "000", "数据库中已存在该记录");
    }

    @ExceptionHandler(Exception.class)
    public ResultDTO handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return ResultDTO.error("sys." + this.appName + ".500", "未知错误，请联系系统管理员");
    }
}
