package com.pengjinfei.concurrence.boundedbuffer;

/**
 * Created by Pengjinfei on 2016/10/22.
 * Description: 将前提条件的失败传递给调用者，客户端必须在二者之间做出选择：
 * 要么容忍自旋导致的CPU时钟周期浪费，要么容忍由于休眠导致的低响应性
 */
public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

    public static void main(String[] args) {
        GrumpyBoundedBuffer<String> buffer = new GrumpyBoundedBuffer<>(24);
        while (true) {
            try {
                String take = buffer.take();
            } catch (BUfferEmptyException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    protected GrumpyBoundedBuffer(int capacity) {
        super(capacity);
    }

    public synchronized void put(V v) throws BufferFullException {
        if (isFull()) {
            throw new BufferFullException();
        }
        doPut(v);
    }

    public synchronized V take() throws BUfferEmptyException {
        if (isEmpty()) {
            throw new BUfferEmptyException();
        }
        return   doTake();
    }
}
