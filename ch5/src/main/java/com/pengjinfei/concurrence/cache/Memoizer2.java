package com.pengjinfei.concurrence.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Pengjinfei on 16/9/26.
 * Description:
 */
public class Memoizer2<A, V> implements Computable<A, V> {

    private final Map<A, V> cache = new ConcurrentHashMap<A, V>();
    private final Computable<A, V> c;

    public Memoizer2(Computable<A, V> c) {
        this.c = c;
    }


    /*
    可能出现两个线程同时对一个arg进行计算的情况
     */
    @Override
    public V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
