package com.hdims.model;

import java.io.Serializable;
import java.util.Date;

/**
 * InventoryDrug class represents the inventory drug entity in the system.
 */
public class InventoryDrug implements Serializable {
    private static final long serialVersionUID = 1L;

    private String pdno;    // 药品编号
    private Date pdbatch;   // 批次
    private int pdnum;      // 数量
    private String sno;     // 供应商编号
    private String sano;    // 入库库存管理员编号
    private Date stime;     // 入库时间

    // Getters and Setters

    public String getPdno() {
        return pdno;
    }

    public void setPdno(String pdno) {
        this.pdno = pdno;
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

    public Date getStime() {
        return stime;
    }

    public void setStime(Date stime) {
        this.stime = stime;
    }
}
