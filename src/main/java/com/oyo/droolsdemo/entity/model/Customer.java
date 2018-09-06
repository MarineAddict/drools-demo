package com.oyo.droolsdemo.entity.model;

import lombok.Data;

/**
 * @author: create by xuqie
 * @description:
 * @date:2018/9/5
 */
@Data
public class Customer {

    /**
     * 客户名*/
    private String name;

    /**
     * 年龄*/
    private Integer age;

    /**
     * 是否是vip客户*/
    private boolean vip;

    /**
     * 存款
     */
    private Double deposit;

    /**
     * 消费力
     */
    private ComsumptionLevel comsumptionLevel;


   public enum ComsumptionLevel{
        LOW,
        MEDIUM,
        HIGH
    }

}
