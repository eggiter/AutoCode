package com.lhd.autocode.dao;

import com.alibaba.fastjson.JSON;
import com.lhd.autocode.bean.Column;
import com.lhd.autocode.bean.Table;
import com.lhd.autocode.service.TableFiller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lvhaodong on 2016/7/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-*.xml"})
public class TableDaoTest {

    @Autowired
    private TableDao tableDao;

    @Test
    public void testGetTableList() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>(2);
        List<String> list = new ArrayList<String>();
        list.add("matchs");
        list.add("buy_queue");
        map.put("tableSchema", "yszk");
        map.put("tableNames", list);
        List<Table> tableList = tableDao.getTableList(map);
        System.out.println(tableList.size());
        Table table = tableList.get(0);
        System.out.println(JSON.toJSONString(table));
        List<Column> columnList = table.getColumns();
        System.out.println(columnList.size());
        System.out.println(JSON.toJSONString(columnList.get(0)));
    }


    @Test
    public void testFillTable(){
        TableFiller fillTable = new TableFiller();
        Map<String, Object> map = new HashMap<String, Object>(2);
        List<String> list = new ArrayList<String>();
        list.add("matchs");
        map.put("tableSchema", "yszk");
        map.put("tableNames", list);
        List<Table> tableList = tableDao.getTableList(map);
        System.out.println(tableList.size());
        fillTable.addQueryLike("matchs", "finance_plan_name");
        fillTable.addQueryLike("matchs", "shit");
        fillTable.addQueryTimeBetween("matchs", "create_time");
        // fill tables
        fillTable.fillTables(tableList);
        Table table = tableList.get(0);
        System.out.println(JSON.toJSONString(table));
        List<Column> columnList = table.getColumns();
        System.out.println(columnList.size());
        System.out.println(JSON.toJSONString(columnList.get(0)));
    }


    @Value("${db.username}")
    private String string;

    @Value("${groupId}")
    private String groupId;

    @Test
    public void  testProps(){
        System.out.println(string);
        System.out.println(groupId);
    }
}