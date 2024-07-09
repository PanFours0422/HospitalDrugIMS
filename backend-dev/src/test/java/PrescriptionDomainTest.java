
import com.hdims.domain.PrescriptionDomain;
import com.hdims.model.PrescriptionDetail;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * PrescriptionDomainTest 类用于测试 PrescriptionDomain 的功能。
 */
public class PrescriptionDomainTest {

    private PrescriptionDomain prescriptionDomain;

    @Before
    public void setUp() {
        prescriptionDomain = new PrescriptionDomain();
    }

    // 其他已有测试方法...

    @Test
    public void testGetUnhandledPrescriptions() {
        Map<Integer, List<PrescriptionDetail>> prescriptions = prescriptionDomain.getUnhandledPrescriptions();
        Assert.assertNotNull("未处理的处方列表为空！", prescriptions);
        Assert.assertTrue("未处理的处方列表应包含数据！", !prescriptions.isEmpty());

        // 打印未处理的处方信息
        for (Map.Entry<Integer, List<PrescriptionDetail>> entry : prescriptions.entrySet()) {
            System.out.print("处方编号: " + entry.getKey());
            for (PrescriptionDetail detail : entry.getValue()) {
                System.out.print(" 医生: " + detail.getDname() + " 药品: " + detail.getPdname() + " 数量: " + detail.getPdnum());
            }
            System.out.println();
        }
    }

    @Test
    public void testHandlePrescription() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入要处理的处方编号: ");
        int pno = scanner.nextInt();

        String nno = "nurse0001";
        boolean result = prescriptionDomain.handlePrescription(pno, nno);
        Assert.assertTrue("处理处方失败！", result);

        // 验证该处方已被标记为处理
        Map<Integer, List<PrescriptionDetail>> prescriptions = prescriptionDomain.getUnhandledPrescriptions();
        Assert.assertFalse("处方未被正确标记为已处理！", prescriptions.containsKey(pno));
    }
}
