package com.pengjinfei.concurrence.expand;

import com.pengjinfei.concurrence.annotation.ThreadSafe;

import java.util.Vector;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description: 扩展方法比直接将代码添加到类中更脆弱，因为同步策略分散到多个单独维护的源码文件中
 */
@ThreadSafe
public class BetterVector<E> extends Vector{

    public synchronized boolean putIfAbsent(E e) {
        if (!contains(e)) {
            add(e);
            return true;
        }
        return false;
    }
}
