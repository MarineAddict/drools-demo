package com.oyo.droolsdemo.common.constant;

/**
 * @author: create by xuqie
 * @description: drool的统一返回码
 * @date:2018/9/14
 */
public enum  DroolsResponseCode {

    DROOL_RUNTIME_ERROR("0000","drl规则引擎运行时异常"),
    DROOL_COMPILE_ERROR("0001","drl规则文件编译错误");


    private String responseCode;
    private String responseText;

    DroolsResponseCode(String responseCode, String responseText) {
        this.responseCode = responseCode;
        this.responseText = responseText;
    }

}
