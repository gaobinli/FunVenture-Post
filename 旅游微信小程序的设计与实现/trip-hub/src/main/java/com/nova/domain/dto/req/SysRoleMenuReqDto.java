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
public class SysRoleMenuReqDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色编码 VARCHAR
     */
    @ApiModelProperty(value = "角色编码")
    @JsonProperty(index = 10)
    private String roleCode;
    /**
     * 菜单编码 VARCHAR
     */
    @ApiModelProperty(value = "菜单编码")
    @JsonProperty(index = 20)
    private String menuCode;

    private String code;
}






