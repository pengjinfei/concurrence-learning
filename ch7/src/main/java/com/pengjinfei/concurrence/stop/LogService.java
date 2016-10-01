package com.pengjinfei.concurrence.stop;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description: 向LogWriter添加可靠的取消操作
 * @see LogWriter
 */
public class LogService {
    private final BlockingQueue<String> queue;
    private final LoggerThread loggerThread;
    private final static int CAPACITY=20;
    private boolean isShutdown;
    private int reservations;

    public LogService(PrintWriter writer) {
        this.queue = new LinkedBlockingDeque<>(CAPACITY);
        isShutdown=false;
        reservations=0;
        loggerThread = new LoggerThread(writer);
    }

    public void start() {
        loggerThread.start();
    }

    /*
    任务停止时，中断loggerThread
     */
    public void stop() {
        synchronized (this) {
            isShutdown=true;
        }
        loggerThread.interrupt();
    }

    public void log(String msg) throws InterruptedException {
        synchronized (this) {
            if (isShutdown) {
                throw new IllegalArgumentException("Logservice is shutdown");
            }
            ++reservations;
        }
        queue.put(msg);
    }

    private class LoggerThread extends Thread {
        private final PrintWriter writer;

        public LoggerThread(PrintWriter writer) {
            this.writer = writer;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    synchronized (LogService.this) {
                        /*
                        只有当收到关闭指令，并且所有到达的日志已经处理完成时，线程才能停止
                         */
                        if (isShutdown && reservations == 0) {
                            break;
                        }
                    }
                    String msg=queue.take();
                    synchronized (LogService.this) {
                        --reservations;
                    }
                    writer.print(msg);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                writer.close();
            }
        }
    }
}
