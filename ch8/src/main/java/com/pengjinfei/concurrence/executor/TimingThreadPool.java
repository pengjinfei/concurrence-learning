package com.pengjinfei.concurrence.executor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description: 自定义线程池
 */
public class TimingThreadPool extends ThreadPoolExecutor {
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private final Logger logger = LoggerFactory.getLogger(TimingThreadPool.class);
    private final AtomicLong numTasks = new AtomicLong();
    private final AtomicLong totoalTime = new AtomicLong();

    public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        try {
            long endTime = System.nanoTime();
            long taskTime = endTime - startTime.get();
            numTasks.incrementAndGet();
            totoalTime.addAndGet(taskTime);
            logger.info(String.format("Thread %s: end %s , time=%dns", t, r, taskTime));
        } finally {
            super.afterExecute(r, t);
        }
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        logger.info(String.format("Thread %s: start %s", t, r));
        startTime.set(System.nanoTime());
    }

    /*
    线程池关闭时输出平均任务时间
     */
    @Override
    protected void terminated() {
        try {
            logger.info("Terminated: avg time=%dns", totoalTime.get() / numTasks.get());
        } finally {
            super.terminated();
        }
    }
}
