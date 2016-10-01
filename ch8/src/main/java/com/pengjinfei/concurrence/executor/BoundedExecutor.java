package com.pengjinfei.concurrence.executor;

import com.pengjinfei.concurrence.annotation.ThreadSafe;

import java.util.concurrent.*;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description: 使用Semaphore来控制任务的提交速率
 */
@ThreadSafe
public class BoundedExecutor {

    private final ExecutorService service;
    private final Semaphore semaphore;

    public BoundedExecutor(ExecutorService service,int bound) {
        this.service = service;
        semaphore = new Semaphore(bound);
    }

    public void submitTask(final Runnable command) throws InterruptedException{
        if (semaphore.availablePermits() == 0) {
            return;
        }
        semaphore.acquire();
        try {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        command.run();
                    } finally {
                        semaphore.release();
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        /*
        创建一个固定大小的线程池，并采用有界队列以及"调用者运行"饱和策略
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 15, 5, TimeUnit.MINUTES, new LinkedBlockingQueue<>(20));
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        /*
        对通过标准工厂方法创建的Executor进行修改
         */
        ExecutorService exec=Executors.newCachedThreadPool();
        if (exec instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) exec;
            threadPoolExecutor.setCorePoolSize(10);
        } else {
            throw new AssertionError("Oops, bad assumption");
        }
        /*
        Executors.unconfigurableExecutorService包装之后不能配置：newSingleThreadExecutor
         */
        ExecutorService unconfigurableExecutorService = Executors.unconfigurableExecutorService(exec);
    }
}
