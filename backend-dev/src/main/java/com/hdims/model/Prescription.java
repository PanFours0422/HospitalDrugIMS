package com.hdims.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Prescription class represents the prescription entity in the system.
 */
public class Prescription implements Serializable {
    private static final long serialVersionUID = 1L;

    private int pno;       // 编号
    private String pid;    // 病人身份证号码
    private String dno;    // 开出医生编号
    private Date ptime;    // 开出时间
    private String nno;    // 处理护士编号
    private Date htime;    // 处理时间
    private boolean pstate;// 状态 (1 为已处理，0 为未处理)

    // Getters and Setters

    public int getPno() {
        return pno;
    }

    public void setPno(int pno) {
        this.pno = pno;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDno() {
        return dno;
    }

    public void setDno(String dno) {
        this.dno = dno;
    }

    public Date getPtime() {
        return ptime;
    }

    public void setPtime(Date ptime) {
        this.ptime = ptime;
    }

    public String getNno() {
        return nno;
    }

    public void setNno(String nno) {
        this.nno = nno;
    }

    public Date getHtime() {
        return htime;
    }

    public void setHtime(Date htime) {
        this.htime = htime;
    }

    public boolean isPstate() {
        return pstate;
    }

    public void setPstate(boolean pstate) {
        this.pstate = pstate;
    }
}
