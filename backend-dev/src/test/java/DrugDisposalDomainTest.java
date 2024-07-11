import com.hdims.domain.DrugDisposalDomain;
import com.hdims.model.ExpiredDrugBatch;
import com.hdims.util.DatabaseUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.List;

/**
 * DrugDisposalDomainTest 类用于测试 DrugDisposalDomain 的功能。
 */
public class DrugDisposalDomainTest {

    private DrugDisposalDomain drugDisposalDomain;

    @Before
    public void setUp() {
        drugDisposalDomain = new DrugDisposalDomain();
        insertTestDrug();
        insertTestInventoryDrug();
    }

    /**
     * 插入测试药品数据到 Drug 表。
     */
    private void insertTestDrug() {
        String insertDrugSql = "INSERT INTO Drug (PDno, PDname, PDlife) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertDrugSql)) {

            stmt.setString(1, "J20180016");
            stmt.setString(2, "替米沙坦片");
            stmt.setInt(3, 365);
            stmt.executeUpdate();
        } catch (SQLException e) {
            // 如果药品已经存在，可以忽略这个异常
            if (!e.getMessage().contains("违反唯一约束条件")) {
                e.printStackTrace();
                Assert.fail("插入测试药品数据失败！");
            }
        }
    }

    /**
     * 插入测试库存药品数据到 InventoryDrug 表。
     */
    private void insertTestInventoryDrug() {
        String insertInventorySql = "INSERT INTO InventoryDrug (PDno, PDbatch, PDnum, Sno, SAno, Stime) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertInventorySql)) {

            stmt.setString(1, "J20180016");
            stmt.setDate(2, java.sql.Date.valueOf("2022-01-01")); // 过期的批次
            stmt.setInt(3, 100);
            stmt.setString(4, "S123");
            stmt.setString(5, "A123");
            stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail("插入测试库存药品数据失败！");
        }
    }

    @Test
    public void testGetExpiredDrugBatches() {
        List<ExpiredDrugBatch> expiredDrugBatches = drugDisposalDomain.getExpiredDrugBatches();
        Assert.assertNotNull("过期药品批次列表为空！", expiredDrugBatches);
        Assert.assertTrue("过期药品批次列表应包含数据！", expiredDrugBatches.size() > 0);
    }

    @Test
    public void testDestroyExpiredDrugBatch() {
        String pdno = "J20180016";
        String pdbatch = "2022-01-01";
        String dano = "A123";

        boolean result = drugDisposalDomain.destroyExpiredDrugBatch(pdno, pdbatch, dano);
        Assert.assertTrue("销毁过期药品批次失败！", result);
    }
}
