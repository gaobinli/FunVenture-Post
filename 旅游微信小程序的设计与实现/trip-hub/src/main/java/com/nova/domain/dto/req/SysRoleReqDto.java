package com.nova.domain.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Validated
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel
public class SysRoleReqDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色编码 VARCHAR
     */
    @ApiModelProperty(value = "角色编码")
    @JsonProperty(index = 10)
    private String code;
    /**
     * 角色名称 VARCHAR
     */
    @ApiModelProperty(value = "角色名称")
    @JsonProperty(index = 20)
    private String name;

    private List<String> menuCodes;
}






