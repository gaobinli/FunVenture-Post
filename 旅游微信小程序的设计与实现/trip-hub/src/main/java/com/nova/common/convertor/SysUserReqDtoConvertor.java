package com.nova.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.nova.domain.model.SysUserEntity;
import com.nova.domain.dto.req.SysUserReqDto;
import com.nova.domain.po.SysUserPo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class SysUserReqDtoConvertor implements IConvertor<SysUserReqDto, SysUserEntity, SysUserPo> {

}








