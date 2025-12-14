package com.nova.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.ext.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_role")
public class SysRolePo extends BasePo<SysRolePo> implements Serializable{

    private static final long serialVersionUID = 1L;

    public SysRolePo(){

    }
    public SysRolePo(Long id){
        this.id = id;
    }

    /**
     * 角色编码 VARCHAR
     */
    private String code;

    /**
     * 角色名称 VARCHAR
     */
    private String name;

}






