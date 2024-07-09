package com.hdims.model;

import java.io.Serializable;

/**
 * Nurse class represents the nurse entity in the system.
 */
public class Nurse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nno;    // 编号
    private String nname;  // 姓名
    private boolean nsex;  // 性别 (1 为男，0 为女)
    private int nage;      // 年龄
    private String npwd;   // 登录密码

    // Getters and Setters

    public String getNno() {
        return nno;
    }

    public void setNno(String nno) {
        this.nno = nno;
    }

    public String getNname() {
        return nname;
    }

    public void setNname(String nname) {
        this.nname = nname;
    }

    public boolean isNsex() {
        return nsex;
    }

    public void setNsex(boolean nsex) {
        this.nsex = nsex;
    }

    public int getNage() {
        return nage;
    }

    public void setNage(int nage) {
        this.nage = nage;
    }

    public String getNpwd() {
        return npwd;
    }

    public void setNpwd(String npwd) {
        this.npwd = npwd;
    }
}
