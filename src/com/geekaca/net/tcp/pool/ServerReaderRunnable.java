package com.geekaca.net.tcp.pool;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;

public class ServerReaderRunnable implements Runnable{
    private Socket socket;

    public ServerReaderRunnable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + "启动");//当前线程的名字 + 启动
            SocketAddress remoteAddr = socket.getRemoteSocketAddress();
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
