package com.oyo.droolsdemo.common.datasourceutil;

import com.alibaba.druid.pool.DruidDataSource;
import com.oyo.droolsdemo.config.bean.datasource.BaseDruid;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author: create by xuqie
 * @description: 连接池工具类
 * @date:2018/9/13
 */
public class DataSourceUtils {

    //获取连接池
    public static DataSource getDataSource(BaseDruid properties) {

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(properties.getUrl());
        dataSource.setUsername(properties.getUserName());
        dataSource.setPassword(properties.getPassword());
        dataSource.setDriverClassName(properties.getDriverClass());
        if (properties.getInitialSize() > 0) {
            dataSource.setInitialSize(properties.getInitialSize());
        }
        if (properties.getMinIdle() > 0) {
            dataSource.setMinIdle(properties.getMinIdle());
        }
        if (properties.getMaxActive() > 0) {
            dataSource.setMaxActive(properties.getMaxActive());
        }
        dataSource.setTestOnBorrow(properties.getTestOnBorrow());

        try {
            dataSource.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }


}
