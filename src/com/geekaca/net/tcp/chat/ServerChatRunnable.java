package com.geekaca.net.tcp.chat;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;

public class ServerChatRunnable implements Runnable{
    private Socket socket;

    public ServerChatRunnable(Socket socket) {
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
                String say = remoteAddr + " 说: " + line;
                System.out.println(say);
                //进行广播
                for (int i = 0; i < ServerChat.onlineSocket.size(); i++) {
                    //遍历每一个当前的连接
                    Socket socket = ServerChat.onlineSocket.get(i);
                    if (socket == this.socket){
                        //说明当前遍历到的是 发言者 socket , 那么就不用给他发消息了,因为是他自己说的
                        continue;
                    }
                    //打开输出流
                    OutputStream ops2 = socket.getOutputStream();
                    PrintStream pps = new PrintStream(ops2);
                    //发送消息(广播)
                    pps.println(say);
                    pps.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
