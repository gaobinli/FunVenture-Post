package com.nova.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.nova.domain.dto.req.SysUserRoleReqDto;
import com.nova.domain.model.SysUserRoleEntity;
import com.nova.domain.po.SysUserRolePo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class SysUserRoleReqDtoConvertor implements IConvertor<SysUserRoleReqDto, SysUserRoleEntity, SysUserRolePo> {

}








