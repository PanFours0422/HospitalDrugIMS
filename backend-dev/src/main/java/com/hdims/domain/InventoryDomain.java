package com.hdims.domain;

import com.hdims.model.DrugViewItem;
import com.hdims.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * InventoryDomain 类负责处理查看库存药品信息的业务逻辑。
 */
public class InventoryDomain {

    /**
     * 获取库存中的药品信息。
     *
     * @return 药品列表
     */
    public List<DrugViewItem> getInventoryDrugs() {
        List<DrugViewItem> drugs = new ArrayList<>();
        String sql = "SELECT PDno, PDname, PDlife, PDnum FROM dbo.DrugView";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DrugViewItem drug = new DrugViewItem();
                drug.setPdno(rs.getString("PDno"));
                drug.setPdname(rs.getString("PDname"));
                drug.setPdlife(rs.getInt("PDlife"));
                drug.setPdnum(rs.getInt("PDnum"));
                drugs.add(drug);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return drugs;
    }
}
