package com.pengjinfei.concurrence.visible;

/**
 * Created by Pengjinfei on 16/9/24.
 * Description:
 */
public class NoVisibility {

    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread{
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        /*
        无法保证number和ready对读线程的可见性，ReaderThread可能输出42，0，或者根本无法终止
        无法终止：读线程可能永远都看不到ready的值
        0：读线程可能看到了写入的ready的值，但是没有看到number写入的值，这种现象称为"重排序"
         */
        number=42;
        ready=true;
    }
}
