package com.oyo.droolsdemo.pagination;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: create by xuqie
 * @description: mysql分页用
 * @date:2018/9/15
 */
public class Pagination {

    public static final Integer DEFAULTPAGESIZE=10;

    public static final boolean DEFAULTNEEDPAGINATION=false;
    /**
     * 页数
     */
    private Integer pageNumber;

    /**
     *  每页size
     */
    private Integer pageSize;

    /**
     * 数据总数
     */
    private Integer total;

    /**
     * 是否需要分页
     */
    private boolean needPagination;

    /**
     * 请求参数
     */
    private Map param=new HashMap();


    public Pagination(){
    }

    public Pagination(Integer pageNumber,Integer pageSize){
        this.setPageSize(pageSize);
        this.setPageNumber(pageNumber);
    }


    public boolean isNeedPagination() {
        return needPagination;
    }

    public void setNeedPagination(boolean needPagination) {
        this.needPagination = needPagination;
    }

    public Map getParam() {
        return param;
    }


    /**
     * 添加请求参数
     * @param key
     * @param value
     */
    public void putParameter(String key,Object value){
        param.put(key,value);
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
