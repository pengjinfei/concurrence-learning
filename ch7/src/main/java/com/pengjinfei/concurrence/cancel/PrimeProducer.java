package com.pengjinfei.concurrence.cancel;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Pengjinfei on 16/9/29.
 * Description:通常，中断是实现取消的最合理方式
 */
public class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;
    private volatile boolean cancelled=false;

    public PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        BigInteger p=BigInteger.ONE;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                queue.put(p=p.nextProbablePrime());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        interrupt();
    }
}
