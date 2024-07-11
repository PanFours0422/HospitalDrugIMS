package com.hdims.domain;

import com.hdims.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

/**
 * DrugInventoryDomain 类提供药品入库的功能。
 */
public class DrugInventoryDomain {

    /**
     * 药品入库操作。
     *
     * @param pdname 药品名称
     * @param pdbatch 批次
     * @param pdnum 数量
     * @param sno 供应商编号
     * @param sano 库存管理员编号
     * @return 入库是否成功
     */
    public boolean insertInventoryDrug(String pdname, String pdbatch, int pdnum, String sno, String sano) {
        String getPdnoSql = "SELECT PDno FROM Drug WHERE PDname = ?";
        String insertInventorySql = "{call InsertInventoryDrug(?, ?, ?, ?, ?, ?, ?)}";
        String pdno = null;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement getPdnoStmt = conn.prepareStatement(getPdnoSql)) {

            // 根据药品名称获取药品编号
            getPdnoStmt.setString(1, pdname);
            try (ResultSet rs = getPdnoStmt.executeQuery()) {
                if (rs.next()) {
                    pdno = rs.getString("PDno");
                } else {
                    System.out.println("药品名称不存在！");
                    return false;
                }
            }

            // 插入药品库存记录
            try (CallableStatement insertStmt = conn.prepareCall(insertInventorySql)) {
                insertStmt.setString(1, pdno);
                insertStmt.setDate(2, java.sql.Date.valueOf(pdbatch));
                insertStmt.setInt(3, pdnum);
                insertStmt.setString(4, sno);
                insertStmt.setString(5, sano);
                insertStmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                insertStmt.registerOutParameter(7, Types.SMALLINT);

                insertStmt.execute();

                int returnValue = insertStmt.getInt(7);
                return returnValue == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
