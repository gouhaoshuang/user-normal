package com.tianyi.exception;


import com.tianyi.common.BaseResponse;
import com.tianyi.common.ResultUtils;
import com.tianyi.contant.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e){
        log.error("BusinessException : " + e.getMessage(), e);
        return ResultUtils.error( e.getCode() , e.getMessage(),e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e){
        log.error("runtimeException : " , e);
        return ResultUtils.error(ErrorCode.SYSTEM_EXCEPTION , e.getMessage(),"系统内部异常");
    }
}

