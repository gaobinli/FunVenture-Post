package com.nova.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.nova.domain.model.OrderEntity;
import com.nova.domain.dto.req.OrderReqDto;
import com.nova.domain.po.OrderPo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class OrderReqDtoConvertor implements IConvertor<OrderReqDto, OrderEntity, OrderPo> {

}








