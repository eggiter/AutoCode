package com.lhd.autocode.bean;

import com.alibaba.fastjson.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lvhaodong on 2016/7/18.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-*.xml"})
public class ConfigTest {

    @Autowired
    private Config config;

    @Test
    public void testConfig() throws Exception {
        //config.init();
        System.out.println(config.getArtifactId());
        System.out.println(config.getTableNameList());
        JSONArray queryColumnJson = config.getQueryColumnsJson();
        System.out.println(queryColumnJson);
        System.out.println(queryColumnJson.getJSONObject(0));
    }
}