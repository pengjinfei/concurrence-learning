package com.pengjinfei.concurrence.cache;

/**
 * Created by Pengjinfei on 16/9/26.
 * Description:
 */
public interface Computable <A,V>{
    V compute(A arg) throws InterruptedException;
}
