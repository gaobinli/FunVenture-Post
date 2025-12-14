package com.nova.mapper;

import com.nova.domain.po.FoodPo;
import com.baomidou.mybatisplus.ext.BaseExtMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoodMapper extends BaseExtMapper<FoodPo> {
	
}
