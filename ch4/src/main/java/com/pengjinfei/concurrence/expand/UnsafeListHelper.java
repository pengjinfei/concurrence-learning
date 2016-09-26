package com.pengjinfei.concurrence.expand;

import com.pengjinfei.concurrence.annotation.NoThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description:
 * @see SafeListHelper
 */
@NoThreadSafe
public class UnsafeListHelper<E> {

    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    /*
    与Collections.synchronizedList并不是同一把锁
     */
    public synchronized boolean putIfAbsent(E e) {
        boolean absent = list.contains(e);
        if (absent) {
            list.add(e);
        }
        return absent;
    }
}
