package com.nova.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.nova.domain.model.SysMenuEntity;
import com.nova.domain.dto.req.SysMenuReqDto;
import com.nova.domain.po.SysMenuPo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class SysMenuReqDtoConvertor implements IConvertor<SysMenuReqDto, SysMenuEntity, SysMenuPo> {

}








