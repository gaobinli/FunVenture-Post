package com.nova.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.ext.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_user")
public class SysUserPo extends BasePo<SysUserPo> implements Serializable{

    private static final long serialVersionUID = 1L;

    public SysUserPo(){

    }
    public SysUserPo(Long id){
        this.id = id;
    }

    /**
     * 用户编码 VARCHAR
     */
    private String code;

    /**
     * 密码 VARCHAR
     */
    private String password;

    /**
     * 姓名 VARCHAR
     */
    private String name;

    /**
     * 手机号 VARCHAR
     */
    private String phone;

    /**
     * 邮箱 VARCHAR
     */
    private String email;

    /**
     * 账号是否启用，1 启用 BIT
     */
    private Boolean enable;

}






