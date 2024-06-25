package com.tianyi.common;

import com.tianyi.contant.ErrorCode;
import lombok.Data;

import java.io.Serializable;


/**
 * 通用返回类
 * @param <T>
 */
@Data
public class BaseResponse<T> implements Serializable {

    // 返回码
    private int code;
    // 返回信息
    private String message;
    // 返回数据
    private T data;

    private String description;

    public BaseResponse(){}

    public BaseResponse(int code , T data ,String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
    public BaseResponse(int code , T data ,String message,String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(), null, errorCode.getMessage(),errorCode.getDescription());
    }
}
