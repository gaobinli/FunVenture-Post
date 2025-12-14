package com.nova.mapper;

import com.baomidou.mybatisplus.ext.BaseExtMapper;
import com.nova.domain.po.OrderPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseExtMapper<OrderPo> {
	
}
