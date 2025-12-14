package com.nova.common.convertor;

import com.nova.domain.model.HotelEntity;
import com.nova.domain.dto.req.HotelReqDto;
import com.nova.domain.po.HotelPo;
import com.baomidou.mybatisplus.ext.IConvertor;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class HotelReqDtoConvertor implements IConvertor<HotelReqDto, HotelEntity,HotelPo> {

}








