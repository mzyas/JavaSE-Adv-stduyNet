package com.geekaca.net.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class TCPServer {
    public static void main(String[] args) {
        /**
         * TCP服务端
         * 1.占据9999端口,等待别人连接
         * 2.当有人连接上来后,读取对方的数据显示
         */
        //1.绑定,占据9999端口
        try {
            int port = 9999;
            System.out.println("服务端启动.... " + port);
            ServerSocket serverSocket = new ServerSocket(port);
            //阻塞等待别人的连接     返回值,创建一个socket对象 指向当前的客户端连接
            Socket socket = serverSocket.accept();
            SocketAddress remoteAddr = socket.getRemoteSocketAddress();
            System.out.println("有连接.... " + remoteAddr);
            //2. server读取client客户端发送来的数据
            InputStream ips = socket.getInputStream();
            //打开对方的输出流,向对方发送数据
            OutputStream ops = socket.getOutputStream();
            PrintStream ps = new PrintStream(ops);
            ps.println("欢迎连接.....");
            ps.flush();
            //字节流 ---> 字符流
            BufferedReader br = new BufferedReader(new InputStreamReader(ips));
            //固定套路读取 , 循环,不断等待客户端发送数据来
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(remoteAddr + " 说: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
