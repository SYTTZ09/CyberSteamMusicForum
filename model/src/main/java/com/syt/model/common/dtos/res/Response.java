package com.syt.model.common.dtos.res;


import com.syt.model.common.enums.ResponseCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回类
 */
@Data
public class Response<T> implements Serializable {

    private static final long serializableUID = 1L;

    private String host;
    private Integer code;
    private String message;
    private T data;

    public Response() {
        this.code = ResponseCode.SUCCESS.getCode();
        this.message = ResponseCode.SUCCESS.getMessage();
    }

    public Response(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Response(String host, Integer code, String message, T data) {
        this.host = host;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
