package com.hdims.domain;

import com.hdims.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DoctorDomain 类处理医生的业务逻辑。
 */
public class DoctorDomain {

    /**
     * 验证医生登录信息。
     *
     * @param dno 医生的编号
     * @param dpwd 医生的密码
     * @return 如果登录成功返回 true，否则返回 false
     */
    public boolean login(String dno, String dpwd) {
        String sql = "SELECT Dno, Dpwd FROM Doctor WHERE Dno = ? AND Dpwd = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dno);
            stmt.setString(2, dpwd);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
