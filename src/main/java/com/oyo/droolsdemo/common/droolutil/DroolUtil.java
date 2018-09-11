package com.oyo.droolsdemo.common.droolutil;

import lombok.Data;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
    private KieFileSystem kieFileSystem;
    @Autowired
    private DefaultListableBeanFactory beanFactory;
    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private KieServices kieServices;

    /**
     * 这里只保存一个container
     */
    public static KieContainer KIECONTAINER;
    private static ApplicationContext applicationContext;
    private static DefaultListableBeanFactory DefaultListableBeanFactory;
    /**
     * 这里只保存一个FILESYSTEM
     */
    public static KieFileSystem KIEFILESYSTEM;

    /**
     * 初始化所有的静态资源
     */
    @PostConstruct
    public void initContainer(){
        KIEFILESYSTEM=kieFileSystem;
        KIECONTAINER =kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        applicationContext=ctx;
        DefaultListableBeanFactory=beanFactory;
    }



    /**
     * 对所有的drl文件重新加载，重新获得一个container，
     * 可以调用这个方法得到一个最新的container
     */
    @Deprecated
    public static void reloadAll(){
        KIECONTAINER.dispose();
        KieServices kieServices= (KieServices) applicationContext.getBean("kieFileSystem");
        DefaultListableBeanFactory.destroySingleton("kieServices");
        KIECONTAINER =kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
    }

    public static void reloadFile(String fileRoot,Resource re){
        KIEFILESYSTEM.write(fileRoot,re);
    }


    /**
     * 从当前KieFileSystem种删除掉某个drl
     * @param path
     */
    public static void deleteFromKieFileSystem(String path){
        KIEFILESYSTEM.delete(new String[]{});
    }

    /**
     * 得到当前container种的某个kiesession
     */
    public static KieSession getKieSession(String sessionName){
       return KIECONTAINER.newKieSession(sessionName);
    }


}
