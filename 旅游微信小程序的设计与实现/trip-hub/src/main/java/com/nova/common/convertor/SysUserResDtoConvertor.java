package com.nova.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.baomidou.mybatisplus.ext.Pager;
import com.nova.domain.dto.res.SysUserResDto;
import com.nova.domain.model.SysUserEntity;
import com.nova.domain.po.SysUserPo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class SysUserResDtoConvertor implements IConvertor<SysUserResDto, SysUserEntity, SysUserPo> {

    public Pager<SysUserResDto> convertPoPager2ResDtoPager(Pager<SysUserPo> poPager) {
        if (poPager == null) {
            return null;
        }

        Pager<SysUserResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(poList2DtoList(poPager.getList()));

        return resDtoPager;
    }
}








