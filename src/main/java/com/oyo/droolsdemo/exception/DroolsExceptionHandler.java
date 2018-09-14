package com.oyo.droolsdemo.exception;

import com.oyo.common.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: create by xuqie
 * @description: 异常处理类
 * @date:2018/9/14
 */
@ControllerAdvice
public class DroolsExceptionHandler {

 @ExceptionHandler(RuntimeException.class)
 @ResponseStatus(HttpStatus.BAD_REQUEST)
 @ResponseBody
    public BaseResponse<?> handleDrlDataBaseException(RuntimeException ex){
       return BaseResponse.fail("0000",ex.getMessage());
    }


}
