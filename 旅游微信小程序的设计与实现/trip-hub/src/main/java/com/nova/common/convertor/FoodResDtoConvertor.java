package com.nova.common.convertor;

import com.nova.domain.dto.res.FoodResDto;
import com.nova.domain.model.FoodEntity;
import com.nova.domain.po.FoodPo;
import com.baomidou.mybatisplus.ext.IConvertor;
import com.baomidou.mybatisplus.ext.Pager;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class FoodResDtoConvertor implements IConvertor<FoodResDto, FoodEntity,FoodPo> {

    public  Pager<FoodResDto> convertPoPager2ResDtoPager(Pager<FoodPo> poPager) {
        if (poPager == null) {
            return null;
        }

        Pager<FoodResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(poList2DtoList(poPager.getList()));

        return resDtoPager;
    }
}








