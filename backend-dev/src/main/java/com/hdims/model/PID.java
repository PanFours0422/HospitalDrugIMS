package com.hdims.model;

import java.io.Serializable;

/**
 * PID class represents the prescription drug entity in the system.
 */
public class PID implements Serializable {
    private static final long serialVersionUID = 1L;

    private int pno;       // 处方编号
    private String pdno;   // 药品编号
    private int pdnum;     // 药品数量

    // Getters and Setters

    public int getPno() {
        return pno;
    }

    public void setPno(int pno) {
        this.pno = pno;
    }

    public String getPdno() {
        return pdno;
    }

    public void setPdno(String pdno) {
        this.pdno = pdno;
    }

    public int getPdnum() {
        return pdnum;
    }

    public void setPdnum(int pdnum) {
        this.pdnum = pdnum;
    }
}
