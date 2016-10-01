package com.pengjinfei.concurrence.jvm;

import com.pengjinfei.concurrence.stop.LogService;

import java.io.PrintWriter;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description:
 */
public class ShutdownHook {

    private final PrintWriter writer;

    private final LogService logService;

    public ShutdownHook(PrintWriter writer) {
        this.writer = writer;
        logService = new LogService(writer);
    }

    public void start() {
        logService.start();
        /*
        关闭钩子不应该依赖那些可能被应用程序或者其他关闭钩子关闭的服务，实现这种功能的一种方式是对所有服务使用同一个关闭钩子，并且在
        该关闭钩子中执行一系列的关闭操作，避免关闭操作之间的竞态条件或者死锁问题
         */
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                try {
                    logService.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
