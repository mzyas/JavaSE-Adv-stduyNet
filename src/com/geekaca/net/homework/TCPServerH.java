package com.geekaca.net.homework;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class TCPServerH {
    public static void main(String[] args) {
        try {
            int port = 10011;
            System.out.println("服务端启动.... " + port);
            ServerSocket serverSocket = new ServerSocket(port);
            //阻塞等待别人的连接     返回值,创建一个socket对象 指向当前的客户端连接
            Socket socket = serverSocket.accept();
            SocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();
            System.out.println("有连接来自:" + remoteSocketAddress);
            //server读取client客户端发送来的数据
            InputStream ips = socket.getInputStream();
            OutputStream ops = socket.getOutputStream();
            PrintStream ps = new PrintStream(ops);
            ps.println("欢迎连接");
            ps.flush();
            //字节流 ---> 字符流
            BufferedReader br = new BufferedReader(new InputStreamReader(ips));
            //固定套路读取 , 循环,不断等待客户端发送数据来
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(remoteSocketAddress + " 说: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
