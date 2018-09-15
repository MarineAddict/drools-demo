package com.oyo.droolsdemo.mapper.mysql;

import com.oyo.droolsdemo.entity.drool.DataBaseDroolDrl;

import java.util.List;

public interface BaseDroolDrlMapper {

    int deleteByPrimaryKey(String id);

    int insert(DataBaseDroolDrl record);

    DataBaseDroolDrl selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DataBaseDroolDrl record);

    List<DataBaseDroolDrl> selectAllDrools(List);

}