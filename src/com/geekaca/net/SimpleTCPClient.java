package com.geekaca.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
public class SimpleTCPClient {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int serverPort = 9999;
        try (Socket socket = new Socket(serverAddress, serverPort);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Received from server: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

