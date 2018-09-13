package com.oyo.droolsdemo.config.bean.datasource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: create by xuqie
 * @description:
 * @date:2018/9/12
 * druid 连接池的属性
 */
@Data
@Component
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
