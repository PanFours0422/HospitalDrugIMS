import com.hdims.domain.AdminDomain;
import org.junit.Assert;
import org.junit.Test;

/**
 * AdminDomainTest 类用于测试 AdminDomain 的登录功能。
 */
public class AdminDomainTest {

    @Test
    public void testAdminLogin() {
        AdminDomain adminDomain = new AdminDomain();
        boolean loginResult = adminDomain.login("admin0001", "admin0001"); // 使用测试账号和密码
        Assert.assertTrue("库存管理员登录测试失败！", loginResult);
    }
}
