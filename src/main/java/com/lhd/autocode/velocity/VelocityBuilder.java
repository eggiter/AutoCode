package com.lhd.autocode.velocity;

import com.lhd.autocode.bean.Config;
import com.lhd.autocode.bean.Table;
import com.lhd.autocode.constant.VelocityContextValue;
import com.lhd.autocode.enums.EmConstant;
import com.lhd.autocode.enums.EmJavaType;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by lvhaodong on 2016/7/19.
 */
@Service
public class VelocityBuilder {

    private static Logger logger = Logger.getLogger(VelocityBuilder.class);

    @Autowired
    private Config config;

    private static Map<String, Object> configMap;

    private static VelocityEngine velocityEngine;

    @PostConstruct
    private void init (){
        configToProps();
        initVelocityEngine();
    }

    private void configToProps(){
        try {
            configMap = BeanUtils.describe(config);
            Map<String, String> javaTypeMap = new HashMap<String, String>();
            javaTypeMap.put(EmJavaType.Date.name(), EmJavaType.Date.getClazz());
            javaTypeMap.put(EmJavaType.BigDecimal.name(), EmJavaType.BigDecimal.getClazz());
            configMap.put("javaType", javaTypeMap);
            configMap.putAll(BeanUtils.describe(new VelocityContextValue()));
            Set<String> set = new HashSet<String>();
            for (EmConstant.EmBaseColumns item : EmConstant.EmBaseColumns.values())
                set.add(item.name());
            configMap.put(EmConstant.columnsExcluded.name(), set);
            configMap.remove("class");
        } catch (IllegalAccessException e) {
            logger.error("全局配置文件转换失败！", e);
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            logger.error("全局配置文件转换失败！", e);
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            logger.error("全局配置文件转换失败！", e);
            e.printStackTrace();
        }
    }

    private void initVelocityEngine(){
        Properties properties = new Properties();
        properties.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        properties.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        properties.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "");
        velocityEngine = new VelocityEngine(properties);
    }

    public Template getTemplate(String name){
        return velocityEngine.getTemplate(name);
    }

    public VelocityContext buildVelocityContext(){
        return buildVelocityContext(null);
    }

    public VelocityContext buildVelocityContext(Table table) {
        Map<String, Object> allProps = new HashMap<String, Object>();
        if (table != null){
            try {
                allProps.putAll(BeanUtils.describe(table));
                allProps.remove("class");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        allProps.putAll(configMap);
        VelocityContext velocityContext = new VelocityContext(allProps);
        if (table != null){
            velocityContext.put("columns", table.getColumns());
        }
        return velocityContext;
    }
}
