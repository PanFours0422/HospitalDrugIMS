package com.hdims.domain;

import com.hdims.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * NurseDomain 类处理护士的业务逻辑。
 */
public class NurseDomain {

    /**
     * 验证护士登录信息。
     *
     * @param nno 护士的编号
     * @param npwd 护士的密码
     * @return 如果登录成功返回 true，否则返回 false
     */
    public boolean login(String nno, String npwd) {
        String sql = "SELECT Nno, Npwd FROM Nurse WHERE Nno = ? AND Npwd = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nno);
            stmt.setString(2, npwd);
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
