package com.oyo.droolsdemo.service;

import com.oyo.droolsdemo.entity.model.Customer;
import com.oyo.droolsdemo.entity.request.DroolsData;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author: create by xuqie
 * @description:
 * @date:2018/9/5
 */
@Service
public class DroolsService {

    public final static String separator= System.getProperty("line.separator");
    public final static String blank="\b";


    private String baseRoot=DroolsService.class.getClassLoader().getResource("drool").getPath();

    /**
     * 生成drl文件
     * @param
     */
    public void generateDrlFile(DroolsData droolsData)  {

        if(droolsData.getRule()==null||"".equals(droolsData.getRule())){
            return;
        }
        FileWriter fw=null;
        try {
        String fileName=baseRoot+"/"+droolsData.getDroolFileName()+".drl";
        File file= ResourceUtils.getFile(fileName);
        if(!file.exists()){
            file.createNewFile();
        }
            fw=new FileWriter(fileName);
            fw.write(droolsData.getPackageTitle());
            fw.write(separator);
            fw.write(droolsData.getImportTile());
            fw.write(separator);
            fw.write(droolsData.getRule());
            fw.write(separator);
            fw.write(droolsData.getWhenString());
            fw.write(separator);
            fw.write(droolsData.getThenString());
            fw.write(separator);
            fw.write(droolsData.getEnd());
            fw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                fw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void testDrool(String sessionName,Object obj){
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer("drool");
        KieSession ksession = kc.newKieSession(sessionName);
        ksession.insert(obj);
        ksession.fireAllRules();

    }



    }
