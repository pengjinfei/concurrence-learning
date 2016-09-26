package com.pengjinfei.concurrence.instanceConfinement;

import com.pengjinfei.concurrence.annotation.ThreadSafe;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description: 线程安全且可变
 * @see PublishingVehicleTracker
 */
@ThreadSafe
public class SafePoint {

    private int x,y;

    private SafePoint(int[] a) {
        this(a[0], a[1]);
    }

    public SafePoint(SafePoint other) {
        /*
        不是this(other.x,other.y),那样会产生竞态条件，而私有构造函数可以避免这种竞态条件
         */
        this(other.get());
    }

    public SafePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public synchronized int[] get() {
        return new int[]{x, y};
    }

    public synchronized void set(int x, int y) {
        this.x=x;
        this.y=y;
    }
}
