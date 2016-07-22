package com.lhd.autocode.enums;

import java.util.HashMap;

/**
 * Created by lvhaodong on 2016/7/18.
 */
public enum EmJavaType {
    Integer("java.lang.Integer"),
    String("java.lang.String"),
    Float("java.lang.Float"),
    Double("java.lang.Double"),
    Long("java.lang.bigint"),
    Date("java.util.Date"),
    BigDecimal("java.math.BigDecimal"),
    ;
    private String clazz;

    EmJavaType(java.lang.String clazz) {
        this.clazz = clazz;
    }

    public java.lang.String getClazz() {
        return clazz;
    }
}
