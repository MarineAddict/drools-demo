package com.oyo.droolsdemo.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: create by xuqie
 * @description:
 * @date:2018/9/5
 */
@Configuration
public class DrlGeneratorConfig {

    private List<String> kieBaseList=new ArrayList<String>();
    String  scanRoot = this.getClass().getClassLoader().getResource("drool").getPath();

    @Bean
    public KieContainer kieContainer(){
        KieServices ks = KieServices.Factory.get();
        //create a Kmodule
        KieModuleModel kieModuleModel = ks.newKieModuleModel();

        insertKieBase(scanRoot);
        //add new kiebase based on his package name (this is to add packages ristrictions to
        // kiebase so that kie-session will execute drl with his package)
        configKieBase(kieModuleModel);

        KieFileSystem kfs = ks.newKieFileSystem();
        kfs.writeKModuleXML(kieModuleModel.toXML());

        //add the resource drl to this file system
        addDrlFiles(kfs,scanRoot);

        /**everything (module,model,session,drl source) has been added into the service,
         build KieFileSystem and create a container*/
        ks.newKieBuilder( kfs ).buildAll();
        KieContainer kieContainer = ks.newKieContainer(ks.getRepository().getDefaultReleaseId());
        return kieContainer;
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

    /**
     * thorougly scan the root and register all the sessions based on their parent folder name
     * @param scanRoot
     * @param kieBaseModel
     */
    private void appendKieSession(String scanRoot, KieBaseModel kieBaseModel) {
        if(scanRoot==null||"".equals(scanRoot)){
            return;
        }

    }

    private void insertKieBase(String scanRoot){
        File folder=new File(scanRoot);
        File[] files=folder.listFiles();
        for(File file:files){
            if(file.isDirectory()){
                kieBaseList.add(file.getName());
            }
        }
    }

//    /**
//     *
//     * @param kieBaseModel
//     */
//    private void registerKieSession(KieBaseModel kieBaseModel){
//        if(kieSessionList.size()>0) {
//            for(String sessionName:kieSessionList) {
//                kieBaseModel.newKieSessionModel(sessionName)
//                        .setDefault(true)
//                        .setType(KieSessionModel.KieSessionType.STATEFUL)
//                        .setClockType(ClockTypeOption.get("realtime"));
//            }
//        }
//    }


}
