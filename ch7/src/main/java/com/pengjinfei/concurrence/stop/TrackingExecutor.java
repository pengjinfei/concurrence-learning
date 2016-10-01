package com.pengjinfei.concurrence.stop;

import java.util.*;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description: 在ExecutorService中跟踪在关闭后被取消的任务
 */
public class TrackingExecutor extends AbstractExecutorService {

    private final ExecutorService service;
    private final Set<Runnable> tasksCancelledAtShutdown = Collections.synchronizedSet(new HashSet<Runnable>());

    public TrackingExecutor(ExecutorService service) {
        this.service = service;
    }

    public List<Runnable> getCancelledTasks() {
        if (!service.isTerminated()) {
            throw new IllegalStateException("...");
        }
        return new ArrayList<>(tasksCancelledAtShutdown);
    }

    @Override
    public void shutdown() {

    }

    @Override
    public List<Runnable> shutdownNow() {
        return null;
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void execute(Runnable command) {
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    command.run();
                } finally {
                    if (!isShutdown() && Thread.currentThread().isInterrupted()) {
                        tasksCancelledAtShutdown.add(command);
                    }
                }
            }
        });
    }
}
