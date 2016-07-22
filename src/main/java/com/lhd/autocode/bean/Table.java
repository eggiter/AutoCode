package com.lhd.autocode.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvhaodong on 2016/7/18.
 */
public class Table {

    /**
     * 数据库名称
     */
    private String tableSchema;

    /**
     * 数据库表名
     */
    private String tableName;

    /**
     * 表的注释
     */
    private String tableComment;

    /**
     * 表的名称，并且首字母大写
     */
    private String tableNameUpperCase;

    /**
     * 表的名称，并且首字母小写
     */
    private String tableNameLowerCase;

    /**
     * 表的注释中重要的部分
     */
    private String tableUsefulComment;

    /**
     * 该表所对应的全部列/属性
     */
    private List<Column> columns;

    public Table() {
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getTableNameUpperCase() {
        return tableNameUpperCase;
    }

    public void setTableNameUpperCase(String tableNameUpperCase) {
        this.tableNameUpperCase = tableNameUpperCase;
    }

    public String getTableNameLowerCase() {
        return tableNameLowerCase;
    }

    public void setTableNameLowerCase(String tableNameLowerCase) {
        this.tableNameLowerCase = tableNameLowerCase;
    }

    public String getTableUsefulComment() {
        return tableUsefulComment;
    }

    public void setTableUsefulComment(String tableUsefulComment) {
        this.tableUsefulComment = tableUsefulComment;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public void addColumn(Column column){
        if (this.columns == null)
            columns = new ArrayList<Column>();
        columns.add(column);
    }
}
