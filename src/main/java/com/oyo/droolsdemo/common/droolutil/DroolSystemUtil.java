package com.oyo.droolsdemo.common.droolutil;

import lombok.Data;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
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
 * Drool系统的操作类
 */
@Component
@Data
public class DroolSystemUtil {

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
    private static KieServices KIESERVICES;

    /**
     * 初始化所有的静态资源
     */
    @PostConstruct
    public void initContainer(){
        KIESERVICES=kieServices;
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
        //刷新bean
        KieServices kieServices= (KieServices) applicationContext.getBean("kieServices");
        DefaultListableBeanFactory.destroySingleton("kieServices");
        KieFileSystem kieFileSystem= (KieFileSystem) applicationContext.getBean("kieFileSystem");
        DefaultListableBeanFactory.destroySingleton("kieFileSystem");
        //静态部分全部更新到最新的状态
        KIESERVICES=kieServices;
        KIEFILESYSTEM=kieFileSystem;
//        KIECONTAINER =kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        KIECONTAINER.updateToVersion(kieServices.getRepository().getDefaultReleaseId());
    }

    public static void loadFile(String fileRoot,Resource re){
        KIEFILESYSTEM.write(fileRoot,re);
    }


    /**
     * 从当前KieFileSystem种删除掉drl
     * @param paths
     */
    public static void deleteFromKieFileSystem(List<String> paths){
        if(paths.isEmpty()){
            return;
        }
        String[] strArr= paths.toArray(new String[paths.size()]);
        //删除对应msf的drl文件
        KIEFILESYSTEM.delete(strArr);
        //refresh kieservice and kiecontainer
       refreshContainer();
    }

    /**
     * 从当前KieFileSystem种删除掉drl
     * @param filePath 文件的路径，从src/main/resource/ 后的部分开始写
     */
    public static void addIntoKieFileSystem(String filePath){
        if(filePath.isEmpty()){
            return;
        }
        try {
            KIEFILESYSTEM.write(ResourceFactory.newClassPathResource(filePath).setResourceType(ResourceType.DRL));
        }catch (RuntimeException re){
            re.printStackTrace();
        }
        //refresh kieservice and kiecontainer
        refreshContainer();
    }




    /**
     * 得到当前container种的某个kiesession
     */
    public static KieSession getKieSession(String sessionName){
       return KIECONTAINER.newKieSession(sessionName);
    }

    /**
     * 刷新kiecontainer,由于kiefilesystem
     */
    public static void refreshContainer(){
       List<Message> results= KIESERVICES.newKieBuilder(KIEFILESYSTEM).buildAll().getResults().getMessages(Message.Level.WARNING);
       if(results.size()>0){
           System.out.println(results);
       }
        KIECONTAINER.updateToVersion(KIESERVICES.getRepository().getDefaultReleaseId());
    }

}
