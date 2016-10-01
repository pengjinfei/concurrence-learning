package com.pengjinfei.concurrence.jvm;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description: 处理费正常的线程终止
 */
public class AbnormalStop extends Thread{

    /*
    典型的线程池工作者线程结构
     */
    public void run() {
        Throwable throwable=null;
        try {
            while (!isInterrupted()) {
                //从队列取出任务开始工作
            }
        } catch (Throwable t) {
            throwable=t;
        } finally {
            //处理捕获的异常throwable，可能会用新的线程来替代这个线程工作
        }
    }

}
