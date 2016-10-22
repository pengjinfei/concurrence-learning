package com.pengjinfei.concurrence;

/**
 * Created by Pengjinfei on 2016/10/22.
 * Description:
 */
public class CasCounter {
    private SimulateCAS value;

    public int getValue() {
        return value.getValue();
    }

    /*
    反复地重试是一种合理的策略，但在一些竞争很激烈的情况下，更好的方式是在重试之前先等待一段时间或者回退
    从而避免造成活锁问题
     */
    public int increment() {
        int v;
        do {
            v = value.getValue();
        } while (v != value.compareAndSwap(v, v + 1));
        return v + 1;
    }
}
