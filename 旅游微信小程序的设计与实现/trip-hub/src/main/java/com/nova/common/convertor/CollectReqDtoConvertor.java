package com.nova.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.nova.domain.model.CollectEntity;
import com.nova.domain.dto.req.CollectReqDto;
import com.nova.domain.po.CollectPo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class CollectReqDtoConvertor implements IConvertor<CollectReqDto, CollectEntity, CollectPo> {

}








