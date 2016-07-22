package com.lhd.autocode.velocity;

import com.alibaba.fastjson.JSON;
import com.lhd.autocode.bean.Config;
import com.lhd.autocode.bean.Table;
import com.lhd.autocode.enums.EmJavaType;
import com.lhd.autocode.service.TableService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 这是测试类，与整个项目无关。
 * Created by lvhaodong on 2016/7/19.
 */
public class VelocityTest {

    private static Config config;

    private static TableService tableService;

    private static VelocityBuilder velocityBuilder;

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-*.xml");
        config = (Config) context.getBean("config");
        tableService = (TableService) context.getBean("tableService");
        List<Table> tableList = tableService.getTableList();
        Table table = tableList.get(0);
        System.out.println(JSON.toJSONString(table));
        System.out.println(JSON.toJSONString(config));
        System.out.println(config.getArtifactId());
        Properties properties = new Properties();
        properties.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        properties.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        properties.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        VelocityEngine velocityEngine = new VelocityEngine(properties);
        velocityEngine.init();
        String fileName = "src\\main\\resources\\vm\\test.vm";
        Template template = velocityEngine.getTemplate(fileName);

        Map<String, Object> map = null, mapTable = null;
        try {
            map = BeanUtils.describe(config);
            mapTable = BeanUtils.describe(table);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        map.remove("class");
        VelocityContext velocityContext = new VelocityContext(mapTable);
        velocityContext.put("columns", table.getColumns());
        velocityContext.put("EmJavaType", EmJavaType.values());

        System.out.println(map);
        StringWriter writer = new StringWriter();
        /**
         * start
         */

        velocityBuilder = (VelocityBuilder) context.getBean("velocityBuilder");
        try {
            String string = new File("D:\\Git\\AutoCode\\src\\main\\project\\projectName\\src\\main\\java\\groupId\\artifactId\\bean\\base\\BaseBean.java.vm").getCanonicalPath();
            template = velocityBuilder.getTemplate(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        velocityContext = velocityBuilder.buildVelocityContext(table);

        template.merge(velocityContext, writer);
        System.out.println(writer.toString());
    }
}