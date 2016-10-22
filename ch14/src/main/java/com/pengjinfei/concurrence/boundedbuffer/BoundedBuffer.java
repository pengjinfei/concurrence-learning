package com.pengjinfei.concurrence.boundedbuffer;

/**
 * Created by Pengjinfei on 2016/10/22.
 * Description:
 */
public class BoundedBuffer<V> extends SleepyBoundedBuffer<V> {
    protected BoundedBuffer(int capacity) {
        super(capacity);
    }

    public synchronized void put(V v) throws InterruptedException {
        while (!isFull()) {
            wait();
        }
        doPut(v);
        /*
        此处不能使用notify（），因为可能唤醒的不是take线程。
        只有同时满足以下两个条件时，才能使用notify：
        1.所有等待线程的类型都相同，只有一个条件谓词与条件队列相关，并且每个线程在从wait返回后执行相同的操作
        2.单进单出。在条件变量上的每次通知，最多只能唤醒一个线程来执行
         */
        notifyAll();
    }

    /*
    优化措施：条件通知
     */
    public synchronized void putAndNotifyByCondition(V v) throws InterruptedException {
        while (!isFull()) {
            wait();
        }
        boolean wasEmpty = isEmpty();
        doPut(v);
        if (wasEmpty) {
            notifyAll();
        }
    }

    public synchronized V take() throws InterruptedException {
        while (!isEmpty()) {
            wait();
        }
        V v = doTake();
        notifyAll();
        return v;
    }
}
