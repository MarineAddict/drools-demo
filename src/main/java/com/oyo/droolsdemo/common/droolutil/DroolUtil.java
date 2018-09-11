package com.oyo.droolsdemo.common.droolutil;

import lombok.Data;
import org.kie.api.KieServices;
import org.kie.api.definition.KiePackage;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author: create by xuqie
 * @description:
 * @date:2018/9/10
 * Drool的常用操作类
 */
@Component
@Data
public class DroolUtil {

    @Autowired
    private KieServices KieServices;
    @Autowired
    private DefaultListableBeanFactory beanFactory;
    @Autowired
    private ApplicationContext ctx;

    /**
     * 这里只保存一个container
     */
    public static KieContainer kieContainer;
    private static ApplicationContext applicationContext;
    private static DefaultListableBeanFactory DefaultListableBeanFactory;

    @PostConstruct
    public void initContainer(){
        kieContainer=KieServices.newKieContainer(KieServices.getRepository().getDefaultReleaseId());
//        List<KiePackage> packages= (List<KiePackage>) kieContainer.getKieBase("").getKiePackages();
////        for(KiePackage p:packages){
//         p.getProcesses().clear();
//        }
        applicationContext=ctx;
        DefaultListableBeanFactory=beanFactory;
    }

    /**
     * 对所有的drl文件重新加载，重新获得一个container，（在调用了更新或者删除规则操作后）可以调用这个方法得到一个最新的container
     */
    public static void reload(){
        kieContainer.dispose();
        KieServices kieServices= (KieServices) applicationContext.getBean("kieServices");
        DefaultListableBeanFactory.destroySingleton("kieServices");
        kieContainer=kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
    }

    /**
     * 得到当前container种的某个kiesession
     */
    public static KieSession getKieSession(String sessionName){
       return kieContainer.newKieSession(sessionName);
    }


}
