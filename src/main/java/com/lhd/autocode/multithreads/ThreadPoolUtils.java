package com.lhd.autocode.multithreads;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by lvhaodong on 2016/7/20.
 */
public class ThreadPoolUtils {
    private static final int N_THREADS = 10;

    private static ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);

    public static Future<Message> submit(Callable<Message> callable){
        return executorService.submit(callable);
    }

    public static void shutdown(){
        executorService.shutdown();
    }
}