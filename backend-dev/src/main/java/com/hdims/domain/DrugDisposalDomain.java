package com.hdims.domain;

import com.hdims.util.DatabaseUtil;
import com.hdims.model.ExpiredDrugBatch;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DrugDisposalDomain 类提供药品销毁的功能。
 */
public class DrugDisposalDomain {

    /**
     * 获取过期的药品批次信息。
     *
     * @return 过期药品批次列表
     */
    public List<ExpiredDrugBatch> getExpiredDrugBatches() {
        List<ExpiredDrugBatch> expiredDrugBatches = new ArrayList<>();
        String sql = "SELECT PDno, PDname, PDbatch, PDnum, Sno, SAno, Stime " +
                "FROM InventoryDrugView WHERE Rdays < 0";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ExpiredDrugBatch batch = new ExpiredDrugBatch();
                batch.setPdno(rs.getString("PDno"));
                batch.setPdname(rs.getString("PDname"));
                batch.setPdbatch(rs.getDate("PDbatch"));
                batch.setPdnum(rs.getInt("PDnum"));
                batch.setSno(rs.getString("Sno"));
                batch.setSano(rs.getString("SAno"));
                batch.setStime(rs.getTimestamp("Stime"));
                expiredDrugBatches.add(batch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expiredDrugBatches;
    }

    /**
     * 销毁过期药品批次。
     *
     * @param pdno 药品编号
     * @param pdbatch 批次
     * @param dano 销毁库存管理员编号
     * @return 销毁是否成功
     */
    public boolean destroyExpiredDrugBatch(String pdno, String pdbatch, String dano) {
        String sql = "{call DestroyInventoryDrug(?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseUtil.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, pdno);
            stmt.setDate(2, java.sql.Date.valueOf(pdbatch));
            stmt.setString(3, dano);
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            stmt.registerOutParameter(5, Types.SMALLINT);

            stmt.execute();

            int returnValue = stmt.getInt(5);
            return returnValue == 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
