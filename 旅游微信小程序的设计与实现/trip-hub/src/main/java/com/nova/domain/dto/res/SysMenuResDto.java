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
import java.util.List;

@Validated
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel
public class SysMenuResDto implements Serializable {

    private static final long serialVersionUID = 1L;

/**
 *  INT
 */
@ApiModelProperty(value = "")
@JsonProperty(index = 0)

private Integer id;
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
/**
 * 创建人 VARCHAR
 */
@ApiModelProperty(value = "创建人")
@JsonProperty(index = 60)

private String createUser;
/**
 * 创建时间 DATETIME
 */
@ApiModelProperty(value = "创建时间")
@JsonProperty(index = 70)
private LocalDateTime createTime;
/**
 * 修改人 VARCHAR
 */
@ApiModelProperty(value = "修改人")
@JsonProperty(index = 80)

private String modifyUser;
/**
 * 修改时间 DATETIME
 */
@ApiModelProperty(value = "修改时间")
@JsonProperty(index = 90)
private LocalDateTime modifyTime;

    private List<SysMenuResDto> childrens;

    private Boolean hasChildren;

    private Integer sort;

    private String icon;
}






