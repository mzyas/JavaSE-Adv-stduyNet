package com.geekaca.net.tcp.pool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class ServerPool {
    public static void main(String[] args) {
        /**
         * 目标: 以 线程池的方式 对接 socket
         */
        //创建线程池
        ExecutorService pool = new ThreadPoolExecutor(300,
                500,
                10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("serverSocket启动 " + 9999);
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("收到连接: " + socket.getRemoteSocketAddress());
                // 利用线程池 获取线程,去和客户端socket 对接,沟通
                Runnable readerRunnable = new ServerReaderRunnable(socket);
                pool.execute(readerRunnable);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
