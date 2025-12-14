package com.nova.mapper;

import com.baomidou.mybatisplus.ext.BaseExtMapper;
import com.nova.domain.po.SysUserPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseExtMapper<SysUserPo> {
	
}
