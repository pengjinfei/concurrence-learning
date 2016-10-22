package com.pengjinfei.concurrence.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created by Pengjinfei on 2016/10/22.
 * Description: 使用AbstractQueuedSynchronizer实现二元闭锁
 */
public class OneShotLatch {

    private final Sync sync = new Sync();

    public void signal() throws InterruptedException {
        sync.releaseShared(0);
    }

    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(0);
    }

    private class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected int tryAcquireShared(int arg) {
            // 如果闭锁是开的（getState() == 1），那么这个操作将成功，否则将失败
            return getState() == 1 ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            setState(-1); //打开闭锁
            return true; //现在其他的线程可以获得该闭锁
        }
    }

}
