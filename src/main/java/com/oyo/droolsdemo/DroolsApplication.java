package com.oyo.droolsdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: create by xuqie
 * @description:
 * @date:2018/9/4
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.oyo.droolsdemo"})
public class DroolsApplication {
    public static void main(String[] args) {
        SpringApplication.run(DroolsApplication.class, args);
    }
}
