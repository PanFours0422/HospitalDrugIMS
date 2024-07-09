package com.hdims.service;

import com.hdims.domain.AdminDomain;
import com.hdims.domain.DoctorDomain;
import com.hdims.domain.NurseDomain;

/**
 * LoginService 类处理各类用户的登录操作。
 */
public class LoginService {

    private AdminDomain adminDomain;
    private DoctorDomain doctorDomain;
    private NurseDomain nurseDomain;

    public LoginService() {
        this.adminDomain = new AdminDomain();
        this.doctorDomain = new DoctorDomain();
        this.nurseDomain = new NurseDomain();
    }

    /**
     * 管理员登录
     *
     * @param ano 管理员编号
     * @param password 管理员密码
     * @return 登录成功返回 true，否则返回 false
     */
    public boolean adminLogin(String ano, String password) {
        return adminDomain.login(ano, password);
    }

    /**
     * 医生登录
     *
     * @param dno 医生编号
     * @param password 医生密码
     * @return 登录成功返回 true，否则返回 false
     */
    public boolean doctorLogin(String dno, String password) {
        return doctorDomain.login(dno, password);
    }

    /**
     * 护士登录
     *
     * @param nno 护士编号
     * @param password 护士密码
     * @return 登录成功返回 true，否则返回 false
     */
    public boolean nurseLogin(String nno, String password) {
        return nurseDomain.login(nno, password);
    }
}
