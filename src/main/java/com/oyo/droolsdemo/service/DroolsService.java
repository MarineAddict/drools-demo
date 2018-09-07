package com.oyo.droolsdemo.service;

import com.oyo.droolsdemo.entity.request.DroolsData;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private KieContainer kc;

    private String baseRoot=DroolsService.class.getClassLoader().getResource("drool").getPath();

    /**
     * 生成drl文件
     * @param
     */
    public String generateDrlFile(DroolsData droolsData)  {

        if(droolsData.getRule()==null||"".equals(droolsData.getRule())){
            return "EMPTY NAME IS INVALID";
        }
        FileWriter fw=null;
        String fileName=null;
        try {
         fileName=baseRoot+"/"+droolsData.getDroolFileName()+".drl";
        File file= ResourceUtils.getFile(fileName);
        if(!file.exists()){
            file.createNewFile();
        }else{
            return "FILE ALREADY EXISTS";
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
                if(fw!=null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       String result= verifyCompiledDrl(fileName);
        if(result!=null){
            System.out.println(result);
        }
        return "SUCCESS";
    }

    public String verifyCompiledDrl(String fileName){
        KnowledgeBuilder knowledgeBuilder= KnowledgeBuilderFactory.newKnowledgeBuilder();
        knowledgeBuilder.add(ResourceFactory.newFileResource(fileName), ResourceType.DRL);
        KnowledgeBuilderErrors errors = knowledgeBuilder.getErrors();
        if(errors.size()>0){
            return errors.toString();
        }
        return null;
    }



    public void testDrool(String sessionName,Object obj){
        KieSession ksession = kc.newKieSession(sessionName);
        ksession.insert(obj);
        ksession.fireAllRules();
        ksession.dispose();
    }





    }
