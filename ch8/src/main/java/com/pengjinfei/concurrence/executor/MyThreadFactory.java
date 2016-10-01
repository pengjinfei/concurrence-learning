package com.pengjinfei.concurrence.executor;

import java.util.concurrent.ThreadFactory;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description:
 * @see java.util.concurrent.Executors.PrivilegedThreadFactory
 */
public class MyThreadFactory implements ThreadFactory {
    private final String poolName;

    public MyThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new MyAppThread(r,poolName);
    }
}
