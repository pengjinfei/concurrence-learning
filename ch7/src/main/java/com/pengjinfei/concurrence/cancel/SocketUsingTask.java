package com.pengjinfei.concurrence.cancel;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description: 采用newTaskFor来封装非标准的取消
 */
public abstract class SocketUsingTask<T> implements CancellableTask<T>{

    private Socket socket;

    public synchronized void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public synchronized void cancel() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    定制FutureTask，使cancel方法封装非标准的取消
     */
    @Override
    public RunnableFuture<T> newTask() {
        return new FutureTask<T>(this){
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                try {
                    SocketUsingTask.this.cancel();
                } finally {
                    return super.cancel(mayInterruptIfRunning);
                }
            }
        };
    }
}

interface CancellableTask<T> extends Callable<T> {
    void cancel();

    RunnableFuture<T> newTask();
}

/*
修改newTaskFor方法来返回定制的FutureTask
 */
class CancellingExecutor extends ThreadPoolExecutor {

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        if (callable instanceof CancellableTask) {
            CancellableTask cancellableTask = (CancellableTask) callable;
            return cancellableTask.newTask();
        }
        return super.newTaskFor(callable);
    }
}
