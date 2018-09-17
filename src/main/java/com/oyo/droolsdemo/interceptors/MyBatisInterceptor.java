package com.oyo.droolsdemo.interceptors;


import com.oyo.droolsdemo.pagination.Pagination;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * @author: create by xuqie
 * @description:
 * @date:2018/9/17
 */
@Intercepts(value = {@Signature(type= StatementHandler.class,method="prepare",args={Connection.class,Integer.class})})
public class MyBatisInterceptor implements Interceptor {

    private static final String DATABASEDIALECT="MYSQL";


    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        if(invocation.getTarget() instanceof RoutingStatementHandler){
            RoutingStatementHandler statementHandler = (RoutingStatementHandler)invocation.getTarget();
            StatementHandler delegate= (StatementHandler)getFieldValue(statementHandler,"delegate");
            BoundSql boundSql=statementHandler.getBoundSql();
           //获得sql的请求参数
            Object obj= boundSql.getParameterObject();
           if(obj instanceof Pagination){
               Pagination pagination= (Pagination) obj;
               if(pagination.isNeedPagination()==false){
                   return invocation.proceed();
               }
               MappedStatement mappedStatement = (MappedStatement) getFieldValue(delegate, "mappedStatement");
               Connection connection = (Connection)invocation.getArgs()[0];
               String sql = boundSql.getSql();
               setTotalRecord(pagination,
                       mappedStatement, connection);
            //获取分页Sql语句
            String pageSql = getPageSql(pagination, sql);
            //利用反射设置当前BoundSql对应的sql属性为我们建立好的分页Sql语句
            setFieldValue(boundSql, "sql", pageSql);
           }
        }
return  invocation.proceed();
    }

    private void setFieldValue(Object obj, String fieldName, Object value) {
        if(null == obj){return;}
        Field targetField = getTargetField(obj.getClass(), fieldName);
        try {
            FieldUtils.writeField(targetField, obj, value) ;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private String getPageSql(Pagination page, String sql) {
        StringBuffer sqlBuffer = new StringBuffer(sql);
        if ("mysql".equalsIgnoreCase(DATABASEDIALECT)) {
            return getMysqlPageSql(page, sqlBuffer);
        } else if ("oracle".equalsIgnoreCase(DATABASEDIALECT)) {
            return getOraclePageSql(page, sqlBuffer);
        }
        return sqlBuffer.toString();
    }

    private String getMysqlPageSql(Pagination page, StringBuffer sqlBuffer) {
        //计算第一条记录的位置，Mysql中记录的位置是从0开始的。
//   System.out.println("page:"+page.getPage()+"-------"+page.getRows());
        int offset = (page.getPageNumber() - 1) * page.getPageSize();
        sqlBuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
        return sqlBuffer.toString();
    }

    /**
     * 获取Oracle数据库的分页查询语句
     * @param page 分页对象
     * @param sqlBuffer 包含原sql语句的StringBuffer对象
     * @return Oracle数据库的分页查询语句
     */
    private String getOraclePageSql(Pagination page, StringBuffer sqlBuffer) {
        //计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的
        int offset = (page.getPageNumber() - 1) * page.getPageSize() + 1;
        sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum < ").append(offset + page.getPageSize());
        sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(offset);
        //上面的Sql语句拼接之后大概是这个样子：
        //select * from (select u.*, rownum r from (select * from t_user) u where rownum < 31) where r >= 16
        return sqlBuffer.toString();
    }

    private void setTotalRecord(Pagination pagination, MappedStatement mappedStatement, Connection connection) {
//获取对应的BoundSql，这个BoundSql其实跟我们利用StatementHandler获取到的BoundSql是同一个对象。
        //delegate里面的boundSql也是通过mappedStatement.getBoundSql(paramObj)方法获取到的。
        BoundSql boundSql = mappedStatement.getBoundSql(pagination);
        //获取到我们自己写在Mapper映射语句中对应的Sql语句
        String sql = boundSql.getSql();
        //通过查询Sql语句获取到对应的计算总记录数的sql语句
        String countSql = this.getCountSql(sql);
        //通过BoundSql获取对应的参数映射
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        //利用Configuration、查询记录数的Sql语句countSql、参数映射关系parameterMappings和参数对象page建立查询记录数对应的BoundSql对象。
        BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, pagination);
        //通过mappedStatement、参数对象page和BoundSql对象countBoundSql建立一个用于设定参数的ParameterHandler对象
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, pagination, countBoundSql);
        //通过connection建立一个countSql对应的PreparedStatement对象。
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(countSql);
            //通过parameterHandler给PreparedStatement对象设置参数
            parameterHandler.setParameters(pstmt);
            //之后就是执行获取总记录数的Sql语句和获取结果了。
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int totalRecord = rs.getInt(1);
                //给当前的参数page对象设置总记录数
                pagination.setTotal(totalRecord);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private  Object getFieldValue(Object obj, String fieldName) {
        if(obj == null){
            return null ;
        }
        Field targetField = getTargetField(obj.getClass(), fieldName);
        try {
            return FieldUtils.readField(targetField, obj, true ) ;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null ;
    }

    private  Field getTargetField(Class<?> targetClass, String fieldName) {
        Field field = null;
        try {
            if (targetClass == null) {
                return field;
            }
            if (Object.class.equals(targetClass)) {
                return field;
            }
            field = FieldUtils.getDeclaredField(targetClass, fieldName, true);
            if (field == null) {
                field = getTargetField(targetClass.getSuperclass(), fieldName);
            }
        } catch (Exception e) {
        }
        return field;
    }

    private void executeTotalQuery(Pagination pagination, MappedStatement mappedStatement, String sql) {
        //得到计算总数的sql语句
        String countSql=getCountSql(sql);




    }

    private String getCountSql(String sql) {
        int index = sql.indexOf("from");
        return "select count(*) " + sql.substring(index);   }


    public String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));

            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        return sql;
    }

    private String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(obj) + "'";
//	            System.out.println(value);
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }




    @Override
    public Object plugin(Object o) {
         return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
