package com.hdims.client;

import com.google.gson.Gson;
import com.hdims.server.Protocol;
import com.hdims.server.Request;
import com.hdims.server.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "localhost"; // 或者服务器的IP地址
    private static final int SERVER_PORT = 8989;              // 服务器的端口号
    private Gson gson = new Gson();

    public String login(String role, String id, String password) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // 创建请求对象
            Request request = new Request();
            request.setAction("login");
            request.setRole(role);
            request.setId(id);
            request.setPassword(password);

            // 发送请求
            out.println(Protocol.toJson(request));

            // 接收响应
            String responseJson = in.readLine();
            Response response = Protocol.fromJson(responseJson, Response.class);

            // 返回结果
            return response.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "登录失败：无法连接到服务器";
        }
    }
}
