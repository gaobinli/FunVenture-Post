package com.nova.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.baomidou.mybatisplus.ext.Pager;
import com.nova.domain.dto.res.SysUserRoleResDto;
import com.nova.domain.model.SysUserRoleEntity;
import com.nova.domain.po.SysUserRolePo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class SysUserRoleResDtoConvertor implements IConvertor<SysUserRoleResDto, SysUserRoleEntity, SysUserRolePo> {

    public Pager<SysUserRoleResDto> convertPoPager2ResDtoPager(Pager<SysUserRolePo> poPager) {
        if (poPager == null) {
            return null;
        }

        Pager<SysUserRoleResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(poList2DtoList(poPager.getList()));

        return resDtoPager;
    }
}








