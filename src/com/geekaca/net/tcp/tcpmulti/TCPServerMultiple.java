package com.geekaca.net.tcp.tcpmulti;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * 目标:一个服务端可以接受多个客户端的连接
 */

public class TCPServerMultiple {
    public static void main(String[] args) {
        /**
         * TCP服务端
         * 1.占据9999端口,等待别人连接
         * 2.当有人连接上来后,读取对方的数据显示
         */
        //1.绑定,占据9999端口
        try {
            int prot = 9999;
            System.out.println("服务端启动.... " + prot);
            ServerSocket serverSocket = new ServerSocket(prot);
            while(true){
                //阻塞等待别人的连接     返回值,创建一个socket对象 指向当前的客户端连接
                //ServerSocket类似保安大爷在门口复杂 访客的接待
                Socket socket = serverSocket.accept();
                SocketAddress remoteAddr = socket.getRemoteSocketAddress();
                System.out.println("有连接.... " + remoteAddr);
                //异步 计算机系 土木系 多线程 ,有一个连接,就创建一个线程和他对应
                SocketThread socketThread = new SocketThread(socket);
                socketThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
