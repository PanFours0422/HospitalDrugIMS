package com.hdims.model;

import java.io.Serializable;

/**
 * DrugViewItem class represents the drug entity along with quantity in the system.
 */
public class DrugViewItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private String pdno;   // 编号
    private String pdname; // 名称
    private int pdlife;    // 保质期 (天数)
    private int pdnum;     // 数量

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

    public int getPdnum() {
        return pdnum;
    }

    public void setPdnum(int pdnum) {
        this.pdnum = pdnum;
    }
}
