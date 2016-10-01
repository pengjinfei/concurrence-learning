package com.pengjinfei.concurrence.cancel;

import com.pengjinfei.concurrence.ExceptionUtils;

import java.util.concurrent.*;

/**
 * Created by Pengjinfei on 16/9/30.
 * Description: 在指定时间内运行一个任意的Runnable
 */
public class TimedRunner {

    private static final ScheduledExecutorService cancelExec = new ScheduledThreadPoolExecutor(10);
    private static final ExecutorService exec= Executors.newCachedThreadPool();

    public static void timedRun1(Runnable r, long timeout, TimeUnit unit) {
        final Thread taskThread=Thread.currentThread();
        cancelExec.schedule(new Runnable() {
            @Override
            public void run() {
                /*
                在中断线程之前，应该了解他的中断策略，否则不要中断线程
                 */
                taskThread.interrupt();
            }
        },timeout,unit);
        r.run();
    }

    public static void timedRun2(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        class RethrowableTask implements Runnable {
            private volatile Throwable t;
            @Override
            public void run() {
                try {
                    r.run();
                } catch (Throwable e) {
                    this.t=e;
                }
            }

            void rethrow() {
                if (t != null) {
                    //重新抛出异常
                    throw ExceptionUtils.launderThrowable(t);
                }
            }
        }

        RethrowableTask task = new RethrowableTask();
        final Thread thread = new Thread(task);
        thread.start();
        cancelExec.schedule(new Runnable() {
            @Override
            public void run() {
                thread.interrupt();
            }
        }, timeout, unit);
        /*
        即使r不响应中断，限时方法仍能返回到它的调用者
        join方法存在的不足：无法知道执行控制是因为线程正常退出而返回还是因为join超时而返回
         */
        thread.join(unit.toMillis(timeout));
        task.rethrow();
    }

    public static void timedRun3(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        Future<?> future = exec.submit(r);
        try {
            future.get(timeout,unit);
        } catch (ExecutionException e) {
            throw ExceptionUtils.launderThrowable(e.getCause());
        } catch (TimeoutException e) {
            //接下来任务将被取消
        } finally {
            /*
            如果任务正在执行，那么将被中断，如果任务已经结束，取消操作也不会带来任何影响
             */
            future.cancel(true);
        }
    }
    }
