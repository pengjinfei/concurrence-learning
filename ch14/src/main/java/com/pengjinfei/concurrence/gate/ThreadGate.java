package com.pengjinfei.concurrence.gate;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Pengjinfei on 2016/10/22.
 * Description: 支持重新关闭的阀门
 */
public class ThreadGate {

    private boolean isOpen = false;
    private int generation;

    public static void main(String[] args) throws InterruptedException {
        ThreadGate gate = new ThreadGate();
        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        Random random = new Random();
                        int time = random.nextInt(5) + 2;
                        gate.await();
                        System.out.println(Thread.currentThread().getName()+" begin to execute on "+System.nanoTime());
                        //doSomething
                        TimeUnit.SECONDS.sleep(time);
                        gate.open();
                        System.out.println(Thread.currentThread().getName()+" complete execute on "+System.nanoTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        gate.open();
        gate.close();
        gate.await();
        System.out.println("main thread stop!");

    }

    public synchronized void close() {
        isOpen = false;
    }

    public synchronized void open() {
        ++generation;
        isOpen = true;
        notifyAll();
    }

    public synchronized void await() throws InterruptedException {
        int arrivalGeneration = generation;
        /*
        如果阀门在打开后又非常快速的关闭了，并且await只检查isOpen,那么可能所有等待的线程都无法释放
        arrivalGeneration == generation的检查表示阀门是否打开过
         */
        while (!isOpen && arrivalGeneration == generation) {
            wait();
        }
    }
}
