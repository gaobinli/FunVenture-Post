package com.nova.common.convertor;

import com.nova.domain.dto.req.CarouselReqDto;
import com.nova.domain.model.CarouselEntity;
import com.nova.domain.po.CarouselPo;
import com.baomidou.mybatisplus.ext.IConvertor;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class CarouselReqDtoConvertor implements IConvertor<CarouselReqDto, CarouselEntity,CarouselPo> {

}








