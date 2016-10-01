package com.pengjinfei.concurrence.stop;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description: 不支持关闭的生产者-消费者日志服务
 */
public class LogWriter {

    private final BlockingQueue<String> queue;
    private final LoggerThread logger;
    private final static int CAPACITY=20;

    public LogWriter(PrintWriter writer) {
        this.queue = new LinkedBlockingDeque<>(CAPACITY);
        this.logger = new LoggerThread(writer);
    }

    public void start() {
        logger.start();
    }

    public void log(String msg) throws InterruptedException {
        queue.put(msg);
    }

    private class LoggerThread extends Thread{
        private final PrintWriter writer;

        public LoggerThread(PrintWriter writer) {
            this.writer = writer;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    writer.print(queue.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                writer.close();
            }
        }
    }
}
