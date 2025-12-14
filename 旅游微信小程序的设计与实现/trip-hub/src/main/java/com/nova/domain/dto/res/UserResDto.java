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
public class UserResDto implements Serializable {

    private static final long serialVersionUID = 1L;

/**
 * ID INT
 */
@ApiModelProperty(value = "ID")
@JsonProperty(index = 510)
private Integer id;

    private Boolean enable;

    private String nickName;

    private String phone;

    private Integer sex;

    private LocalDateTime createTime;

    private String avatar;

    private Integer balance;
}






