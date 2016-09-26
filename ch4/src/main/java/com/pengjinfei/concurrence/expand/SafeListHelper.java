package com.pengjinfei.concurrence.expand;

import com.pengjinfei.concurrence.annotation.ThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description:
 * @see UnsafeListHelper
 */
@ThreadSafe
public class SafeListHelper<E> {

    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    public  boolean putIfAbsent(E e) {
        synchronized (list) {
            boolean absent = list.contains(e);
            if (absent) {
                list.add(e);
            }
            return absent;
        }
    }
}
