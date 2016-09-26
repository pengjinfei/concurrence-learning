package com.pengjinfei.concurrence.immutable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description: 事实不可变对象：对象从技术上看是可变的，但是其状态在发布后不会再改变
 */
public class EffectivelyImmutable {

    /*
    Date对象本身是可变的，但如果Date对象放入Map后就不会改变，那么synchronizedMap的同步机制足以使Date值被正确发布。
     */
    public static Map<String, Date> lastLogin = Collections.synchronizedMap(new HashMap<>());

    public static void main(String[] args) {

    }
}
