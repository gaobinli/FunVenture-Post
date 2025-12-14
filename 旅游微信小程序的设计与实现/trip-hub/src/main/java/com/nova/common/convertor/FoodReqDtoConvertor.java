package com.nova.common.convertor;

import com.nova.domain.dto.req.FoodReqDto;
import com.nova.domain.model.FoodEntity;
import com.nova.domain.po.FoodPo;
import com.baomidou.mybatisplus.ext.IConvertor;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class FoodReqDtoConvertor implements IConvertor<FoodReqDto, FoodEntity,FoodPo> {

}








