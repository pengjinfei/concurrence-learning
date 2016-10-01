package com.pengjinfei.concurrence.cancel;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Pengjinfei on 16/9/29.
 * Description:
 */
public class ResumeInterrupte {

    private final BlockingQueue<Integer> queue;

    public ResumeInterrupte(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    public Integer getNextInteger() {
        boolean interrupted=false;
        try {
            while (true) {
                try {
                    return queue.take();
                } catch (InterruptedException e) {
                    interrupted=true;
                    /*
                    如果在这里设置中断状态，重新尝试又会抛出中断异常
                     */
//                    Thread.currentThread().interrupt();
                }
            }
        } finally {
            /*
            不可取消的任务在退出前恢复中断
             */
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
