package com.pengjinfei.concurrence.stop;

import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description: 将管理线程的工作委托给一个ExecutorService，通过封装ExecutorService，可以将所有权链从
 * 应用程序扩展到服务以及线程，所有权链上的各个成员都将管理它所拥有的服务或者线程的声明周期
 */
public class LogServiceUseExecutor {
    private final ExecutorService service = Executors.newSingleThreadExecutor();
    private final PrintWriter writer;

    public LogServiceUseExecutor(PrintWriter writer) {
        this.writer = writer;
    }

    public void stop() throws InterruptedException {
        try {
            service.shutdown();
            service.awaitTermination(2, TimeUnit.SECONDS);
        } finally {
            writer.close();
        }
    }

    public void log(String msg) {
        try {
            service.execute(new WriterTask(msg));
        } catch (RejectedExecutionException e) {
            e.printStackTrace();
        }
    }

    private class WriterTask implements Runnable {
        private String msg;

        public WriterTask(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            writer.write(msg);
        }
    }
}
