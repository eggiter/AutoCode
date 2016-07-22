package com.lhd.autocode.enums;

/**
 * Created by lvhaodong on 2016/7/19.
 */
public enum EmConstant {
    tableSchema, //TableDao 查询条件
    tableName, //读取配置文件 queryColumns
    TableName,
    tableNames, //TableDao 查询条件
    queryLikeColumns, //读取配置文件 queryColumns
    queryTimeBetweenColumns, //读取配置文件 queryColumns

    groupId,
    artifactId,
    projectName,

    userTypedCode,
    columnsExcluded,
    ;
    public enum EmBaseColumns{
        id,
        remark,
        metaCreated,
        metaUpdated,
        deletedMark,
        metaDeleted,
        ;
    }
}