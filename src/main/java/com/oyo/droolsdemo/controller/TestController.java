package com.oyo.droolsdemo.controller;

import com.oyo.droolsdemo.common.droolutil.DrlFileUtil;
import com.oyo.droolsdemo.common.droolutil.DroolUtil;
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

    @Autowired
    private DroolUtil droolUtil;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/demo")
    @ResponseBody
    public String demo(@RequestBody DroolsData droolsData){
        DrlFileUtil.generateDrlFile(droolsData);
        DroolUtil.reloadAll();
        return "sucess";
    };


    @RequestMapping("/testDroolExecution")
    @ResponseBody
    public void testDroolExecution(){
        Customer customer=new Customer();
        customer.setName("login session : ");
        customer.setVip(true);
        customer.setDeposit(30002.0);
        customer.setComsumptionLevel(Customer.ComsumptionLevel.HIGH);
        droolsService.validateByDrools("login",customer);
    }

    @RequestMapping("/testDroolSession")
    @ResponseBody
    public void testDroolExecutionBatch(){
        Customer customer=new Customer();
        customer.setName("register session : ");
        customer.setVip(true);
        customer.setDeposit(30002.0);
        customer.setComsumptionLevel(Customer.ComsumptionLevel.HIGH);
        droolsService.validateByDrools("register",customer);
    }







}
