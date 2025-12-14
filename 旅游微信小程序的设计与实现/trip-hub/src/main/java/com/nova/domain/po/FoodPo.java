package com.nova.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.ext.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "food")
public class FoodPo extends BasePo<FoodPo> implements Serializable{

    private static final long serialVersionUID = 1L;

    public FoodPo(){

    }
    public FoodPo(Long id){
        this.id = id;
    }

    /**
     * 封面 VARCHAR
     */
    private String url;

    /**
     * 名称 VARCHAR
     */
    private String name;

    /**
     * 价格 DECIMAL
     */
    private Integer price;

    /**
     * 地址 VARCHAR
     */
    private String address;

    /**
     * 简短推荐 VARCHAR
     */
    private String shortRecommend;

    /**
     * 详情内容 TEXT
     */
    private String content;

    private Integer inventory;

}






