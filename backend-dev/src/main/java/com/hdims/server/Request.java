package com.hdims.server;

// 用于封装客户端请求数据的类
public class Request {
    private String action;   // 操作类型（如"login", "getAllDrugs"等）
    private String role;     // 用户角色（如"Admin", "Doctor", "Nurse"）
    private String id;       // 用户ID
    private String password; // 用户密码
    private Object data;     // 其他数据

    // Getters and setters
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
