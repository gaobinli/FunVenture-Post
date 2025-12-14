package com.nova.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.ext.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_role_menu")
public class SysRoleMenuPo extends BasePo<SysRoleMenuPo> implements Serializable{

    private static final long serialVersionUID = 1L;

    public SysRoleMenuPo(){

    }
    public SysRoleMenuPo(Long id){
        this.id = id;
    }

    /**
     * 角色编码 VARCHAR
     */
    private String roleCode;

    /**
     * 菜单编码 VARCHAR
     */
    private String menuCode;

}






