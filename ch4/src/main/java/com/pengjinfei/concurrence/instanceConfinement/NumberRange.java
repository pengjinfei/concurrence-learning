package com.pengjinfei.concurrence.instanceConfinement;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description: 即使NumberRange的各个状态组成部分都是线程安全的，但却造成委托失效
 */
public class NumberRange {

    /*
    不变性条件：lower<=upper
     */
    private final AtomicInteger lower = new AtomicInteger(0);
    private final AtomicInteger upper = new AtomicInteger(0);

    public void setLower(int i) {
        if (i > upper.get()) {
            throw new IllegalArgumentException("Cannot set lower to " + i + "> upper");
        }
        lower.set(i);
    }

    public void setUpper(int i) {
        if (i < lower.get()) {
            throw new IllegalArgumentException("Cannot set upper to " + i + "< lower");
        }
        upper.set(i);
    }

    public boolean isRange(int i) {
        return (i >= lower.get()) && (i <= upper.get());
    }
}
