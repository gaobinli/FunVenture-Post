package com.nova.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.baomidou.mybatisplus.ext.Pager;
import com.nova.domain.dto.res.SysRoleResDto;
import com.nova.domain.model.SysRoleEntity;
import com.nova.domain.po.SysRolePo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class SysRoleResDtoConvertor implements IConvertor<SysRoleResDto, SysRoleEntity, SysRolePo> {

    public Pager<SysRoleResDto> convertPoPager2ResDtoPager(Pager<SysRolePo> poPager) {
        if (poPager == null) {
            return null;
        }

        Pager<SysRoleResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(poList2DtoList(poPager.getList()));

        return resDtoPager;
    }
}








