package com.lhd.autocode.service;

import com.alibaba.fastjson.JSON;
import com.lhd.autocode.bean.Column;
import com.lhd.autocode.bean.Table;
import com.lhd.autocode.enums.EmJavaType;
import com.lhd.autocode.enums.EmJdbcType;
import com.lhd.autocode.enums.EmYesNo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lvhaodong on 2016/7/18.
 */
public class TableFiller {

    private Logger logger = Logger.getLogger(TableFiller.class);

    private List<String> splitters;

    private Map<String, Map<String, Integer>> queryLikes;

    private Map<String, Map<String, Integer>> queryTimeBetweens;

    public TableFiller() {
        this.splitters = new ArrayList<String>();
        this.splitters.add(" ");
        this.splitters.add("(");
        this.splitters.add("（");
    }

    public List<String> getSplitters() {
        return splitters;
    }

    public void setSplitters(List<String> splitters) {
        this.splitters = splitters;
    }

    public void addSplitter(String splitter){
        if(this.splitters == null)
            this.splitters = new ArrayList<String>();
        this.splitters.add(splitter);
    }

    public void addQueryLike(String tableName, String queryLike){
        if(this.queryLikes == null)
            this.queryLikes = new HashMap<String, Map<String, Integer>>();
        if (this.queryLikes.get(tableName) == null)
            this.queryLikes.put(tableName, new HashMap<String, Integer>());
        this.queryLikes.get(tableName).put(queryLike, 0);
    }

    public void addQueryTimeBetween(String tableName, String queryTimeBetween){
        if(this.queryTimeBetweens == null)
            this.queryTimeBetweens = new HashMap<String, Map<String, Integer>>();
        if (this.queryTimeBetweens.get(tableName) == null)
            this.queryTimeBetweens.put(tableName, new HashMap<String, Integer>());
        this.queryTimeBetweens.get(tableName).put(queryTimeBetween, 0);
    }

    public void fillTables(List<Table> tableList){
        logger.info("filling tables...");
        if (tableList == null)
            return;
        for (Table table : tableList){
            fillTable(table);
        }
        HashMap<String, List<String>> unused = checkQueries(this.queryLikes);
        if (!unused.isEmpty()) {
            logger.warn("generating likes:\tcolumns in these tables cannot be recognized!\n" +
                    JSON.toJSONString(unused));
        }
        unused = checkQueries(this.queryTimeBetweens);
        if (!unused.isEmpty()) {
            logger.warn("generating timeBetweens:\tcolumns in these tables cannot be recognized!\n" +
                    JSON.toJSONString(unused));
        }
    }

    private void fillTable(Table table) {
        if (table == null)
            return;
        table.setTableNameLowerCase(underscoreToCamelCase(table.getTableName()));
        table.setTableNameUpperCase(underscoreToCamelCaseUpperFirst(table.getTableName()));
        table.setTableUsefulComment(cutoffRedundancy(table.getTableComment()));
        if (table.getColumns()!=null){
            for (Column column : table.getColumns()){
                fillColumn(column);
            }
        }
    }

    private void fillColumn(Column column) {
        if (column == null)
            return;
        column.setColumnNameLowerCase(underscoreToCamelCase(column.getColumnName()));
        column.setColumnNameUpperCase(underscoreToCamelCaseUpperFirst(column.getColumnName()));
        column.setJavaType(jdbcTypeToJavaType(column.getDataType()));
        column.setQueryLike(contains(queryLikes, column.getTableName(), column.getColumnName()));
        column.setQueryTimeBetween(contains(queryTimeBetweens, column.getTableName(), column.getColumnName()));
    }

    public String underscoreToCamelCase(String underscore){
        StringBuilder camelCase = new StringBuilder();
        if (!StringUtils.isBlank(underscore)){
            underscore = underscore.toLowerCase();
            String[] words = StringUtils.split(underscore, "_");
            camelCase.append(words[0]);
            for (int i=1; i<words.length; i++){
                char[] letters = words[i].toCharArray();
                letters[0] = Character.toUpperCase(letters[0]);
                camelCase.append(letters);
            }
        }
        return camelCase.toString();
    }

    public String underscoreToCamelCaseUpperFirst(String underscore){
        String camelCase = underscoreToCamelCase(underscore);
        String ret = "";
        if(StringUtils.isNotBlank(camelCase)){
            char[] letters = camelCase.toCharArray();
            letters[0] = Character.toUpperCase(letters[0]);
            ret = new String(letters);
        }
        return  ret;
    }

    public String cutoffRedundancy(String string){
        if(string == null)
            return null;
        String brief = string.trim();
        for (String s : splitters){
            brief = StringUtils.split(brief, s)[0];
        }
        return brief;
    }

    public String jdbcTypeToJavaType(String dataType){
        String javaType = "";
        if (StringUtils.isNotBlank(dataType)){
            if (dataType.equalsIgnoreCase(EmJdbcType.Int.getName()))
                javaType = EmJavaType.Integer.toString();
            else if (dataType.equalsIgnoreCase(EmJdbcType.bigint.getName()))
                javaType = EmJavaType.Long.toString();
            else if (dataType.equalsIgnoreCase(EmJdbcType.Float.getName()))
                javaType = EmJavaType.Float.toString();
            else if (dataType.equalsIgnoreCase(EmJdbcType.Double.getName()))
                javaType = EmJavaType.Double.toString();
            else if (dataType.equalsIgnoreCase(EmJdbcType.datetime.getName()))
                javaType = EmJavaType.Date.toString();
            else if (dataType.equalsIgnoreCase(EmJdbcType.timestamp.getName()))
                javaType = EmJavaType.Date.toString();
            else if (dataType.equalsIgnoreCase(EmJdbcType.decimal.getName()))
                javaType = EmJavaType.BigDecimal.toString();
            else
                javaType = EmJavaType.String.toString();
        }
        return javaType;
    }

    public String contains(Map<String, Map<String, Integer>> map, String tableName, String item){
        String ret = EmYesNo.NO.name();
        if (map != null && !map.isEmpty() && StringUtils.isNotBlank(item)){
           Map<String, Integer> mapColumn = map.get(tableName);
            if (mapColumn != null && mapColumn.keySet().contains(item)) {
                mapColumn.put(item, mapColumn.get(item) + 1);
                ret = EmYesNo.YES.name();
            }
        }
        return ret;
    }

    public HashMap<String, List<String>> checkQueries(Map<String, Map<String, Integer>> queries){
        HashMap<String, List<String>> unused = new HashMap<String, List<String>>();
        for (String tableName : queries.keySet()){
            List<String> list = new ArrayList<String>();
            Map<String, Integer> map = queries.get(tableName);
            for (String columnName : map.keySet()){
                Integer usedCount = map.get(columnName);
                if (usedCount <= 0)
                    list.add(columnName);
            }
            if (!list.isEmpty()){
                unused.put(tableName, list);
            }
        }
        return unused;
    }
}
