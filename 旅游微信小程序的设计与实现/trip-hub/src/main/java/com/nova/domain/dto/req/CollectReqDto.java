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
public class CollectReqDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 关联id INT
     */
    @ApiModelProperty(value = "关联id")
    @JsonProperty(index = 10)
    private Long relationId;
    /**
     * 类型，1 景点、2 美食、3 酒店 INT
     */
    @ApiModelProperty(value = "订单类型，1 景点、2 美食、3 酒店")
    @JsonProperty(index = 20)
    private Integer type;

    private Long userId;
}






