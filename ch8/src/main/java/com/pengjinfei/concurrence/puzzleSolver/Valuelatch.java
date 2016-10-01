package com.pengjinfei.concurrence.puzzleSolver;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description:
 */
public class Valuelatch<T> {
    private T value=null;

    private final CountDownLatch done = new CountDownLatch(1);

    public boolean isSet() {
        return done.getCount() == 0;
    }

    public synchronized void setValue(T newValue) {
        if (!isSet()) {
            value=newValue;
            done.countDown();
        }
    }

    public T getValue() throws InterruptedException{
        done.await();
        synchronized (this) {
            return value;
        }
    }
}
