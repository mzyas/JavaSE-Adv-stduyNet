package com.geekaca.net.homework;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * 处理单个客户端连接
 * 消息收发 消息广播
 * 实现了 Runnable 接口 它的实例可以作为线程运行
 */
public class TCPServerChatRunnableH implements Runnable{
    private Socket socket;

    public TCPServerChatRunnableH(Socket socket) {
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
            PrintStream ps = new PrintStream(ops);//字节流
            ps.println("欢迎连接...");
            ps.flush();

            //字节流 ---> 字符流
            BufferedReader br = new BufferedReader(new InputStreamReader(ips));
            //固定套路读取 , 循环,不断等待客户端发送数据来
            String line = null;
            while ((line = br.readLine()) != null) {
                String say = remoteAddr + " 说: " + line;
                System.out.println(say);
                //进行广播
                for (int i = 0; i < TCPServerChatH.onlineSocket.size(); i++) {
                    //遍历每一个当前的连接
                    Socket socket = TCPServerChatH.onlineSocket.get(i);
                    if (socket == this.socket){
                        //说明当前遍历到的是 发言者 socket , 那么就不用给他发消息了,因为是他自己说的
                        continue;
                    }
                    //打开输出流
                    OutputStream opsServerChat = socket.getOutputStream();
                    PrintStream psServerChat = new PrintStream(opsServerChat);
                    //发送消息(广播)
                    psServerChat.println(say);//输出字节(字节等客户端转换为字符)
                    psServerChat.flush();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
