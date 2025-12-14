package com.nova.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.baomidou.mybatisplus.ext.Pager;
import com.nova.domain.dto.res.CollectResDto;
import com.nova.domain.model.CollectEntity;
import com.nova.domain.po.CollectPo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class CollectResDtoConvertor implements IConvertor<CollectResDto, CollectEntity, CollectPo> {

    public  Pager<CollectResDto> convertPoPager2ResDtoPager(Pager<CollectPo> poPager) {
        if (poPager == null) {
            return null;
        }

        Pager<CollectResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(poList2DtoList(poPager.getList()));

        return resDtoPager;
    }
}








