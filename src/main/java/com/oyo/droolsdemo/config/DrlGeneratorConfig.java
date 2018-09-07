package com.oyo.droolsdemo.config;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: create by xuqie
 * @description:
 * @date:2018/9/5
 */
@Configuration
public class DrlGeneratorConfig {


    @Bean
    public KieContainer kieContainer(){
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        return kc;
    }

}
