package com.pengjinfei.concurrence.executor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Pengjinfei on 16/9/27.
 * Description:
 */
public class TaskExecutionWebServer {

    private static final int NTHREADS=100;
    private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            final Socket conection=socket.accept();
            Runnable task=new Runnable() {
                @Override
                public void run() {
                    //处理请求
                }
            };
            exec.execute(task);
        }
    }
}
