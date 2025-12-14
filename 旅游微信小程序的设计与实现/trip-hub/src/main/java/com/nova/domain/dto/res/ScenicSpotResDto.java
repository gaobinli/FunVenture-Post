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
public class ScenicSpotResDto implements Serializable {

    private static final long serialVersionUID = 1L;

/**
 * 主键 INT
 */
@ApiModelProperty(value = "主键")
@JsonProperty(index = 0)

private Integer id;
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

/**
 * 创建人 VARCHAR
 */
@ApiModelProperty(value = "创建人")
@JsonProperty(index = 70)

private String createUser;
/**
 * 创建时间 DATETIME
 */
@ApiModelProperty(value = "创建时间")
@JsonProperty(index = 80)
private LocalDateTime createTime;
/**
 * 修改人 VARCHAR
 */
@ApiModelProperty(value = "修改人")
@JsonProperty(index = 90)

private String modifyUser;
/**
 * 修改时间 DATETIME
 */
@ApiModelProperty(value = "修改时间")
@JsonProperty(index = 100)
private LocalDateTime modifyTime;

    private Integer price;

    private String shortRecommend;

    private String coverUrl;

    private Integer inventory;
}






