package com.nova.common.convertor;

import com.nova.domain.dto.res.HotelResDto;
import com.nova.domain.model.HotelEntity;
import com.nova.domain.po.HotelPo;
import com.baomidou.mybatisplus.ext.IConvertor;
import com.baomidou.mybatisplus.ext.Pager;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class HotelResDtoConvertor implements IConvertor<HotelResDto, HotelEntity,HotelPo> {

    public  Pager<HotelResDto> convertPoPager2ResDtoPager(Pager<HotelPo> poPager) {
        if (poPager == null) {
            return null;
        }

        Pager<HotelResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(poList2DtoList(poPager.getList()));

        return resDtoPager;
    }
}








