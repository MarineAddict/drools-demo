package com.oyo.droolsdemo.service;

import com.oyo.droolsdemo.common.droolutil.DroolUtil;
import com.oyo.droolsdemo.entity.request.DroolsData;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
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
    }
