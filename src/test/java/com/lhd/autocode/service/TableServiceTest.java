package com.lhd.autocode.service;

import com.alibaba.fastjson.JSON;
import com.lhd.autocode.bean.Column;
import com.lhd.autocode.bean.Table;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lvhaodong on 2016/7/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-*.xml"})
public class TableServiceTest {

    @Autowired
    private TableService tableService;

    @Test
    public void testGetTableList() throws Exception {
        List<Table> tableList = tableService.getTableList();
        System.out.println("tableList size: " + tableList.size());
        Table table = tableList.get(0);
        System.out.println(JSON.toJSONString(table));
        List<Column> columnList = table.getColumns();
        System.out.println(columnList.size());
        System.out.println(JSON.toJSONString(columnList.get(0)));
    }
}