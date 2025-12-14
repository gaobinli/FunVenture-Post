package com.nova.common.convertor;

import com.nova.domain.dto.res.ScenicSpotResDto;
import com.nova.domain.model.ScenicSpotEntity;
import com.nova.domain.po.ScenicSpotPo;
import com.baomidou.mybatisplus.ext.IConvertor;
import com.baomidou.mybatisplus.ext.Pager;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class ScenicSpotResDtoConvertor implements IConvertor<ScenicSpotResDto, ScenicSpotEntity,ScenicSpotPo> {

    public  Pager<ScenicSpotResDto> convertPoPager2ResDtoPager(Pager<ScenicSpotPo> poPager) {
        if (poPager == null) {
            return null;
        }

        Pager<ScenicSpotResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(poList2DtoList(poPager.getList()));

        return resDtoPager;
    }
}








