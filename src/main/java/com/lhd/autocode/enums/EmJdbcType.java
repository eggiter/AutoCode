package com.lhd.autocode.enums;

/**
 * Created by lvhaodong on 2016/7/18.
 */
public enum EmJdbcType {
    Int("int"),
    Float("float"),
    Double("double"),
    bigint("bigint"),
    datetime("datetime"),
    timestamp("timestamp"),
    decimal("decimal"),
    ;

    private String name;

    EmJdbcType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
