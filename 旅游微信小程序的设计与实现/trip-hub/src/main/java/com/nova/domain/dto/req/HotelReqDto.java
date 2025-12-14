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
public class HotelReqDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 封面 VARCHAR
     */
    @ApiModelProperty(value = "封面")
    @JsonProperty(index = 10)
    private String url;
    /**
     * 名称 VARCHAR
     */
    @ApiModelProperty(value = "名称")
    @JsonProperty(index = 20)
    private String name;
    /**
     * 价格 DECIMAL
     */
    @ApiModelProperty(value = "价格")
    @JsonProperty(index = 30)
    private Long price;
    /**
     * 简要推荐 VARCHAR
     */
    @ApiModelProperty(value = "简要推荐")
    @JsonProperty(index = 40)
    private String shortRecommend;
    /**
     * 地址 VARCHAR
     */
    @ApiModelProperty(value = "地址")
    @JsonProperty(index = 50)
    private String address;
    /**
     * 详情内容 TEXT
     */
    @ApiModelProperty(value = "详情内容")
    @JsonProperty(index = 60)
    private String content;

    private Integer inventory;
}






