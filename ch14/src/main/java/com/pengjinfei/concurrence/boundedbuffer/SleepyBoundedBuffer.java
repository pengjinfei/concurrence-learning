package com.pengjinfei.concurrence.boundedbuffer;

/**
 * Created by Pengjinfei on 2016/10/22.
 * Description: 通过轮询与休眠来实现简单的阻塞操作
 */
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

    private static final long SLEEP_GRANULARITY = 1000L;

    protected SleepyBoundedBuffer(int capacity) {
        super(capacity);
    }

    public void put(V v) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isFull()) {
                    doPut(v);
                    return;
                }
            }
            Thread.sleep(SLEEP_GRANULARITY);
        }
    }

    public V get() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isEmpty()) {
                    return doTake();
                }
            }
            Thread.sleep(SLEEP_GRANULARITY);
        }
    }
}
