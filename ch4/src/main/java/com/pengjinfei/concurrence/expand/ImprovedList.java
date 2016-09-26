package com.pengjinfei.concurrence.expand;

import com.pengjinfei.concurrence.annotation.ThreadSafe;

import java.util.AbstractList;
import java.util.List;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description: 通过组合方式实现putIfAbsent
 *
 */
@ThreadSafe
public class ImprovedList<T> extends AbstractList<T> {

    private final List<T> list;

    public ImprovedList(List<T> list) {
        this.list = list;
    }

    public synchronized boolean putIfAbsent(T t) {
        boolean contains = list.contains(t);
        if (!contains) {
            list.add(t);
        }
        return !contains;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
