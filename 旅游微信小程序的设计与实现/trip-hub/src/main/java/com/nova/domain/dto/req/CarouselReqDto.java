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
public class CarouselReqDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 名称 VARCHAR
     */
    @ApiModelProperty(value = "名称")
    @JsonProperty(index = 10)
    private String name;
    /**
     * 轮播图 VARCHAR
     */
    @ApiModelProperty(value = "轮播图")
    @JsonProperty(index = 20)
    private String url;
    /**
     * 关联跳转id INT
     */
    @ApiModelProperty(value = "关联跳转id")
    @JsonProperty(index = 30)
    private Integer relationId;

    private Integer sort;
}






