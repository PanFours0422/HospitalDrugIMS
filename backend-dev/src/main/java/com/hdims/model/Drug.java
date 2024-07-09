package com.hdims.model;

import java.io.Serializable;

/**
 * Drug class represents the drug entity in the system.
 */
public class Drug implements Serializable {
    private static final long serialVersionUID = 1L;

    private String pdno;   // 编号
    private String pdname; // 名称
    private int pdlife;    // 保质期 (天数)

    // Getters and Setters

    public String getPdno() {
        return pdno;
    }

    public void setPdno(String pdno) {
        this.pdno = pdno;
    }

    public String getPdname() {
        return pdname;
    }

    public void setPdname(String pdname) {
        this.pdname = pdname;
    }

    public int getPdlife() {
        return pdlife;
    }

    public void setPdlife(int pdlife) {
        this.pdlife = pdlife;
    }
}
