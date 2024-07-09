import com.hdims.util.DatabaseUtil;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * DatabaseUtilTest 类用于测试数据库连接。
 */
public class DatabaseUtilTest {

    @Test
    public void testConnection() {
        try (Connection conn = DatabaseUtil.getConnection()) {
            Assert.assertNotNull("数据库连接失败！", conn);
            System.out.println("数据库连接成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail("数据库连接测试失败！");
        }
    }
}
