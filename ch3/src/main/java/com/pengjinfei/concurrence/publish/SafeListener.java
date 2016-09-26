package com.pengjinfei.concurrence.publish;

import java.awt.*;
import java.util.EventListener;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description: 使用私有构造方法和工厂函数来避免this引用的逃逸
 */
public class SafeListener {

    private final EventListener listener;
    private final int num;

    private SafeListener() {
        listener=new EventListener() {
            public void onEvent(Event event) {
                doSomething(event);
            }
        };
        num=42;
    }

    private void doSomething(Event event) {
        if (num != 42) {
            System.out.println("Race condition detected.");
        }
    }

    public static SafeListener newInstance(EventSource eventSource) {
        SafeListener safeListener=new SafeListener();
        eventSource.registerListener(safeListener.listener);
        return safeListener;
    }
}
