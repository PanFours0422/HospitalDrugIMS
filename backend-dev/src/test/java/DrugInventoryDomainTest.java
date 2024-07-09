import com.hdims.domain.DrugInventoryDomain;
import com.hdims.util.DatabaseUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * DrugInventoryDomainTest 类用于测试 DrugInventoryDomain 的功能。
 */
public class DrugInventoryDomainTest {

    private DrugInventoryDomain drugInventoryDomain;

    @Before
    public void setUp() {
        drugInventoryDomain = new DrugInventoryDomain();
        insertTestDrug();
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

    @Test
    public void testInsertInventoryDrug() {
        String pdname = "替米沙坦片";
        String pdbatch = "2023-01-01";
        int pdnum = 100;
        String sno = "S123";
        String sano = "A123";

        boolean result = drugInventoryDomain.insertInventoryDrug(pdname, pdbatch, pdnum, sno, sano);
        Assert.assertTrue("药品入库失败！", result);
    }
}
