package com.oyo.droolsdemo.common.constant;

import lombok.Data;

/**
 * @author: create by xuqie
 * @description:
 * @date:2018/9/14
 */

public enum DroolsDataBaseResponseCode {

        DROOL_DATABASE_ERROR("0002","drl数据库操作出错");

        private String responseCode;
        private String responseText;

        DroolsDataBaseResponseCode(String responseCode, String responseText) {
            this.responseCode = responseCode;
            this.responseText = responseText;
        }

    public String getResponseCode() {
        return responseCode;
    }

    public String getResponseText() {
        return responseText;
    }
}
