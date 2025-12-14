package com.nova.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.nova.common.util.SecurityUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    Boolean deletedDefault = false;

    @Override
    public void insertFill(MetaObject metaObject) {

        Object createUser = getFieldValByName("createUser", metaObject);
        String userName = SecurityUtil.getUserName();
        if (createUser == null) {
            setFieldValByName("createUser", userName, metaObject);
        }

        Object createTime = getFieldValByName("createTime", metaObject);
        if (createTime == null) {
            setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }

        Object deleted = getFieldValByName("deleted", metaObject);
        if (deleted == null) {
            setFieldValByName("deleted", deletedDefault, metaObject);
        }

        Object modifyUser = getFieldValByName("modifyUser", metaObject);
        if (modifyUser == null) {
            setFieldValByName("modifyUser", userName, metaObject);

        }

        Object modifyTime = getFieldValByName("modifyTime", metaObject);
        if (modifyTime == null) {
            setFieldValByName("modifyTime", LocalDateTime.now(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String userName = SecurityUtil.getUserName();

        Object modifyUser = getFieldValByName("modifyUser", metaObject);
        if (modifyUser == null || Objects.equals(-1L, modifyUser)) {
            setFieldValByName("modifyUser", userName, metaObject);
        }

        setFieldValByName("modifyTime", LocalDateTime.now(), metaObject);
    }
}
