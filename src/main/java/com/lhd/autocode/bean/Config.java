package com.lhd.autocode.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvhaodong on 2016/7/18.
 */
@Component
public class Config {

    @Value("${groupId}")
    private String groupId;

    @Value("${artifactId}")
    private String artifactId;

    @Value("${author}")
    private String author;

    @Value("${databaseName}")
    private String databaseName;

    @Value("${tableNames:''}")
    private String tableNames;

    private List<String> tableNameList;

    @Value("${queryColumns:''}")
    private String queryColumns;

    private JSONArray queryColumnsJson;

    @Value("${projectName}")
    private String projectName;

    //@Value("${templateDirectory}")
    private String templateDirectory;

    @Value("${destinationDirectory}")
    private String destinationDirectory;

    public Config() {
    }

    @PostConstruct
    private void init(){
        parseTableNames();
        parseQueryColumns();
        this.templateDirectory = "src/main/project";
        System.out.println("post construct");
    }

    private void parseTableNames(){
        if (StringUtils.isBlank(this.tableNames))
            return;
        String[] tableNameArray = StringUtils.split(this.tableNames, ",");
        this.tableNameList = new ArrayList<String>();
        for (String tableName : tableNameArray){
            tableName = tableName.trim();
            if (StringUtils.isNotBlank(tableName))
                this.tableNameList.add(tableName);
        }
    }

    private void parseQueryColumns(){
        if (StringUtils.isBlank(this.queryColumns))
            return;
        this.queryColumnsJson = JSON.parseArray(this.queryColumns);
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getAuthor() {
        return author;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public List<String> getTableNameList() {
        return tableNameList;
    }

    public JSONArray getQueryColumnsJson() {
        return queryColumnsJson;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getTemplateDirectory() {
        return templateDirectory;
    }

    public String getDestinationDirectory() {
        return destinationDirectory;
    }
}
