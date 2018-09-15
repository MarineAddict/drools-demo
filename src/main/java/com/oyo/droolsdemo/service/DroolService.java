package com.oyo.droolsdemo.service;

import com.oyo.droolsdemo.common.constant.DroolsDataBaseResponseCode;
import com.oyo.droolsdemo.common.droolutil.DroolToolUtil;
import com.oyo.droolsdemo.entity.drool.DataBaseDroolDrl;
import com.oyo.droolsdemo.entity.request.DroolsData;
import com.oyo.droolsdemo.exception.DrlDataBaseException;
import com.oyo.droolsdemo.mapper.mysql.BaseDroolDrlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: create by xuqie
 * @description: base数据库的增删改
 * @date:2018/9/13
 */
@Service
public class DroolService {

    @Autowired
    private BaseDroolDrlMapper baseDroolDrlMapper;

    public void insertNewDrl(DroolsData droolsData){
        DataBaseDroolDrl dataBaseDroolDrl=DroolToolUtil.transferToDBDroolsData(droolsData);
        try {
            baseDroolDrlMapper.insert(dataBaseDroolDrl);
        }catch (Exception e){
            throw new RuntimeException(DroolsDataBaseResponseCode.DROOL_DATABASE_ERROR.getResponseText());
        }
    }

    public void deleteDrl(DroolsData droolsData){
        if(droolsData.getId()==null){
            throw new RuntimeException(DroolsDataBaseResponseCode.DROOL_DATABASE_ERROR.getResponseText());
        }
        try {
            baseDroolDrlMapper.deleteByPrimaryKey(droolsData.getId());
        }catch (Exception e){
            throw new RuntimeException(DroolsDataBaseResponseCode.DROOL_DATABASE_ERROR.getResponseText());
        }
    }


   public List<DataBaseDroolDrl> selectAllDrools(){
      return  baseDroolDrlMapper.selectAllDrools();
   }



}
