package com.hdims.model;

import java.io.Serializable;

/**
 * Admin class represents the admin entity in the system.
 */
public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ano;    // 编号
    private String aname;  // 姓名
    private boolean asex;  // 性别 (1 为男，0 为女)
    private int aage;      // 年龄
    private String apwd;   // 登录密码

    // Getters and Setters

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public boolean isAsex() {
        return asex;
    }

    public void setAsex(boolean asex) {
        this.asex = asex;
    }

    public int getAage() {
        return aage;
    }

    public void setAage(int aage) {
        this.aage = aage;
    }

    public String getApwd() {
        return apwd;
    }

    public void setApwd(String apwd) {
        this.apwd = apwd;
    }
}
