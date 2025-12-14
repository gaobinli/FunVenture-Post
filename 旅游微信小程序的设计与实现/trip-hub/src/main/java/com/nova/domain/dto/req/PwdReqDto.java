package com.nova.domain.dto.req;

import io.swagger.annotations.ApiModel;
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
public class PwdReqDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String pwd;
    private String newPwd;
    private String reNewPwd;
}






