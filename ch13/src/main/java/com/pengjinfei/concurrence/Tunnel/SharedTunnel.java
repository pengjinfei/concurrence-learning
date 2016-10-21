package com.pengjinfei.concurrence.Tunnel;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Pengjinfei on 2016/10/21.
 * Description: 在共享通信线路上发送小心
 */
public class SharedTunnel {

    private Lock lock = new ReentrantLock();

    /*
    限时的获取锁操作
     */
    public boolean trySendOnShareLine(String message, long timeout, TimeUnit unit) throws InterruptedException {
        long nanosToLock = unit.toNanos(timeout) - estimatedNanosToSend(message);
        if (!lock.tryLock(nanosToLock, TimeUnit.NANOSECONDS)) {
            return false;
        }
        try {
            return sendOnSharedLine(message);
        }finally {
            lock.unlock();
        }

    }

    /*
    可以中断的获取锁操作
     */
    public boolean interruptableSendOnSharedLine(String message) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            return cancellableSendOnSharedLine(message);
        }finally {
            lock.unlock();
        }
    }

    private boolean cancellableSendOnSharedLine(String message) throws InterruptedException {
        return false;
    }

    private boolean sendOnSharedLine(String message) {
        return false;
    }

    private long estimatedNanosToSend(String message) {
        return 0;
    }

}
