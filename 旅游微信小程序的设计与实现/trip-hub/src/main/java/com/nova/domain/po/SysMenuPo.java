package com.nova.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.ext.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_menu")
public class SysMenuPo extends BasePo<SysMenuPo> implements Serializable{

    private static final long serialVersionUID = 1L;

    public SysMenuPo(){

    }
    public SysMenuPo(Long id){
        this.id = id;
    }

    /**
     * 菜单编码 VARCHAR
     */
    private String code;

    /**
     * 菜单名称 VARCHAR
     */
    private String name;

    /**
     * 上级菜单 VARCHAR
     */
    private String parentCode;

    /**
     * 菜单路径 VARCHAR
     */
    private String path;

    private Integer sort;

    private String icon;

}






