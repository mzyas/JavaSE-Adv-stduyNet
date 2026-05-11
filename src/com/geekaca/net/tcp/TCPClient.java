package com.geekaca.net.tcp;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) {
        /**
         * TCP客户端
         * 参数一: hsot服务器的IP地址
         * 参数二: 服务器的端口
         */
        Scanner scanner = new Scanner(System.in);
        try (//1.创建socket 去连接本机的9999端口
             Socket clientSocket = new Socket("127.0.0.1", 9999);
             //2.向对方(server)发送数据 打开socket的输出流,为了向对方发送数据
             OutputStream ops = clientSocket.getOutputStream();
             //3.把低级的字节流包装成打印流,,目的地指向socket输出流,指向目的地server
             PrintStream ps = new PrintStream(ops);
        ) {
            System.out.println("client 连接 并发送信息");
            new ClientReaderThread(clientSocket).start();
            while (true) {
                System.out.println("请输入: ");
                String input = scanner.next();
                //输入exit 退出
                if ("exit".equals(input)){
                    break;
                }
                ps.println(input);
                //把数据发送出去
                ps.flush();
            }

//            //同样可以读取对方的数据
//            InputStream ips = clientSocket.getInputStream();
//            BufferedReader br = new BufferedReader(new InputStreamReader(ips));
//            //固定套路读取
//            String line = null;
//            while ((line = br.readLine()) != null) {
//                System.out.println("server 说: " + line);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
