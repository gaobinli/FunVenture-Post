package com.nova.domain.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.time.LocalDateTime;

@Validated
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel
public class SysUserResDto implements Serializable {

    private static final long serialVersionUID = 1L;

/**
 *  INT
 */
@ApiModelProperty(value = "")
@JsonProperty(index = 0)

private Integer id;
/**
 * 用户编码 VARCHAR
 */
@ApiModelProperty(value = "用户编码")
@JsonProperty(index = 10)

private String code;
/**
 * 密码 VARCHAR
 */
@ApiModelProperty(value = "密码")
@JsonProperty(index = 20)

private String password;
/**
 * 姓名 VARCHAR
 */
@ApiModelProperty(value = "姓名")
@JsonProperty(index = 30)

private String name;
/**
 * 手机号 VARCHAR
 */
@ApiModelProperty(value = "手机号")
@JsonProperty(index = 40)

private String phone;
/**
 * 邮箱 VARCHAR
 */
@ApiModelProperty(value = "邮箱")
@JsonProperty(index = 50)

private String email;
/**
 * 账号是否启用，1 启用 BIT
 */
@ApiModelProperty(value = "账号是否启用，1 启用")
@JsonProperty(index = 60)

private Boolean enable;
/**
 * 创建人 VARCHAR
 */
@ApiModelProperty(value = "创建人")
@JsonProperty(index = 80)

private String createUser;
/**
 * 创建时间 DATETIME
 */
@ApiModelProperty(value = "创建时间")
@JsonProperty(index = 90)
private LocalDateTime createTime;
/**
 * 修改人 VARCHAR
 */
@ApiModelProperty(value = "修改人")
@JsonProperty(index = 100)

private String modifyUser;
/**
 * 修改时间 DATETIME
 */
@ApiModelProperty(value = "修改时间")
@JsonProperty(index = 110)
private LocalDateTime modifyTime;

private String roleNames;

}






