package com.oyo.droolsdemo.config;

import com.oyo.droolsdemo.config.bean.datasource.BaseDruid;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author: create by xuqie
 * @description:
 * @date:2018/9/12
 */
@Configuration
@EnableConfigurationProperties(BaseDruid.class)
@MapperScan(basePackages = "mapper.mysql",sqlSessionFactoryRef = "")
@Slf4j
public class MySQLDataSourceConfig {

    @Autowired
    private BaseDruid baseDruid;

    @Bean(name = "mysqlDataSource")
    public DataSource mysqlDataSource() {
        return null;
    }


    @Bean(name = "mysqlSqlSessionFactory")
    public SqlSessionFactory mysqlSqlSessionFactory() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(mysqlDataSource());
        //添加实体类路径
        bean.setTypeAliasesPackage("com.oyo.droolsdemo.entity.drool");
        try {
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
//        //添加插件
//        PageHelper pageHelper = MybatisUtils.getPageHelper();
//        bean.setPlugins(new Interceptor[]{pageHelper});

//        //添加XML目录
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        try {
//            bean.setMapperLocations(resolver.getResources(Constants.MAPPER_MYSQL_XML_PATH));
//            return bean.getObject();
//        } catch (Exception e) {
//            log.error("build mysql SqlSessionFactory has error !");
//            throw new RuntimeException(e);
//        }
    }
}
