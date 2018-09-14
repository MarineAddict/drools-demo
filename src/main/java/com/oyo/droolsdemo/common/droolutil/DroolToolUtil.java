package com.oyo.droolsdemo.common.droolutil;

import com.oyo.droolsdemo.entity.drool.DataBaseDroolDrl;
import com.oyo.droolsdemo.entity.request.DroolsData;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author: create by xuqie
 * @description: drool的常用工具
 * @date:2018/9/13
 */
public class DroolToolUtil {

    /**
     * 将数据库的drl 记录entity 传输到droolData
     * @param dataBaseDroolDrl
     * @return
     */
    public static DroolsData transferToDroolsData(DataBaseDroolDrl dataBaseDroolDrl){
        DroolsData droolsData=new DroolsData();
        droolsData.setPackageTitle(dataBaseDroolDrl.getPackageDesc());
        droolsData.setImportTile(dataBaseDroolDrl.getImportDesc());
        droolsData.setRule(dataBaseDroolDrl.getRule());
        droolsData.setThen(dataBaseDroolDrl.getThenExp());
        droolsData.setWhen(dataBaseDroolDrl.getWhenExp());
        droolsData.setEnd(dataBaseDroolDrl.getThenExp());
        droolsData.setComments(dataBaseDroolDrl.getComments());
        return droolsData;
    }

    /**
     * 将droolData 传输到DBDroolsData
     */
    public static DataBaseDroolDrl transferToDBDroolsData(DroolsData droolsData){
        DataBaseDroolDrl dataBaseDroolDrl=new DataBaseDroolDrl();
        if(droolsData.getId()==null){
            dataBaseDroolDrl.setId(UUID.randomUUID().toString().replace("-",""));
        }else{
            dataBaseDroolDrl.setId(droolsData.getId());
        }
        dataBaseDroolDrl.setComments(droolsData.getComments());
        dataBaseDroolDrl.setPackageDesc(droolsData.getPackage());
        dataBaseDroolDrl.setRule(droolsData.getRule());
        dataBaseDroolDrl.setImportDesc(droolsData.getImportTile());
        dataBaseDroolDrl.setWhenExp(droolsData.getWhen());
        dataBaseDroolDrl.setThenExp(droolsData.getThen());
        dataBaseDroolDrl.setDrlRoot(getDrlRoot(droolsData));
        dataBaseDroolDrl.setCreateTime(LocalDateTime.now());
        dataBaseDroolDrl.setUpdateTime(LocalDateTime.now());
        return dataBaseDroolDrl;
    }

    private static String getDrlRoot(DroolsData droolsData) {
        return droolsData.getPackage().replaceAll("\\.","/")+"/"+droolsData.getRule()+".drl";
    }


}
