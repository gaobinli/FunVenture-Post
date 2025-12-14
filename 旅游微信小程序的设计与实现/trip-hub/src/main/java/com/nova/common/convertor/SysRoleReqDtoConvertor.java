package com.nova.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.nova.domain.model.SysRoleEntity;
import com.nova.domain.dto.req.SysRoleReqDto;
import com.nova.domain.po.SysRolePo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class SysRoleReqDtoConvertor implements IConvertor<SysRoleReqDto, SysRoleEntity, SysRolePo> {

}








