package com.nova.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.baomidou.mybatisplus.ext.Pager;
import com.nova.domain.dto.res.SysMenuResDto;
import com.nova.domain.model.SysMenuEntity;
import com.nova.domain.po.SysMenuPo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class SysMenuResDtoConvertor implements IConvertor<SysMenuResDto, SysMenuEntity, SysMenuPo> {

    public Pager<SysMenuResDto> convertPoPager2ResDtoPager(Pager<SysMenuPo> poPager) {
        if (poPager == null) {
            return null;
        }

        Pager<SysMenuResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(poList2DtoList(poPager.getList()));

        return resDtoPager;
    }
}








