package com.lhd.autocode.service;

import org.junit.Test;

/**
 * Created by lvhaodong on 2016/7/18.
 */
public class TableFillerTest {

    TableFiller tableFiller = new TableFiller();
    @Test
    public void testUnderscoreToCamelCase() throws Exception {
        String underscore = "my_name_is_ethan!";
        System.out.println(tableFiller.underscoreToCamelCase(underscore));
    }

    @Test
    public void testFillTables() throws Exception {

    }

    @Test
    public void testUnderscoreToCamelCaseUpperFirst() throws Exception {

    }

    @Test
    public void testCutoffRedundancy() throws Exception {
        String string = "表名（进一步的解释）";
        System.out.println("result:\n\t" + tableFiller.cutoffRedundancy(string));
    }
}