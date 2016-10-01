package com.pengjinfei.concurrence.jvm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description:当一个线程由于未捕获异常而退出时，JVM会把这个事件汇报给应用程序提供的UncaughtExceptionHandler异常处理器，默认输出到system.err
 * 如果希望在任务发生异常而失败时获得通知并执行一定的恢复操作，那么可以将任务封装在能捕获异常的Runnable或者Callable中，或者改写ThreadPoolExecutor的afterExecute方法
 */
public class UEHLogger implements Thread.UncaughtExceptionHandler {

    /*
    要为线程池的所有线程设置一个UncaughtExceptionHandler，需要为ThreadPoolExecutor的构造函数提供一个ThreadFactory
    只有通过execute提交的任务抛出的异常才能交给UncaughtExceptionHandler，submit则是通过future的get方法封装在ExecutionException中重新抛出
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logger logger = LoggerFactory.getLogger(UEHLogger.class);
        logger.error("Thread terminated with exception: " + t.getName(), e);
    }
}
