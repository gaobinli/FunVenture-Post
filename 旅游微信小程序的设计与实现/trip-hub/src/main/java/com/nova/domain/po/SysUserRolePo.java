package com.nova.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.ext.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_user_role")
public class SysUserRolePo extends BasePo<SysUserRolePo> implements Serializable{

    private static final long serialVersionUID = 1L;

    public SysUserRolePo(){

    }
    public SysUserRolePo(Long id){
        this.id = id;
    }

    /**
     * 用户编码 VARCHAR
     */
    private String userCode;

    /**
     * 角色编码 VARCHAR
     */
    private String roleCode;

}






