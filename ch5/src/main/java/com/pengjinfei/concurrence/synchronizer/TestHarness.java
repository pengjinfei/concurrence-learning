package com.pengjinfei.concurrence.synchronizer;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description:CountDownLatch countDown方法递减计数器，await等待计数器达到零
 * 简而言之：一个线程在等在其他的线程完成（调用countDown）
 */
public class TestHarness {

    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            new Thread(){
                @Override
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }.start();
        }

        long start=System.currentTimeMillis();
        /*
        所有程序开始启动
         */
        startGate.countDown();
        /*
        等待所有程序结束
         */
        endGate.await();
        long end=System.currentTimeMillis();
        return end-start;
    }
}
