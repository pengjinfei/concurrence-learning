package com.pengjinfei.concurrence.stop;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description: 通过毒丸对象来关闭服务
 * 只有在生产者和消费者数量都已知的情况下，才可以使用毒丸对象。
 */
public class IndexingService {

    private static final File POSITION = new File("");
    private final CrawlerThread producer=new CrawlerThread();
    private final IndexerThread consumer = new IndexerThread();
    private final BlockingQueue<File> queue;
    private FileFilter fileFilter;
    private final File root;

    public IndexingService(FileFilter fileFilter, File root) {
        queue = new LinkedBlockingDeque<>(20);
        this.fileFilter = fileFilter;
        this.root = root;
    }

    public void start() {
        producer.start();
        consumer.start();
    }

    public void stop() {
        producer.interrupt();
    }

    public void awaitTermination() throws InterruptedException {
        consumer.join();
    }

    private class CrawlerThread extends Thread {

        @Override
        public void run() {
            try {
                crawl(root);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                while (true) {
                    try {
                        queue.put(POSITION);
                        break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void crawl(File root) throws InterruptedException {
        }
    }
    private class IndexerThread extends Thread{
        @Override
        public void run() {
            try {
                while (true) {
                    File file=queue.take();
                    if (file == POSITION) {
                        break;
                    } else {
                        //开始索引
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
