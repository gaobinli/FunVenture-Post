package com.nova.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.ext.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "user")
public class UserPo extends BasePo<UserPo> implements Serializable{

    private static final long serialVersionUID = 1L;

    public UserPo(){

    }
    public UserPo(Long id){
        this.id = id;
    }

    /**
     * 账号是否启用，1 启用 BIT
     */
    private Boolean enable;

    private String password;

    private String nickName;

    private String phone;

    private Integer sex;

    private String avatar;

    private Integer balance;
}






