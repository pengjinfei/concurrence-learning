package com.pengjinfei.concurrence.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description: 定制Thread，记录多少线程被创建，存活
 */
public class MyAppThread extends Thread {

    private static final String DEFAULT_NAME = "MyAppThread";
    private static final AtomicInteger created = new AtomicInteger();
    private static final AtomicInteger alive = new AtomicInteger();
    public static final Logger logger = LoggerFactory.getLogger(MyAppThread.class);

    public MyAppThread(Runnable runnable) {
        this(runnable, DEFAULT_NAME);
    }

    public MyAppThread(Runnable runnable, String name) {
        super(runnable,name+"-"+created.incrementAndGet());
        setUncaughtExceptionHandler(
                new Thread.UncaughtExceptionHandler(){
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        logger.error("UncaughtException in thread:"+t.getName(),e);
                    }
                }
        );
    }

    @Override
    public void run() {
        logger.info("Created "+getName());
        try {
            alive.incrementAndGet();
            super.run();
        } finally {
            alive.decrementAndGet();
            logger.info("Exiting "+getName());
        }
    }

    public static int getThreadsCreated() {
        return created.get();
    }

    public static int getThreadsAlive() {
        return alive.get();
    }

}
