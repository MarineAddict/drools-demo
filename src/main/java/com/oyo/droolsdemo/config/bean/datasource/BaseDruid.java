package com.oyo.droolsdemo.config.bean.datasource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: create by xuqie
 * @description:
 * @date:2018/9/12
 * druid 连接池的属性
 */
@Data
@ConfigurationProperties(prefix = "druid.mysql")
public class BaseDruid {

    private String url;

    private String userName;

    private String password;

    private String driverClass;

    private Integer maxActive;

    private Integer minIdle;

    private Integer initialSize;

    private Boolean testOnBorrow;

}
