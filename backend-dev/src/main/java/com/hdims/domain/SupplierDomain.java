package com.hdims.domain;

import com.hdims.util.DatabaseUtil;

import java.sql.*;

/**
 * SupplierDomain 类提供供应商管理的功能。
 */
public class SupplierDomain {

    /**
     * 添加供应商记录。
     *
     * @param sno 供应商编号
     * @param sname 供应商名称
     * @param saddr 供应商地址
     * @param sphone 供应商电话
     * @return 添加是否成功
     */
    public boolean addSupplier(String sno, String sname, String saddr, String sphone) {
        String sql = "INSERT INTO Supplier (Sno, Sname, Saddr, Sphone) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sno);
            stmt.setString(2, sname);
            stmt.setString(3, saddr);
            stmt.setString(4, sphone);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改供应商记录。
     *
     * @param sno 供应商编号
     * @param sname 供应商名称
     * @param saddr 供应商地址
     * @param sphone 供应商电话
     * @return 修改是否成功
     */
    public boolean updateSupplier(String sno, String sname, String saddr, String sphone) {
        String sql = "UPDATE Supplier SET Sname = ?, Saddr = ?, Sphone = ? WHERE Sno = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sname);
            stmt.setString(2, saddr);
            stmt.setString(3, sphone);
            stmt.setString(4, sno);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除供应商记录，同时更新相关药品记录。
     *
     * @param sno 供应商编号
     * @return 删除是否成功
     */
    public boolean deleteSupplier(String sno) {
        String sqlDeleteInventory = "DELETE FROM InventoryDrug WHERE Sno = ?";
        String sqlDeleteDestroyed = "DELETE FROM DestroyedDrug WHERE Sno = ?";
        String sqlDeleteSupplier = "DELETE FROM Supplier WHERE Sno = ?";

        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtDeleteInventory = conn.prepareStatement(sqlDeleteInventory);
                 PreparedStatement stmtDeleteDestroyed = conn.prepareStatement(sqlDeleteDestroyed);
                 PreparedStatement stmtDeleteSupplier = conn.prepareStatement(sqlDeleteSupplier)) {

                // 删除 InventoryDrug 表中的相关记录
                stmtDeleteInventory.setString(1, sno);
                stmtDeleteInventory.executeUpdate();

                // 删除 DestroyedDrug 表中的相关记录
                stmtDeleteDestroyed.setString(1, sno);
                stmtDeleteDestroyed.executeUpdate();

                // 删除 Supplier 表中的记录
                stmtDeleteSupplier.setString(1, sno);
                int rowsDeleted = stmtDeleteSupplier.executeUpdate();

                conn.commit();
                return rowsDeleted > 0;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
