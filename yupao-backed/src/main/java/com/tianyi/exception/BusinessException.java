package com.tianyi.exception;

import com.tianyi.contant.ErrorCode;

/**
 * 自定义异常类
 */
public class BusinessException extends RuntimeException{

    private final int code;
    private final String description;

    public BusinessException(ErrorCode errorCode) {
        //这一句一定要加上，否则继承自RuntimeException的异常，不会强制要求在catch中处理
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }
    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }
    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
