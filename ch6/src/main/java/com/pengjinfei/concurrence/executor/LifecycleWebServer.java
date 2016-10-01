package com.pengjinfei.concurrence.executor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by Pengjinfei on 16/9/27.
 * Description:
 */
public class LifecycleWebServer {

    private final int NTHREADS = 100;
    private final ExecutorService exec = Executors.newFixedThreadPool(NTHREADS);

    public void start() throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (!exec.isShutdown()) {
            try {
                final Socket conection = socket.accept();
                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        handleRequest(conection);
                    }
                };
                exec.execute(task);
            } catch (RejectedExecutionException e) {
                if (!exec.isShutdown()) {
                    System.out.println("task submission rejected");
                }
            }
        }
    }

    private void stop() {
        exec.shutdown();
    }

    private void handleRequest(Socket conection) {

    }

}
