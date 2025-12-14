package com.nova.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.ext.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "collect")
public class CollectPo extends BasePo<CollectPo> implements Serializable{

    private static final long serialVersionUID = 1L;

    public CollectPo(){

    }
    public CollectPo(Long id){
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
}






