package com.nova.domain.dto.req;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;


@Validated
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel
public class UserReqDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Boolean enable;

    private String password;

    private String nickName;

    private String phone;

    private Integer sex;

    private String avatar;

    private Integer balance;
}






