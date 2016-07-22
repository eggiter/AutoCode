package com.lhd.autocode.dao;

import com.lhd.autocode.bean.Table;

import java.util.List;
import java.util.Map;

/**
 * Created by lvhaodong on 2016/7/18.
 */
public interface TableDao {

    List<Table> getTableList(Map<String, Object> map);
}
