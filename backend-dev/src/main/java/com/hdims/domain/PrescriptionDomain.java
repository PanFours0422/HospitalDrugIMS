package com.hdims.domain;

import com.hdims.model.PrescriptionDetail;
import com.hdims.model.PrescriptionDrug;
import com.hdims.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * PrescriptionDomain 类提供医生查看和修改自己开出处方的功能。
 */
public class PrescriptionDomain {

    /**
     * 获取医生开出的处方药品信息。
     *
     * @param dno 医生编号
     * @return 处方药品信息列表
     */
    public Map<Integer, List<PrescriptionDrug>> getDoctorPrescriptions(String dno) {
        Map<Integer, List<PrescriptionDrug>> prescriptions = new LinkedHashMap<>();
        String sql = "SELECT p.Pno, d.PDname, pid.PDnum " +
                "FROM Prescription p " +
                "JOIN PID pid ON p.Pno = pid.Pno " +
                "JOIN Drug d ON pid.PDno = d.PDno " +
                "WHERE p.Dno = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dno);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int pno = rs.getInt("Pno");
                    String pdname = rs.getString("PDname");
                    int pdnum = rs.getInt("PDnum");

                    PrescriptionDrug prescriptionDrug = new PrescriptionDrug();
                    prescriptionDrug.setPdname(pdname);
                    prescriptionDrug.setPdnum(pdnum);

                    prescriptions.computeIfAbsent(pno, k -> new ArrayList<>()).add(prescriptionDrug);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prescriptions;
    }

    /**
     * 删除处方。
     *
     * @param pno 处方编号
     * @return 删除是否成功
     */
    public boolean deletePrescription(int pno) {
        String sql = "DELETE FROM Prescription WHERE Pno = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pno);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改处方中的药品名称。
     *
     * @param pno 处方编号
     * @param oldPdname 旧药品名称
     * @param newPdname 新药品名称
     * @return 修改是否成功
     */
    public boolean updatePrescriptionDrugName(int pno, String oldPdname, String newPdname) {
        String sql = "UPDATE PID SET PDno = (SELECT PDno FROM Drug WHERE PDname = ?) " +
                "WHERE Pno = ? AND PDno = (SELECT PDno FROM Drug WHERE PDname = ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newPdname);
            stmt.setInt(2, pno);
            stmt.setString(3, oldPdname);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改处方中的药品数量。
     *
     * @param pno 处方编号
     * @param pdname 药品名称
     * @param newPdnum 新药品数量
     * @return 修改是否成功
     */
    public boolean updatePrescriptionDrugQuantity(int pno, String pdname, int newPdnum) {
        String sql = "UPDATE PID SET PDnum = ? WHERE Pno = ? AND PDno = (SELECT PDno FROM Drug WHERE PDname = ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newPdnum);
            stmt.setInt(2, pno);
            stmt.setString(3, pdname);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取护士处理的所有处方。
     *
     * @param nno 护士编号
     * @return 处方药品信息列表
     */
    public Map<Integer, List<PrescriptionDetail>> getHandledPrescriptions(String nno) {
        Map<Integer, List<PrescriptionDetail>> prescriptions = new LinkedHashMap<>();
        String sql = "SELECT p.Pno, d.Dname, drug.PDname, pid.PDnum " +
                "FROM Prescription p " +
                "JOIN Doctor d ON p.Dno = d.Dno " +
                "JOIN PID pid ON p.Pno = pid.Pno " +
                "JOIN Drug drug ON pid.PDno = drug.PDno " +
                "WHERE p.Nno = ? AND p.Pstate = 1";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nno);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int pno = rs.getInt("Pno");
                    String dname = rs.getString("Dname");
                    String pdname = rs.getString("PDname");
                    int pdnum = rs.getInt("PDnum");

                    PrescriptionDetail detail = new PrescriptionDetail();
                    detail.setDname(dname);
                    detail.setPdname(pdname);
                    detail.setPdnum(pdnum);

                    prescriptions.computeIfAbsent(pno, k -> new ArrayList<>()).add(detail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prescriptions;
    }

    /**
     * 获取所有未处理的处方。
     *
     * @return 处方药品信息列表
     */
    public Map<Integer, List<PrescriptionDetail>> getUnhandledPrescriptions() {
        Map<Integer, List<PrescriptionDetail>> prescriptions = new LinkedHashMap<>();
        String sql = "SELECT p.Pno, d.Dname, drug.PDname, pid.PDnum " +
                "FROM Prescription p " +
                "JOIN Doctor d ON p.Dno = d.Dno " +
                "JOIN PID pid ON p.Pno = pid.Pno " +
                "JOIN Drug drug ON pid.PDno = drug.PDno " +
                "WHERE p.Pstate = 0";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int pno = rs.getInt("Pno");
                    String dname = rs.getString("Dname");
                    String pdname = rs.getString("PDname");
                    int pdnum = rs.getInt("PDnum");

                    PrescriptionDetail detail = new PrescriptionDetail();
                    detail.setDname(dname);
                    detail.setPdname(pdname);
                    detail.setPdnum(pdnum);

                    prescriptions.computeIfAbsent(pno, k -> new ArrayList<>()).add(detail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prescriptions;
    }

    /**
     * 处理指定编号的处方。
     *
     * @param pno 处方编号
     * @param nno 护士编号
     * @return 处理是否成功
     */
    public boolean handlePrescription(int pno, String nno) {
        String updatePrescriptionSql = "UPDATE Prescription SET Pstate = 1, Nno = ?, Htime = GETDATE() WHERE Pno = ? AND Pstate = 0";
        String updateInventorySql = "UPDATE InventoryDrug SET PDnum = PDnum - ? WHERE PDno = ? AND PDbatch = (SELECT MIN(PDbatch) FROM InventoryDrug WHERE PDno = ? AND PDnum >= ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement updatePrescriptionStmt = conn.prepareStatement(updatePrescriptionSql);
             PreparedStatement updateInventoryStmt = conn.prepareStatement(updateInventorySql)) {

            conn.setAutoCommit(false);

            // 更新处方状态
            updatePrescriptionStmt.setString(1, nno);
            updatePrescriptionStmt.setInt(2, pno);
            int rowsUpdated = updatePrescriptionStmt.executeUpdate();

            if (rowsUpdated > 0) {
                // 减少相应的药品库存
                String selectPidSql = "SELECT PDno, PDnum FROM PID WHERE Pno = ?";
                try (PreparedStatement selectPidStmt = conn.prepareStatement(selectPidSql)) {
                    selectPidStmt.setInt(1, pno);
                    try (ResultSet rs = selectPidStmt.executeQuery()) {
                        while (rs.next()) {
                            String pdno = rs.getString("PDno");
                            int pdnum = rs.getInt("PDnum");

                            updateInventoryStmt.setInt(1, pdnum);
                            updateInventoryStmt.setString(2, pdno);
                            updateInventoryStmt.setString(3, pdno);
                            updateInventoryStmt.setInt(4, pdnum);
                            updateInventoryStmt.executeUpdate();
                        }
                    }
                }
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
