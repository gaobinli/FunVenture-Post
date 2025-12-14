package com.nova.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.nova.domain.dto.req.SysRoleMenuReqDto;
import com.nova.domain.model.SysRoleMenuEntity;
import com.nova.domain.po.SysRoleMenuPo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class SysRoleMenuReqDtoConvertor implements IConvertor<SysRoleMenuReqDto, SysRoleMenuEntity, SysRoleMenuPo> {

}








