package com.lhd.autocode.multithreads;

import com.lhd.autocode.bean.Config;
import com.lhd.autocode.service.TableService;
import com.lhd.autocode.velocity.VelocityBuilder;
import org.apache.velocity.VelocityContext;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by lvhaodong on 2016/7/20.
 */
public class VelocityUtilsTest {

    @Autowired
    private Config config;

    @Autowired
    private TableService tableService;

    @Autowired
    VelocityBuilder velocityBuilder;

    @Test
    public void testGetTemplate() throws Exception {

    }

    @Test
    public void testBuildVelocityContext() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-*.xml");
        context.start();
        context.registerShutdownHook();
        VelocityContext velocityContext = velocityBuilder.buildVelocityContext();
        System.out.println(velocityContext.toString());
    }

    @Test
    public void testBuildVelocityContext1() throws Exception {

    }
}