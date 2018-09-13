package com.oyo.droolsdemo.service;

import com.oyo.droolsdemo.common.droolutil.DroolToolUtil;
import com.oyo.droolsdemo.entity.drool.DataBaseDroolDrl;
import com.oyo.droolsdemo.entity.request.DroolsData;
import com.oyo.droolsdemo.mapper.mysql.BaseDroolDrlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        baseDroolDrlMapper.insert(dataBaseDroolDrl);
    }






}
