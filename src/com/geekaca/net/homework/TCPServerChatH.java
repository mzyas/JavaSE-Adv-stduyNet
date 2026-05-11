package com.geekaca.net.homework;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TCPServerChatH {
    //保存当前所有连接上来的socket对象
    public static List<Socket> onlineSocket = new ArrayList<Socket>();

    public static void main(String[] args) {
        TCPServerChatH tcpServerChatH = new TCPServerChatH();
        tcpServerChatH.startServer();
    }

    private void startServer() {

        /**
         * 目标: 以 线程池的方式 对接 socket
         */
        //创建线程池
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                300,
                500,
                10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        try {
            int port = 10011;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("serverSocket启动 " + port);
            while (true){
                //阻塞等待别人的连接     返回值,创建一个socket对象 指向当前的客户端连接
                Socket socket = serverSocket.accept();
                //把连接上的地址保存在列表
                onlineSocket.add(socket);
                System.out.println("接收到连接来自: " + socket.getRemoteSocketAddress());
                // 利用线程池 获取线程,去和客户端socket 对接,沟通
                Runnable readerRunnable = new TCPServerChatRunnableH(socket);//创建一个处理socket的实例交给Runnable执行
                pool.execute(readerRunnable);//execute方法是用来提交一个没有返回值的任务。它接收一个Runnable类型的任务，并安排线程池中的线程来执行它。
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
