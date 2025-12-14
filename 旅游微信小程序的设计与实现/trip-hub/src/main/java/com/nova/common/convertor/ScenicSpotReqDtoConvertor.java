package com.nova.common.convertor;

import com.nova.domain.model.ScenicSpotEntity;
import com.nova.domain.dto.req.ScenicSpotReqDto;
import com.nova.domain.po.ScenicSpotPo;
import com.baomidou.mybatisplus.ext.IConvertor;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class ScenicSpotReqDtoConvertor implements IConvertor<ScenicSpotReqDto, ScenicSpotEntity,ScenicSpotPo> {

}








