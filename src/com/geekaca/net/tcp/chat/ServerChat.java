package com.geekaca.net.tcp.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 1.服务端能够接收多个客户端的连接(ok)
 * 2.客户端要能发送消息给服务端(ok)
 * 3.服务端收到某个客户端消息的时候,要广播发送给所有客户端
 *  3.1 保存当前连接上来的客户端 List<Socket>
 *  3.2 遍历集合,逐一发送消息
 * 4.客户端要能够不断地接收来自服务端Server的消息
 */
public class ServerChat {
    //保存当前所有连接上来的socket对象
    public static List<Socket> onlineSocket = new ArrayList<>();
    public static void main(String[] args) {
        ServerChat serverChat = new ServerChat();
        serverChat.startServer();
    }

    private void startServer() {
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
                //指向客户端的socket
                Socket socket = serverSocket.accept();
                //连接上来一个,就保存一个
                onlineSocket.add(socket);
                System.out.println("收到连接: " + socket.getRemoteSocketAddress());
                // 利用线程池 获取线程,去和客户端socket 对接,沟通
                Runnable readerRunnable = new ServerChatRunnable(socket);
                pool.execute(readerRunnable);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
