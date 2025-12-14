package com.nova.common.convertor;

import com.nova.domain.dto.res.IconResDto;
import com.nova.domain.model.IconEntity;
import com.nova.domain.po.IconPo;
import com.baomidou.mybatisplus.ext.IConvertor;
import com.baomidou.mybatisplus.ext.Pager;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class IconResDtoConvertor implements IConvertor<IconResDto, IconEntity,IconPo> {

    public  Pager<IconResDto> convertPoPager2ResDtoPager(Pager<IconPo> poPager) {
        if (poPager == null) {
            return null;
        }

        Pager<IconResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(poList2DtoList(poPager.getList()));

        return resDtoPager;
    }
}








