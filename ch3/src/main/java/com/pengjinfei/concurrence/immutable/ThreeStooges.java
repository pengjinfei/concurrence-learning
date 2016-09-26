package com.pengjinfei.concurrence.immutable;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description:对象不可变的定义：
 * 1.对象创建以后其状态就不能修改
 * 2.对象的所有域都是final类型
 * 3.对象是正确创建的，this引用没有逃逸
 */
public class ThreeStooges {

    private final Set<String> stooges = new HashSet<>();

    public ThreeStooges() {
        stooges.add("Moe");
        stooges.add("Larry");
        stooges.add("Curly");
    }

    public boolean isStooge(String name) {
        return stooges.contains(name);
    }
}
