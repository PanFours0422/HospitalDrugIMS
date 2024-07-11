package com.hdims.model;

/**
 * PrescriptionDetail 类存储处方详细信息。
 */
public class PrescriptionDetail {
    private String dname;
    private String pdname;
    private int pdnum;

    // Getters and Setters

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getPdname() {
        return pdname;
    }

    public void setPdname(String pdname) {
        this.pdname = pdname;
    }

    public int getPdnum() {
        return pdnum;
    }

    public void setPdnum(int pdnum) {
        this.pdnum = pdnum;
    }
}
