package com.oyo.droolsdemo.controller;

import com.oyo.droolsdemo.entity.model.Customer;
import com.oyo.droolsdemo.entity.request.DroolsData;
import com.oyo.droolsdemo.service.DroolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: create by xuqie
 * @description:
 * @date:2018/9/4
 */
@Controller
@RequestMapping("/drools")
public class TestController {

    @Autowired
    private DroolsService droolsService;


    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/demo")
    @ResponseBody
    public void demo(@RequestBody DroolsData droolsData){
        droolsService.generateDrlFile(droolsData);
    };


    @RequestMapping("/testDroolExecution")
    @ResponseBody
    public void testDroolExecution(){
        Customer customer=new Customer();
        customer.setVip(true);
        customer.setComsumptionLevel(Customer.ComsumptionLevel.HIGH);
        droolsService.testDrool("ksession-rules",customer);
    }


}
