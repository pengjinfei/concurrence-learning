package com.pengjinfei.concurrence.producerConsumer;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description:
 */
public class FileCrawler implements Runnable {

    private final BlockingQueue<File> fileQueue;
    private final FileFilter fileFilter;
    private final File root;

    public FileCrawler(BlockingQueue<File> fileBlockingQueue, FileFilter fileFilter, File root) {
        this.fileQueue = fileBlockingQueue;
        this.fileFilter = fileFilter;
        this.root = root;
    }

    @Override
    public void run() {
        try {
            crawl(root);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected void crawl(File root) throws InterruptedException{
        File[] files = root.listFiles(fileFilter);
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    crawl(file);
                }else{
                    fileQueue.put(file);
                }
            }
        }
    }
}
