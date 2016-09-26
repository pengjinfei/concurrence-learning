package com.pengjinfei.concurrence.cache;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by Pengjinfei on 16/9/26.
 * Description:
 */
public class Memoizer<A, V> implements Computable<A, V> {


    private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A, V> c;

    public Memoizer(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        while (true) {
            Future<V> f = cache.get(arg);
            if (f == null) {
                Callable<V> eval = new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        return c.compute(arg);
                    }
                };
                FutureTask<V> ft = new FutureTask<V>(eval);
                /*
                "没有则添加"复合原子操作，避免了两个线程同时计算的情况
                 */
                f = cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }
            try {
                return f.get();
            } catch (CancellationException e) {
                cache.remove(arg, f);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
