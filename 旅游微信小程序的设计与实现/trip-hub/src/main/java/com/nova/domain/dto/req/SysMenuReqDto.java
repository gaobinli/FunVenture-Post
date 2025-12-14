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
public class SysMenuReqDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单编码 VARCHAR
     */
    @ApiModelProperty(value = "菜单编码")
    @JsonProperty(index = 10)
    private String code;
    /**
     * 菜单名称 VARCHAR
     */
    @ApiModelProperty(value = "菜单名称")
    @JsonProperty(index = 20)
    private String name;
    /**
     * 上级菜单 VARCHAR
     */
    @ApiModelProperty(value = "上级菜单")
    @JsonProperty(index = 30)
    private String parentCode;
    /**
     * 菜单路径 VARCHAR
     */
    @ApiModelProperty(value = "菜单路径")
    @JsonProperty(index = 40)
    private String path;

    private Integer sort;

    private String icon;
}






