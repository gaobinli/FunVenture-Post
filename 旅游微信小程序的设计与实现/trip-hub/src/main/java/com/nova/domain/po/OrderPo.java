package com.nova.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.ext.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "`order`")
public class OrderPo extends BasePo<OrderPo> implements Serializable{

    private static final long serialVersionUID = 1L;

    public OrderPo(){

    }
    public OrderPo(Long id){
        this.id = id;
    }

    /**
     * 关联id INT
     */
    private Long relationId;

    /**
     * 订单类型，1 景点、2 美食、3 酒店 INT
     */
    private Integer type;

    private Long userId;

    private Integer status;
}






