package com.hdims.service;

import com.hdims.domain.InventoryDomain;
import com.hdims.domain.PrescriptionDomain;
import com.hdims.model.DrugViewItem;
import com.hdims.model.PrescriptionDrug;

import java.util.List;
import java.util.Map;

/**
 * DoctorService 类提供医生的功能。
 * 包括查看药品库存、查看自己开出的处方、开处方、修改处方。
 */
public class DoctorService {

    private InventoryDomain inventoryDomain;
    private PrescriptionDomain prescriptionDomain;

    public DoctorService() {
        this.inventoryDomain = new InventoryDomain();
        this.prescriptionDomain = new PrescriptionDomain();
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
     * 查看医生开出的处方
     *
     * @param dno 医生编号
     * @return 处方药品信息列表
     */
    public Map<Integer, List<PrescriptionDrug>> viewPrescriptions(String dno) {
        return prescriptionDomain.getDoctorPrescriptions(dno);
    }

    /**
     * 删除处方
     *
     * @param pno 处方编号
     * @return 删除成功返回 true，否则返回 false
     */
    public boolean deletePrescription(int pno) {
        return prescriptionDomain.deletePrescription(pno);
    }

    /**
     * 修改处方中的药品名称
     *
     * @param pno 处方编号
     * @param oldPdname 旧药品名称
     * @param newPdname 新药品名称
     * @return 修改成功返回 true，否则返回 false
     */
    public boolean updatePrescriptionDrugName(int pno, String oldPdname, String newPdname) {
        return prescriptionDomain.updatePrescriptionDrugName(pno, oldPdname, newPdname);
    }

    /**
     * 修改处方中的药品数量
     *
     * @param pno 处方编号
     * @param pdname 药品名称
     * @param newPdnum 新药品数量
     * @return 修改成功返回 true，否则返回 false
     */
    public boolean updatePrescriptionDrugQuantity(int pno, String pdname, int newPdnum) {
        return prescriptionDomain.updatePrescriptionDrugQuantity(pno, pdname, newPdnum);
    }

    /**
     * 开处方（创建新的处方）
     *
     * @param dno 医生编号
     * @param pid 病人身份证号码
     * @param pdnames 药品名称列表
     * @param pdnums 药品数量列表
     * @return 开处方成功返回 true，否则返回 false
     */
    public boolean createPrescription(String dno, String pid, List<String> pdnames, List<Integer> pdnums) {
        // 假设 PrescriptionDomain 类有一个 createPrescription 方法
        // return prescriptionDomain.createPrescription(dno, pid, pdnames, pdnums);
        // 这里您可以按照具体的需求实现 createPrescription 方法
        return false;
    }
}
