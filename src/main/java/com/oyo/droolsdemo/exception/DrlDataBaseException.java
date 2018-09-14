package com.oyo.droolsdemo.exception;

import com.oyo.droolsdemo.common.constant.DroolsDataBaseResponseCode;
import com.oyo.droolsdemo.common.constant.DroolsResponseCode;

/**
 * @author: create by xuqie
 * @description: drl数据库相关异常
 * @date:2018/9/14
 */
public class DrlDataBaseException extends Exception {

    private DroolsDataBaseResponseCode droolsDataBaseResponseCode;

    public DrlDataBaseException(String message){
        super(message);
    }

    public DrlDataBaseException() {
    }

    public DrlDataBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DrlDataBaseException(Throwable cause) {
        super(cause);
    }

    public DrlDataBaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DrlDataBaseException(DroolsDataBaseResponseCode droolsDataBaseResponseCode){
        this.droolsDataBaseResponseCode=droolsDataBaseResponseCode;
    }

    public DroolsDataBaseResponseCode getDroolsResponseCode(){
           return droolsDataBaseResponseCode;
    }

}
