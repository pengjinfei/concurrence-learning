package com.pengjinfei.concurrence.cache;

import com.pengjinfei.concurrence.ExceptionUtils;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by Pengjinfei on 16/9/26.
 * Description:
 */
public class Memoizer3<A,V> implements Computable<A,V> {

    private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A,V> c;

    public Memoizer3(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
        if (f == null) {
            Callable<V> eval=new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return c.compute(arg);
                }
            };
            FutureTask<V> ft = new FutureTask<V>(eval);
            f=ft;
            /*
            还是可能出现两个线程同时计算一个arg的情况
             */
            cache.put(arg, ft);
            ft.run();
        }
        try {
            return f.get();
        } catch (ExecutionException e) {
            throw ExceptionUtils.launderThrowable(e.getCause());
        }
    }

}
