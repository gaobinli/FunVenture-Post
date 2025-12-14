package com.nova.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.ext.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "hotel")
public class HotelPo extends BasePo<HotelPo> implements Serializable{

    private static final long serialVersionUID = 1L;

    public HotelPo(){

    }
    public HotelPo(Long id){
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
     * 简要推荐 VARCHAR
     */
    private String shortRecommend;

    /**
     * 地址 VARCHAR
     */
    private String address;

    /**
     * 详情内容 TEXT
     */
    private String content;

    private Integer inventory;
}






