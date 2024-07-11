package com.hdims.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * ExpiredDrugBatch 类存储过期药品批次的信息。
 */
public class ExpiredDrugBatch {
    private String pdno;
    private String pdname;
    private Date pdbatch;
    private int pdnum;
    private String sno;
    private String sano;
    private Timestamp stime;

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

    public Date getPdbatch() {
        return pdbatch;
    }

    public void setPdbatch(Date pdbatch) {
        this.pdbatch = pdbatch;
    }

    public int getPdnum() {
        return pdnum;
    }

    public void setPdnum(int pdnum) {
        this.pdnum = pdnum;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getSano() {
        return sano;
    }

    public void setSano(String sano) {
        this.sano = sano;
    }

    public Timestamp getStime() {
        return stime;
    }

    public void setStime(Timestamp stime) {
        this.stime = stime;
    }
}
