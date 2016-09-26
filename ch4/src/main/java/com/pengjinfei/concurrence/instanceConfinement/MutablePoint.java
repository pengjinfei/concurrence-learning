package com.pengjinfei.concurrence.instanceConfinement;

import com.pengjinfei.concurrence.annotation.NoThreadSafe;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description:
 */
@NoThreadSafe
public class MutablePoint {
    public int x, y;

    public MutablePoint() {
        x = 0;
        y = 0;
    }

    public MutablePoint(MutablePoint point) {
        this.x = point.x;
        this.y = point.y;
    }
}
