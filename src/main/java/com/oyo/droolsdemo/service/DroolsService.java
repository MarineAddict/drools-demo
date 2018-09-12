package com.oyo.droolsdemo.service;

import com.oyo.droolsdemo.common.droolutil.DroolUtil;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: create by xuqie
 * @description:
 * @date:2018/9/5
 */
@Service
public class DroolsService {

    /**
     *
     * 调用drools规则引擎
     * @param kieSessionName 调用drool的session名称
     * @param obj 传入的Fact对象
     */
    public void validateByDrools(String kieSessionName,Object obj){
        KieSession ksession = DroolUtil.getKieSession(kieSessionName);
        ksession.insert(obj);
        ksession.fireAllRules();
        ksession.dispose();
    }

//    /**
//     * 验证语法
//     * @param fileName
//     * @return
//     */
//    public String verifyCompiledDrl(String fileName){
//        KnowledgeBuilder knowledgeBuilder= KnowledgeBuilderFactory.newKnowledgeBuilder();
//        knowledgeBuilder.add(ResourceFactory.newFileResource(fileName), ResourceType.DRL);
//        KnowledgeBuilderErrors errors = knowledgeBuilder.getErrors();
//        if(errors.size()>0){
//            return errors.toString();
//        }
//        return null;
//    }

    /**
     * 将对应的drl文件从KieContainer中删除
     * @param paths
     */
    public void deleteFromKieFileSystem(List<String> paths){
        DroolUtil.deleteFromKieFileSystem(paths);
    }

    /**
     * 将对应路径的drl文件加入到KieContainer中
     */
    public void addIntoKieFileSystem(String path){
        DroolUtil.addIntoKieFileSystem(path);
    }


    }
