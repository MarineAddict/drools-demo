package com.oyo.droolsdemo.controller;

import com.oyo.droolsdemo.common.droolutil.DrlFileGeneratorUtil;
import com.oyo.droolsdemo.common.droolutil.DroolSystemUtil;
import com.oyo.droolsdemo.entity.model.Customer;
import com.oyo.droolsdemo.entity.request.DroolsData;
import com.oyo.droolsdemo.service.DroolService;
import com.oyo.droolsdemo.service.DroolsExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: create by xuqie
 * @description:
 * @date:2018/9/4
 */
@Controller
@RequestMapping("/drools")
public class TestController {


    @Autowired
    private DroolsExecuteService droolsExecuteService;
    @Autowired
    private DroolService droolService;



    @Autowired
    private DroolSystemUtil droolSystemUtil;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/demo")
    @ResponseBody
    public String demo(@RequestBody DroolsData droolsData){
        DrlFileGeneratorUtil.generateDrlFile(droolsData);
        DroolSystemUtil.reloadAll();
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
        droolsExecuteService.validateByDrools("login",customer);
    }

    @RequestMapping("/testDroolSession")
    @ResponseBody
    public void testDroolExecutionBatch(){
        Customer customer=new Customer();
        customer.setName("register session : ");
        customer.setVip(true);
        customer.setDeposit(30002.0);
        customer.setComsumptionLevel(Customer.ComsumptionLevel.HIGH);
        droolsExecuteService.validateByDrools("register",customer);
    }

  @RequestMapping("/deleteDrl")
    @ResponseBody
    public void testDeleteDrl(){
        List<String> list=new ArrayList<String>();
        list.add("src/main/resources/drool/login/login3.drl");
        droolsExecuteService.deleteFromKieFileSystem(list);
    }

    @RequestMapping("/addDrl")
    @ResponseBody
    public void testAddDrl(){
      String path ="drool/login/login3.drl";
        droolsExecuteService.addIntoKieFileSystem(path);
    }

    @RequestMapping("/testDrlFile")
    @ResponseBody
    public void testDrlFile(@RequestBody DroolsData droolsData){
        droolService.insertNewDrl(droolsData);
    }








}
