package com.pengjinfei.concurrence.producerConsumer;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description:
 */
public class Indexer implements Runnable {

    private final BlockingQueue<File> queue;

    public Indexer(BlockingQueue<File> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                File file = queue.take();
                //开始搜索file
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
