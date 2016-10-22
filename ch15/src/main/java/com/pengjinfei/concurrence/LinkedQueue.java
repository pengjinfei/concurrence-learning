package com.pengjinfei.concurrence;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Pengjinfei on 2016/10/22.
 * Description:
 * @see java.util.concurrent.ConcurrentLinkedQueue
 */
public class LinkedQueue<E> {

    private final Node<E> dummy = new Node<>(null, null);
    private final AtomicReference<Node<E>> head = new AtomicReference<>(dummy);
    private final AtomicReference<Node<E>> tail = new AtomicReference<>(dummy);

    public boolean put(E item) {
        Node<E> newNode = new Node<>(item, null);
        while (true) {
            Node<E> curTail = tail.get();
            Node<E> tailNext = curTail.next.get();
            if (curTail == tail.get()) {
                if (tailNext != null) {
                    //队列处于中间状态，推进尾节点
                    tail.compareAndSet(curTail, tailNext);
                } else {
                    //处于稳定状态，尝试插入新节点（这就会使队列处于中间状态，tail.next.get()!=null）
                    if (curTail.next.compareAndSet(null, newNode)) {
                        //插入操作成功，尝试推进尾节点
                        tail.compareAndSet(curTail, newNode);
                        return true;
                    }
                }
            }
        }
    }

    private class Node<T>{
        final T item;
        AtomicReference<Node<T>> next;

        public Node(T item, Node<T> next) {
            this.item = item;
            this.next = new AtomicReference<>(next);
        }
    }
}
