package com.nova.common.convertor;

import com.nova.domain.dto.res.CarouselResDto;
import com.nova.domain.model.CarouselEntity;
import com.nova.domain.po.CarouselPo;
import com.baomidou.mybatisplus.ext.IConvertor;
import com.baomidou.mybatisplus.ext.Pager;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class CarouselResDtoConvertor implements IConvertor<CarouselResDto, CarouselEntity,CarouselPo> {

    public  Pager<CarouselResDto> convertPoPager2ResDtoPager(Pager<CarouselPo> poPager) {
        if (poPager == null) {
            return null;
        }

        Pager<CarouselResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(poList2DtoList(poPager.getList()));

        return resDtoPager;
    }
}








