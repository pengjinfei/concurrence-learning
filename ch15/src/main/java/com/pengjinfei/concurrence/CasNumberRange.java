package com.pengjinfei.concurrence;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Pengjinfei on 2016/10/22.
 * Description:
 */
public class CasNumberRange {

    private final AtomicReference<IntPair> values = new AtomicReference<>(new IntPair(0, 0));

    public int getLower() {
        return values.get().lower;
    }

    public int getUpper() {
        return values.get().upper;
    }

    public void setLower(int i) {
        while (true) {
            IntPair old = values.get();
            if (i > old.upper) {
                throw new IllegalArgumentException("Lower can't be largger than upper.");
            }
            IntPair newV = new IntPair(i, old.upper);
            if (values.compareAndSet(old, newV)) {
                return;
            }
        }
    }

    private static class IntPair{
        final int lower;
        final int upper;

        public IntPair(int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
        }
    }
}
