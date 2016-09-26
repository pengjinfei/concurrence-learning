package com.pengjinfei.concurrence.visible;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description:
 */
public class MutableInteger {
    private int value;

    /*
    如果某个线程调用了set，那么另一个正在调用get的线程可能会看到更新之后的值，也可能看不到
    解决方案是对set方法和get方法同时加synchronized
     */
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
