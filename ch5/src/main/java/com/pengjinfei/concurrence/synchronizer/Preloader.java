package com.pengjinfei.concurrence.synchronizer;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static com.pengjinfei.concurrence.ExceptionUtils.launderThrowable;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description: FutureTask的使用
 */
public class Preloader {

    private final FutureTask<ProductInfo> futureTask = new FutureTask<ProductInfo>(new Callable<ProductInfo>() {
        @Override
        public ProductInfo call() throws DataLoadException {
            return loadProductInfo();
        }
    });

    private final Thread thread = new Thread(futureTask);

    public void start() {
        thread.start();
    }

    /*
    如果数据已经加载，那么返回这些数据，否则等待加载完成再返回
     */
    public ProductInfo get() throws InterruptedException, DataLoadException {
        try {
            return futureTask.get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof DataLoadException) {
                throw (DataLoadException) cause;
            } else {
                throw launderThrowable(cause);
            }
        }
    }

    private ProductInfo loadProductInfo() throws  DataLoadException{
        return new ProductInfo();
    }

    static class DataLoadException extends Exception {
    }


    static class ProductInfo {

    }
}
