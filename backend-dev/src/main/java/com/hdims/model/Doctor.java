package com.hdims.model;

import java.io.Serializable;

/**
 * Doctor class represents the doctor entity in the system.
 */
public class Doctor implements Serializable {
    private static final long serialVersionUID = 1L;

    private String dno;    // 编号
    private String dname;  // 姓名
    private boolean dsex;  // 性别 (1 为男，0 为女)
    private int dage;      // 年龄
    private String dpwd;   // 登录密码

    // Getters and Setters

    public String getDno() {
        return dno;
    }

    public void setDno(String dno) {
        this.dno = dno;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public boolean isDsex() {
        return dsex;
    }

    public void setDsex(boolean dsex) {
        this.dsex = dsex;
    }

    public int getDage() {
        return dage;
    }

    public void setDage(int dage) {
        this.dage = dage;
    }

    public String getDpwd() {
        return dpwd;
    }

    public void setDpwd(String dpwd) {
        this.dpwd = dpwd;
    }
}
