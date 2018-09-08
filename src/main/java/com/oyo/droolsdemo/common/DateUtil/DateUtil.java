package com.oyo.droolsdemo.common.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * @author: create by xuqie
 * @description:
 * @date:2018/9/8
 */
public class DateUtil {


    /**
     * 根据Date返回小时数
     * @param date
     * @return
     */
    public static Integer getHours(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }


    public static void main(String[] args){
        System.out.println(DateUtil.getHours(new Date()));
    }

}
