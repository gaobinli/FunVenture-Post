package com.nova.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.ext.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "icon")
public class IconPo extends BasePo<IconPo> implements Serializable{

    private static final long serialVersionUID = 1L;

    public IconPo(){

    }
    public IconPo(Long id){
        this.id = id;
    }

    /**
     * 名称 VARCHAR
     */
    private String name;

    /**
     * icon图标 VARCHAR
     */
    private String url;

    /**
     * 关联跳转id INT
     */
    private Integer relationId;

    private Integer sort;
}






