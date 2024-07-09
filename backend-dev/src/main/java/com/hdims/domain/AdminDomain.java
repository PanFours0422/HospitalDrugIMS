package com.hdims.domain;

import com.hdims.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * AdminDomain 类处理库存管理员的业务逻辑。
 */
public class AdminDomain {

    /**
     * 验证库存管理员登录信息。
     *
     * @param ano 库存管理员的编号
     * @param apwd 库存管理员的密码
     * @return 如果登录成功返回 true，否则返回 false
     */
    public boolean login(String ano, String apwd) {
        String sql = "SELECT Ano, Apwd FROM Admin WHERE Ano = ? AND Apwd = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ano);
            stmt.setString(2, apwd);
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
