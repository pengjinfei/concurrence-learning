package com.pengjinfei.concurrence.synchronizer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Created by Pengjinfei on 16/9/26.
 * Description: Semaphore的用法
 */
public class BoundleHashSet<T> {
    private final Set<T> set;
    private final Semaphore semaphore;

    public BoundleHashSet(int boudle) {
        this.set = Collections.synchronizedSet(new HashSet<T>());
        this.semaphore = new Semaphore(boudle);
    }

    public boolean add(T t) throws InterruptedException{
        semaphore.acquire();
        boolean wasAdded=false;

        try {
            wasAdded=set.add(t);
            return wasAdded;
        } finally {
            if (!wasAdded) {
                semaphore.release();
            }
        }
    }

    public boolean remove(T t) {
        boolean wasRemoved = set.remove(t);
        if (wasRemoved) {
            semaphore.release();
        }
        return wasRemoved;
    }
}
