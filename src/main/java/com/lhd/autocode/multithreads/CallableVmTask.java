package com.lhd.autocode.multithreads;

import com.lhd.autocode.bean.Table;
import com.lhd.autocode.velocity.GenerateCode;

import java.io.File;
import java.util.concurrent.Callable;

/**
 * Created by lvhaodong on 2016/7/20.
 */
public class CallableVmTask implements Callable<Message> {

    private Table table;

    private File source;

    private File target;

    private GenerateCode generateCode;

    public CallableVmTask(GenerateCode generateCode, File source, File target, Table table) {
        this.generateCode = generateCode;
        this.source = source;
        this.target = target;
        this.table = table;
    }

    public CallableVmTask(GenerateCode generateCode, File source, File target) {
        this(generateCode, source, target, null);
    }

    public Message call() throws Exception {
        if(generateCode.generateFile(source, target, table))
            return Message.success(target.getName() + "文件生成成功！");
        return Message.fail(target.getName() + "文件生成失败！", table);
    }
}
