package com.lhd.autocode.multithreads;

import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Created by lvhaodong on 2016/7/20.
 */
public class CallableCopyTask implements Callable<Message> {

    private File source;

    private File target;

    public CallableCopyTask(File source, File target) {
        this.source = source;
        this.target = target;
    }

    public Message call() throws Exception {
        try{
            copyFile(source, target);
        } catch (IOException e){
            return Message.fail(target.getName() + "文件拷贝失败！", source);
        }
        return Message.success(target.getName() + "文件拷贝成功！");
    }

    private boolean copyFile(File source, File target) throws IOException {
        FileCopyUtils.copy(source, target);
        return true;
    }
}
