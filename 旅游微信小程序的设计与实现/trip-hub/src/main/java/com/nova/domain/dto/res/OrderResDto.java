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
public class OrderResDto implements Serializable {

    private static final long serialVersionUID = 1L;

/**
 * 主键 INT
 */
@ApiModelProperty(value = "主键")
@JsonProperty(index = 0)

private Long id;
/**
 * 关联id INT
 */
@ApiModelProperty(value = "关联id")
@JsonProperty(index = 10)

private Long relationId;
/**
 * 订单类型，1 景点、2 美食、3 酒店 INT
 */
@ApiModelProperty(value = "订单类型，1 景点、2 美食、3 酒店")
@JsonProperty(index = 20)

private Integer type;
/**
 * 创建人 VARCHAR
 */
@ApiModelProperty(value = "创建人")
@JsonProperty(index = 40)

private String createUser;
/**
 * 创建时间 DATETIME
 */
@ApiModelProperty(value = "创建时间")
@JsonProperty(index = 50)
private LocalDateTime createTime;
/**
 * 修改人 VARCHAR
 */
@ApiModelProperty(value = "修改人")
@JsonProperty(index = 60)

private String modifyUser;
/**
 * 修改时间 DATETIME
 */
@ApiModelProperty(value = "修改时间")
@JsonProperty(index = 70)
private LocalDateTime modifyTime;



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

    private Long userId;

    private Integer status;
}






