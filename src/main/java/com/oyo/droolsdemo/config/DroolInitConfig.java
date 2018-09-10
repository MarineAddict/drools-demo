package com.oyo.droolsdemo.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: create by xuqie
 * @description:
 * @date:2018/9/5
 */
@Configuration
public class DroolInitConfig {

    private List<String> kieBaseList=new ArrayList<String>();
    String  scanRoot = this.getClass().getClassLoader().getResource("drool").getPath();

    @Bean
    public KieServices kieServices(){
        KieServices ks = KieServices.Factory.get();
        //create a Kmodule
        KieModuleModel kieModuleModel = ks.newKieModuleModel();
        insertKieBase(scanRoot);
        /*add new kiebase based on his package name (this is to add packages ristrictions to
         kiebase so that kie-session will execute drl with his package)*/
        configKieBase(kieModuleModel);
        KieFileSystem kfs = ks.newKieFileSystem();
        kfs.writeKModuleXML(kieModuleModel.toXML());
        //add the resource drl to this file system
        addDrlFiles(kfs,scanRoot);
        /**everything (module,model,session,drl source) has been added into the service,
         build KieFileSystem and create a container*/
        ks.newKieBuilder( kfs ).buildAll();
        return ks;
    }

    /**
     * 添加所有的drl文件进入filesystem
     * @param kfs
     */
    private void addDrlFiles(KieFileSystem kfs,String root) {
        File folder=new File(root);
        File[] files=folder.listFiles();
        for(File file:files){
            if(file.isFile()&&file.getName().endsWith(".drl")){
                String str=this.getClass().getClassLoader().getResource("").getPath().replaceAll("/","\\\\");
                String path=file.getPath().replaceAll(str.replaceAll("\\\\","\\\\\\\\").substring(2),"");
                kfs.write(ResourceFactory.newClassPathResource(path.replaceAll("\\\\","\\/")).setResourceType(ResourceType.DRL));
            }else{
                addDrlFiles(kfs,file.getPath());

            }
        }
    }

    /**
     *
     * @param kieModuleModel
     */
    private void configKieBase(KieModuleModel kieModuleModel) {
        if(kieBaseList.size()>0){
            for(String baseName:kieBaseList) {
               KieBaseModel baseModel= kieModuleModel.newKieBaseModel("base"+baseName)
                       .setDefault(true)
                        .addPackage("drool."+baseName)
                        .setEqualsBehavior(EqualityBehaviorOption.EQUALITY)
                        .setEventProcessingMode(EventProcessingOption.STREAM);
               //configure a kiesession for this kiebase, again, name after his baseName
               configKieSession(baseModel,baseName);
            }
        }

    }

    private void configKieSession(KieBaseModel baseModel,String baseName) {

        baseModel.newKieSessionModel(baseName)
                .setDefault(true)
                .setType(KieSessionModel.KieSessionType.STATEFUL)
                .setClockType(ClockTypeOption.get("realtime"));
    }


    private void insertKieBase(String scanRoot){
        //first clear the list to avoid any redundant elements when refreshed
        kieBaseList.clear();
        File folder=new File(scanRoot);
        File[] files=folder.listFiles();
        for(File file:files){
            if(file.isDirectory()){
                kieBaseList.add(file.getName());
            }
        }
    }



}
