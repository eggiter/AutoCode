package com.lhd.autocode.velocity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by lvhaodong on 2016/7/19.
 */
public class AutoCode {

    public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-*.xml");
        context.start();
        GenerateCode generateCode = (GenerateCode) context.getBean("generateCode");
        generateCode.generateProject();
        context.registerShutdownHook();
    }
}