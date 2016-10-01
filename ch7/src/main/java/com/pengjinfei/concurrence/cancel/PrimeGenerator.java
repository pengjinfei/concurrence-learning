package com.pengjinfei.concurrence.cancel;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Pengjinfei on 16/9/29.
 * Description: 使用volatile来保存取消状态
 */
public class PrimeGenerator implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        /*
        一次运行一秒钟的素数生成器
         */
        PrimeGenerator generator = new PrimeGenerator();
        new Thread(generator).start();
        try {
            SECONDS.sleep(1);
        } finally {
            generator.cancel();
        }
        System.out.println(generator.get());
    }

    private final List<BigInteger> primes = new ArrayList<>();
    private volatile boolean cancelled;

    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!cancelled) {
            p=p.nextProbablePrime();
            synchronized (this) {
                primes.add(p);
            }
        }
    }

    public void cancel(){
        cancelled=true;
    }

    public synchronized List<BigInteger> get() {
        return new ArrayList<>(primes);
    }
}
