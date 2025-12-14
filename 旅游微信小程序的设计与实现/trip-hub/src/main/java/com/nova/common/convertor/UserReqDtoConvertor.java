package com.nova.common.convertor;

import com.nova.domain.model.UserEntity;
import com.nova.domain.dto.req.UserReqDto;
import com.nova.domain.po.UserPo;
import com.baomidou.mybatisplus.ext.IConvertor;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class UserReqDtoConvertor implements IConvertor<UserReqDto, UserEntity, UserPo> {

}








