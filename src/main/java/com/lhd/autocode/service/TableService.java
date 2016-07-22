package com.lhd.autocode.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lhd.autocode.bean.Config;
import com.lhd.autocode.bean.Table;
import com.lhd.autocode.dao.TableDao;
import com.lhd.autocode.enums.EmConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lvhaodong on 2016/7/19.
 */
@Service
public class TableService {

    @Autowired
    private Config config;

    @Autowired
    private TableDao tableDao;

    private TableFiller tableFiller;

    public TableService() {
    }

    @PostConstruct
    private void init() {
        this.tableFiller = new TableFiller();
        JSONArray queryColumnsJson = config.getQueryColumnsJson();
        for (int i=0;i<queryColumnsJson.size();i++){
            JSONObject jsonObject = queryColumnsJson.getJSONObject(i);
            String tableName = jsonObject.getString(EmConstant.tableName.name());
            JSONArray queryLikeColumns = jsonObject.getJSONArray(EmConstant.queryLikeColumns.name());
            for (int j=0;j<queryLikeColumns.size();j++){
                this.tableFiller.addQueryLike(tableName, queryLikeColumns.getString(j));
            }
            JSONArray queryTimeBetweenColumns = jsonObject.getJSONArray(EmConstant.queryTimeBetweenColumns.name());
            for (int j=0;j<queryTimeBetweenColumns.size();j++){
                this.tableFiller.addQueryTimeBetween(tableName, queryTimeBetweenColumns.getString(j));
            }
        }
    }

    public List<Table> getTableList(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(EmConstant.tableSchema.name(), config.getDatabaseName());
        map.put(EmConstant.tableNames.name(), config.getTableNameList());
        List<Table> tableList = tableDao.getTableList(map);
        tableFiller.fillTables(tableList);
        return tableList;
    }
}
