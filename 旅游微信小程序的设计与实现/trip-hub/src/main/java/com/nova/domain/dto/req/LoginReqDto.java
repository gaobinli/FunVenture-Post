package com.nova.domain.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
public class LoginReqDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    @JsonProperty(index = 10)
    private String username;

    @ApiModelProperty(value = "密码")
    @JsonProperty(index = 20)
    private String password;

    @ApiModelProperty(value = "验证码")
    @JsonProperty(index = 30)
    private String code;

    @ApiModelProperty(value = "验证码key")
    @JsonProperty(index = 40)
    private String uuid;
}
