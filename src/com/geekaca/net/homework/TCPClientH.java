package com.geekaca.net.homework;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class TCPClientH {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try (Socket clientS = new Socket("127.0.0.1",10011);
             //2.向对方(server)发送数据 打开socket的输出流,为了向对方发送数据
             OutputStream outputStream = clientS.getOutputStream();
             //3.把低级的字节流包装成打印流,,目的地指向socket输出流,指向目的地server
             PrintStream printStream = new PrintStream(outputStream);
             ) {
            System.out.println("client 连接 并发送信息");
            TCPClientRR tcpClientRR = new TCPClientRR(clientS);
            Thread tcpThead = new Thread(tcpClientRR);
            tcpThead.start();
            // 暂停主线程500毫秒，给新线程留出执行时间
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while(true){
                System.out.println("请输入:.... ");
                String input = scanner.next();
                //输入exit 退出
                if ("exit".equals(input)){
                    break;
                }

                printStream.println(input);
                //把数据发送出去
                printStream.flush();
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
