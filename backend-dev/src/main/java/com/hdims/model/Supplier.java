package com.hdims.model;

import java.io.Serializable;

/**
 * Supplier class represents the supplier entity in the system.
 */
public class Supplier implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sno;    // 编号
    private String sname;  // 名称
    private String saddr;  // 地址
    private String sphone; // 电话

    // Getters and Setters

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSaddr() {
        return saddr;
    }

    public void setSaddr(String saddr) {
        this.saddr = saddr;
    }

    public String getSphone() {
        return sphone;
    }

    public void setSphone(String sphone) {
        this.sphone = sphone;
    }
}
