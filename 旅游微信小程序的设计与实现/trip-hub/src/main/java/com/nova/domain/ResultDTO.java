package com.nova.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResultDTO<T> implements Serializable {

    /**
     * 业务处理结果说明，一般处理成功返回成功，处理失败返回失败说明。
     */
    @JsonProperty(index = 10)
    @ApiModelProperty(position = 10)
    private String code;
    /**
     * 说明
     */
    @JsonProperty(index = 20)
    @ApiModelProperty(position = 20)
    private String msg;
    /**
     * 业务响应参数,具体由每个接口定义
     */
    @JsonProperty(index = 40)
    @ApiModelProperty(position = 40)
    private T data;

    public ResultDTO() {

    }

    public ResultDTO(String code) {
        this.code = code;
    }

    public ResultDTO(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultDTO(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResultDTO ok() {
        ResultDTO dto = new ResultDTO<T>();
        dto.code = "000000";
        dto.msg = "Success";
        return dto;
    }

    public static <T> ResultDTO ok(T data) {
        ResultDTO dto = new ResultDTO<T>();
        dto.code = "000000";
        dto.msg = "Success";
        dto.data = data;
        return dto;
    }

    public static <T> ResultDTO ok(String code, String msg, T data) {
        ResultDTO dto = new ResultDTO<T>();
        dto.code = code;
        dto.msg = msg;
        dto.data = data;
        return dto;
    }

    public static <T> ResultDTO error() {
        return error("500", "未知异常，请联系管理员");
    }

    public static <T> ResultDTO error(String msg) {
        return error("500", msg);
    }

    public static <T> ResultDTO error(String code, String msg) {
        ResultDTO dto = new ResultDTO<Void>();
        dto.code = code;
        dto.msg = msg;
        return dto;
    }

    public String toSimpleJson() {
        return "{" + "code='" + code + '\'' + ", msg='" + msg + '\'' + "}";
    }
}
