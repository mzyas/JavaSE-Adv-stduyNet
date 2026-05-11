package com.geekaca.net.homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClientRR implements Runnable {
    private Socket socket;

    public TCPClientRR(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + "客户端启动");
            // 3、从client客户端socket通信管道中得到一个字节输入流
            InputStream ips = socket.getInputStream();
            // 4、把字节输入流包装成缓冲字符输入流进行消息的接收
            BufferedReader bfr = new BufferedReader(new InputStreamReader(ips));
            // 5、按照行读取消息
            String msg = null;
            while((msg = bfr.readLine()) != null){
                System.out.println(socket.getRemoteSocketAddress() + "收到了" + msg);
            }

        } catch (IOException e) {
            System.out.println("连接断开..");
            e.printStackTrace();
        }

    }
}
