package com.pengjinfei.concurrence.instanceConfinement;

import com.pengjinfei.concurrence.annotation.ThreadSafe;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description: 将线程安全委托给多个状态变量
 * @see NumberRange
 */
@ThreadSafe
public class VisualComponent {

    /*
    两个状态之间不存在耦合关系
     */
    private final List<KeyListener> keyListeners = new CopyOnWriteArrayList<>();
    private final List<MouseListener> mouseListeners = new CopyOnWriteArrayList<>();

    public void addKeyListener(KeyListener listener) {
        keyListeners.add(listener);
    }

    public void addMouseListener(MouseListener listener) {
        mouseListeners.add(listener);
    }

    public void removeKeyListener(KeyListener listener) {
        keyListeners.remove(listener);
    }

    public void removeMouseListener(MouseListener listener) {
        mouseListeners.remove(listener);
    }
}
