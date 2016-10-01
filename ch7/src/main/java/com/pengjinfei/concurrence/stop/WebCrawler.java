package com.pengjinfei.concurrence.stop;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description: 网络爬虫程序在必须关闭时，无论是还没有开始的任务，还是那些被取消的任务，都记录他们的URL，以便后续重启时继续开始未完成的任务
 */
public abstract class WebCrawler {

    private volatile TrackingExecutor executor;
    private final Set<URL> urlsToCrawl = new HashSet<>();

    public synchronized void start() {
        executor = new TrackingExecutor(Executors.newCachedThreadPool());
        for (URL url : urlsToCrawl) {
            submitCrawlTask(url);
        }
    }

    public synchronized void stop() throws InterruptedException{
        try {
            saveUncrawled(executor.shutdownNow());
            if (executor.awaitTermination(2, TimeUnit.SECONDS)) {
                /*
                可能产生误报，一些认为已经取消的任务实际上已经执行完成
                 */
                saveUncrawled(executor.getCancelledTasks());
            }
        } finally {
            executor=null;
        }
    }

    private void saveUncrawled(List<Runnable> uncrawled) {
        for (Runnable runnable : uncrawled) {
            if (runnable instanceof CrawlTask) {
                CrawlTask crawlTask = (CrawlTask) runnable;
                urlsToCrawl.add(crawlTask.getPage());
            }
        }
    }

    private void submitCrawlTask(URL url) {
        executor.submit(new CrawlTask(url));
    }

    private class CrawlTask implements Runnable{

        private final URL url;

        public CrawlTask(URL url) {
            this.url = url;
        }

        @Override
        public void run() {
            for (URL link : processPage(url)) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
                submitCrawlTask(link);
            }
        }

        public URL getPage() {
            return url;
        }
    }

    protected abstract List<URL> processPage(URL url);
}
