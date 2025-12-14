package com.nova.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.nova.domain.dto.req.LoginReqDto;
import com.nova.domain.model.LoginEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class LoginReqDtoConvertor implements IConvertor<LoginReqDto, LoginEntity, String> {

}








