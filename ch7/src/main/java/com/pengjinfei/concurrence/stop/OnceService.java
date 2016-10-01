package com.pengjinfei.concurrence.stop;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description: 如果某个方法需要处理一批任务，并且当所有的任务都处理完成后才能返回，那么可以通过一个私有的Executor来简化服务的声明周期管理
 */
public class OnceService {

    public boolean checkMain(Set<String> hosts, long timeout, TimeUnit unit) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        /*
        之所以采用AtomicBoolean来代替volatile类型的boolean，是因为能从内部的runnable中访问hasNewMail标志，因此它必须是final类型以免被修改
         */
        final AtomicBoolean hasNewMail = new AtomicBoolean(false);
        try {
            for (String host : hosts) {
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (checkMail(host)) {
                            hasNewMail.set(true);
                        }
                    }
                });
            }
        } finally {
            service.shutdown();
            service.awaitTermination(timeout, unit);
        }
        return hasNewMail.get();
    }

    private boolean checkMail(String host) {
        return false;
    }

}
