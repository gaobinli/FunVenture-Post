package com.nova.common.convertor;

import com.nova.domain.model.IconEntity;
import com.nova.domain.dto.req.IconReqDto;
import com.nova.domain.po.IconPo;
import com.baomidou.mybatisplus.ext.IConvertor;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class IconReqDtoConvertor implements IConvertor<IconReqDto, IconEntity,IconPo> {

}








