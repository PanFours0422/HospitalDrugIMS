package com.hdims.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// 服务器主类，负责监听客户端连接并创建处理线程
public class Server {
    private static final int PORT = 8989;  // 定义服务器端口号

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("服务器已启动，等待连接...");
            while (true) {
                // 接受客户端连接
                Socket clientSocket = serverSocket.accept();
                // 提示客户端成功连接
                System.out.println("客户端已连接: " + clientSocket.getInetAddress().getHostAddress());
                // 为每个客户端连接创建一个新的处理线程
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
