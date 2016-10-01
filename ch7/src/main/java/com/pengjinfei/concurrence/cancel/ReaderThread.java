package com.pengjinfei.concurrence.cancel;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by Pengjinfei on 16/10/1.
 * Description: 通过改写interrupt方法将非标准的取消操作封装至Thread中
 * @see SocketUsingTask
 */
public class ReaderThread extends Thread {
    private final Socket socket;
    private final InputStream in;

    public ReaderThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
    }

    @Override
    public void run() {
        try {
            byte[] buff = new byte[1024];
            while (true) {
                int read = in.read(buff);
                if (read < 0) {
                    break;
                } else if (read > 0) {
                    //处理读取的数据
                }
            }
        } catch (Exception e) {
            /*
            允许线程退出
             */
        }
    }

    @Override
    public void interrupt() {
        try {
            socket.close();
        } catch (IOException e) {
            //忽略
        } finally {
            super.interrupt();
        }
    }
}
