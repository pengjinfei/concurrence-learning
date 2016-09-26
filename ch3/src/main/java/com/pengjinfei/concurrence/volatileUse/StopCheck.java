package com.pengjinfei.concurrence.volatileUse;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description: 仅在满足以下所有条件时，才使用volative变量
 * 1.对变量的写入操作不依赖变量的当前值（boolean值最合适），或者能确保只有一个线程更新变量的值
 * 2.该变量不会与其他状态量一起纳入不变性条件中
 * 3.在访问变量时不需要加锁
 */
public class StopCheck {

    private static volatile boolean stop=false;

    private static class CheckThread extends Thread{
        @Override
        public void run() {
            while (!stop) {
                Thread.yield();
            }
            System.out.println("stop!");
        }
    }

    public static void main(String[] args) {
        new CheckThread().start();
        stop=true;
    }
}
