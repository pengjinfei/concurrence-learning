package com.pengjinfei.concurrence.publish;

import java.awt.*;
import java.util.EventListener;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description: 在构造函数中注册一个监听器或者启动线程，会导致this引用的逃逸
 */
public class EscapeListener {

    private final int num;

    public EscapeListener(EventSource source) {
        source.registerListener(new EventListener() {

            public void onEvent(Event event) {
                doSomething(event);
            }
        });
        num=42;
    }

    private void doSomething(Event event) {
        if (num != 42) {
            System.out.println("Race condition detected.");
        }
    }
}
