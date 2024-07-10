package com.hdims.service;

import com.hdims.domain.InventoryDomain;
import com.hdims.domain.PrescriptionDomain;
import com.hdims.model.DrugViewItem;
import com.hdims.model.PrescriptionDetail;

import java.util.List;
import java.util.Map;

/**
 * NurseService 类提供护士的功能。
 * 包括查看药品库存、查看所有处理的处方、处理处方。
 */
public class NurseService {

    private InventoryDomain inventoryDomain;
    private PrescriptionDomain prescriptionDomain;

    public NurseService() {
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
     * 查看所有处理的处方
     *
     * @param nno 护士编号
     * @return 处方药品信息列表
     */
    public Map<Integer, List<PrescriptionDetail>> viewHandledPrescriptions(String nno) {
        return prescriptionDomain.getHandledPrescriptions(nno);
    }

    /**
     * 查看所有未处理的处方
     *
     * @return 处方药品信息列表
     */
    public Map<Integer, List<PrescriptionDetail>> viewUnhandledPrescriptions() {
        return prescriptionDomain.getUnhandledPrescriptions();
    }

    /**
     * 处理处方
     *
     * @param pno 处方编号
     * @param nno 护士编号
     * @return 处理成功返回 true，否则返回 false
     */
    public boolean handlePrescription(int pno, String nno) {
        return prescriptionDomain.handlePrescription(pno, nno);
    }

    /**
     * 检查处方是否可处理（库存是否足够）
     *
     * @param pno 处方编号
     * @return 可处理返回 true，否则返回 false
     */
    public boolean isPrescriptionProcessable(int pno) {
        return prescriptionDomain.isPrescriptionProcessable(pno);
    }
}
