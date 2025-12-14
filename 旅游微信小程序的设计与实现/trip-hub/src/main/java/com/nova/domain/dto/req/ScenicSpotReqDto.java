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
public class ScenicSpotReqDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 名称 VARCHAR
     */
    @ApiModelProperty(value = "名称")
    @JsonProperty(index = 10)
    private String name;
    /**
     * 景点导航图 VARCHAR
     */
    @ApiModelProperty(value = "景点导航图")
    @JsonProperty(index = 20)
    private String url;
    /**
     * 景点地址 VARCHAR
     */
    @ApiModelProperty(value = "景点地址")
    @JsonProperty(index = 30)
    private String address;
    /**
     * 景点介绍 TEXT
     */
    @ApiModelProperty(value = "景点介绍")
    @JsonProperty(index = 40)
    private String introduce;

    private Integer price;

    private String shortRecommend;

    private String coverUrl;

    private Integer inventory;
}






