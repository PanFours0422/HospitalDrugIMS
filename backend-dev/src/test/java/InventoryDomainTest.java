package com.hdims.domain;

import com.hdims.model.DrugViewItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * InventoryDomainTest 类用于测试 InventoryDomain 的功能。
 */
public class InventoryDomainTest {

    private InventoryDomain inventoryDomain;

    @Before
    public void setUp() {
        inventoryDomain = new InventoryDomain();
    }

    @Test
    public void testGetInventoryDrugs() {
        List<DrugViewItem> drugs = inventoryDomain.getInventoryDrugs();
        Assert.assertNotNull("药品列表为空！", drugs);
        Assert.assertTrue("药品列表应包含数据！", drugs.size() > 0);

        for (DrugViewItem drug : drugs) {
            System.out.println("药品编号: " + drug.getPdno() +
                    ", 药品名称: " + drug.getPdname() +
                    ", 保质期: " + drug.getPdlife() +
                    ", 数量: " + drug.getPdnum());
        }
    }
}
