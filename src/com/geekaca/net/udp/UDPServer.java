package com.geekaca.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * socket 套接字,插头
 */
public class UDPServer {
    public static void main(String[] args) throws IOException {
        //数据报,报文,类似电报
        //接收方(暂时称作服务端,占据8888端口,等待别人发送数据
        DatagramSocket socket = new DatagramSocket(8888);
        //充当要接受数据的载体
        byte[] buffer = new byte[100 * 64];
        /**
         * 接收端
         * 接收数据盘子 64k 这么大
         */
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        System.out.println("port:8888 等待数据.....");

        socket.receive(packet);
        System.out.println("收到数据:");

        //获取收到的数据长度
        int length = packet.getLength();
        String str = new String(buffer, 0, length);
        System.out.println("收到了: " + str);
        System.out.println("对方地址: " + packet.getSocketAddress());
        System.out.println("对方的port: " + packet.getPort());
    }
}
