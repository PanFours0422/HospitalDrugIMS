package com.hdims.server;

// 用于封装服务器响应数据的类
public class Response {
    private String status;  // 响应状态（如"success", "error"）
    private String message; // 响应消息
    private Object data;    // 响应数据

    // Getters and setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
