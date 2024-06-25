package com.tianyi.common;

import com.tianyi.contant.ErrorCode;

/**
 * 返回工具类
 */
public class ResultUtils {
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0 , data , "ok");

    }

    /**
     * 失败
     * @param errorCode
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }
    public static <T> BaseResponse<T> error(ErrorCode errorCode,String message , String description) {
        return new BaseResponse<>(errorCode.getCode()  , null , message , description);
    }
    public static <T> BaseResponse<T> error(ErrorCode errorCode,String description) {
        return new BaseResponse<>(errorCode.getCode()  , null , description);
    }
    public static <T> BaseResponse<T> error(int code,String message ,String description) {
        return new BaseResponse<>(code  , null ,message, description);
    }
}
