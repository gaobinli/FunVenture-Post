package com.nova.mapper;

import com.nova.domain.po.HotelPo;
import com.baomidou.mybatisplus.ext.BaseExtMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HotelMapper extends BaseExtMapper<HotelPo> {
	
}
