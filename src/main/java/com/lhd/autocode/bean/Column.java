package com.lhd.autocode.bean;

/**
 * Created by lvhaodong on 2016/7/18.
 */
public class Column {

    /**
     * 数据库名称
     */
    private String tableSchema;

    /**
     * 数据库表名称
     */
    private String tableName;

    /**
     * 列名称
     */
    private String columnName;

    /**
     * 顺序位置
     */
    private Long ordinalPosition;

    /**
     * 默认列值
     */
    private String columnDefault;

    /**
     * 可以为空 YES/NO
     */
    private String isNullable;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 精度
     */
    private Long numericPrecision;

    /**
     * 小数位数
     */
    private Long numericScale;

    /**
     * 键的类型
     */
    private String columnKey;

    /**
     * 额外信息
     */
    private String extra;

    /**
     * 列的备注
     */
    private String columnComment;

    //不在information_schema.columns表中的属性
    /**
     * 列的名称，并且首字母大写
     */
    private String columnNameUpperCase;

    /**
     * 列的名称，并且首字母小写
     */
    private String columnNameLowerCase;

    /**
     * jdbc类型(dataType)对应的java类型
     */
    private String javaType;

    /**
     * 模糊查询 like查询
     */
    private String queryLike;

    /**
     * 查询时间段
     */
    private String queryTimeBetween;

    public Column() {
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

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Long getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(Long ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    public String getColumnDefault() {
        return columnDefault;
    }

    public void setColumnDefault(String columnDefault) {
        this.columnDefault = columnDefault;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Long getNumericPrecision() {
        return numericPrecision;
    }

    public void setNumericPrecision(Long numericPrecision) {
        this.numericPrecision = numericPrecision;
    }

    public Long getNumericScale() {
        return numericScale;
    }

    public void setNumericScale(Long numericScale) {
        this.numericScale = numericScale;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getColumnNameUpperCase() {
        return columnNameUpperCase;
    }

    public void setColumnNameUpperCase(String columnNameUpperCase) {
        this.columnNameUpperCase = columnNameUpperCase;
    }

    public String getColumnNameLowerCase() {
        return columnNameLowerCase;
    }

    public void setColumnNameLowerCase(String columnNameLowerCase) {
        this.columnNameLowerCase = columnNameLowerCase;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getQueryLike() {
        return queryLike;
    }

    public void setQueryLike(String queryLike) {
        this.queryLike = queryLike;
    }

    public String getQueryTimeBetween() {
        return queryTimeBetween;
    }

    public void setQueryTimeBetween(String queryTimeBetween) {
        this.queryTimeBetween = queryTimeBetween;
    }
}
