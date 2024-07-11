package com.hdims.model;

/**
 * PrescriptionDrug 类存储处方药品的信息。
 */
public class PrescriptionDrug {
    private int pno;
    private String pdname;
    private int pdnum;

    // Getters and Setters

    public int getPno() {
        return pno;
    }

    public void setPno(int pno) {
        this.pno = pno;
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
