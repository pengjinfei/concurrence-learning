package com.pengjinfei.concurrence.cancel;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Pengjinfei on 16/9/29.
 * Description: 不可靠的取消操作将把生产者置于阻塞的操作中
 */
public class BrokenPrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;
    private volatile boolean cancelled=false;

    public BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            BigInteger p=BigInteger.ONE;
            while (!cancelled) {
                Thread.sleep(1000);
                /*
                如果有put方法被阻塞，就无法读取到cancelled的状态，任务可能永远无法停止
                 */
                queue.put(p=p.nextProbablePrime());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        cancelled = true;
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<BigInteger> integers = new LinkedBlockingDeque<>();
        BrokenPrimeProducer primeProducer = new BrokenPrimeProducer(integers);
        primeProducer.start();
        try {
            while (needMorePrimes()) {
                BigInteger bigInteger = integers.take();
                System.out.println("take prime:"+bigInteger);
            }
        } finally {
            primeProducer.cancel();
        }
        System.out.println(integers);
    }

    private static boolean needMorePrimes() {
        Random random = new Random();
        int nextInt = random.nextInt(20);
        return nextInt != 8;
    }

}
