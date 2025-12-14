package com.nova.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.ext.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "scenic_spot")
public class ScenicSpotPo extends BasePo<ScenicSpotPo> implements Serializable{

    private static final long serialVersionUID = 1L;

    public ScenicSpotPo(){

    }
    public ScenicSpotPo(Long id){
        this.id = id;
    }

    /**
     * 名称 VARCHAR
     */
    private String name;

    /**
     * 景点导航图 VARCHAR
     */
    private String url;

    /**
     * 景点地址 VARCHAR
     */
    private String address;

    /**
     * 景点介绍 TEXT
     */
    private String introduce;

    private Integer price;

    private String shortRecommend;

    private String coverUrl;

    private Integer inventory;
}






