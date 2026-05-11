package com.geekaca.net.tcp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReaderThread  extends Thread{
    private Socket socket;

    public ClientReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " clientReader 启动了");
            // 3、从client客户端socket通信管道中得到一个字节输入流
            InputStream is = socket.getInputStream();
            // 4、把字节输入流包装成缓冲字符输入流进行消息的接收
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            // 5、按照行读取消息
            String msg = null;
            while ((msg = br.readLine()) != null){
                System.out.println(socket.getRemoteSocketAddress() + "收到了: " + msg);
            }
        } catch (Exception e) {
            System.out.println("服务端把你踢出去了~~");
            e.printStackTrace();
        }
    }
}
