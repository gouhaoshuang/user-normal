package com.tianyi.contant;

public enum ErrorCode {

    SUCCESS(0, "正确", ""),
    PARAMS_ERROR(40001, "参数错误", ""),
    NULL_RARAM_ERROR(40002, "请求参数为空", ""),
    NOT_LOGIN(40003, "未登录", ""),
    NO_AUTH(40004, "无权限", ""),
    SYSTEM_EXCEPTION(50000, "系统内部异常", "");


    private final int code;
    /**
     * 状态码信息
     */
    private final String message;
    /**
     * 状态信息
     */
    private final String description;


    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
