package com.hdims.service;

import com.hdims.domain.DrugInventoryDomain;
import com.hdims.domain.DrugDisposalDomain;
import com.hdims.domain.InventoryDomain;
import com.hdims.domain.SupplierDomain;
import com.hdims.model.DrugViewItem;
import com.hdims.model.ExpiredDrugBatch;

import java.util.List;

/**
 * AdminService 类提供管理员的功能。
 * 包括查看药品库存、药品入库、销毁过期药品、添加供应商信息、修改供应商信息。
 */
public class AdminService {

    private InventoryDomain inventoryDomain;
    private DrugInventoryDomain drugInventoryDomain;
    private DrugDisposalDomain drugDisposalDomain;
    private SupplierDomain supplierDomain;

    public AdminService() {
        this.inventoryDomain = new InventoryDomain();
        this.drugInventoryDomain = new DrugInventoryDomain();
        this.drugDisposalDomain = new DrugDisposalDomain();
        this.supplierDomain = new SupplierDomain();
    }

    /**
     * 查看药品库存
     *
     * @return 药品库存列表
     */
    public List<DrugViewItem> viewInventory() {
        return inventoryDomain.getInventoryDrugs();
    }

    /**
     * 药品入库
     *
     * @param pdname 药品名称
     * @param pDbatch 批次
     * @param pdnum 数量
     * @param sno 供应商编号
     * @param sano 管理员编号
     * @return 入库成功返回 true，否则返回 false
     */
    public boolean addInventoryDrug(String pdname, String pDbatch, int pdnum, String sno, String sano) {
        return drugInventoryDomain.insertInventoryDrug(pdname, pDbatch, pdnum, sno, sano);
    }

    /**
     * 获取过期药品批次
     *
     * @return 过期药品批次列表
     */
    public List<ExpiredDrugBatch> getExpiredDrugBatches() {
        return drugDisposalDomain.getExpiredDrugBatches();
    }

    /**
     * 销毁过期药品
     *
     * @param pdno 药品编号
     * @param pdbatch 批次
     * @param dano 销毁管理员编号
     * @return 销毁成功返回 true，否则返回 false
     */
    public boolean destroyExpiredDrugBatch(String pdno, String pdbatch, String dano) {
        return drugDisposalDomain.destroyExpiredDrugBatch(pdno, pdbatch, dano);
    }

    /**
     * 添加供应商信息
     *
     * @param sno 供应商编号
     * @param sname 供应商名称
     * @param saddr 供应商地址
     * @param sphone 供应商电话
     * @return 添加成功返回 true，否则返回 false
     */
    public boolean addSupplier(String sno, String sname, String saddr, String sphone) {
        return supplierDomain.addSupplier(sno, sname, saddr, sphone);
    }

    /**
     * 修改供应商信息
     *
     * @param sno 供应商编号
     * @param sname 供应商名称
     * @param saddr 供应商地址
     * @param sphone 供应商电话
     * @return 修改成功返回 true，否则返回 false
     */
    public boolean updateSupplier(String sno, String sname, String saddr, String sphone) {
        return supplierDomain.updateSupplier(sno, sname, saddr, sphone);
    }

    /**
     * 删除供应商信息
     *
     * @param sno 供应商编号
     * @return 删除成功返回 true，否则返回 false
     */
    public boolean deleteSupplier(String sno) {
        return supplierDomain.deleteSupplier(sno);
    }
}
