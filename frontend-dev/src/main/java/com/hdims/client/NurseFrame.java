package com.hdims.client;

import com.hdims.model.DrugViewItem;
import com.hdims.model.PrescriptionDetail;
import com.hdims.service.NurseService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class NurseFrame extends JFrame {
    private JPanel centerPanel;
    private NurseService nurseService;

    public NurseFrame() {
        setTitle("护士界面");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        nurseService = new NurseService(); // 初始化服务层

        // 主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20), // 外部边距
                BorderFactory.createLineBorder(Color.BLACK, 2)  // 内部边框
        ));

        // 创建顶部面板（欢迎信息）
        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel welcomeLabel = new JLabel("欢迎，护士！");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 18));
        topPanel.add(welcomeLabel);

        // 创建左侧面板（功能按钮）
        JPanel leftPanel = new JPanel(new GridLayout(4, 1, 10, 10)); // 3个功能按钮 + 1个注销按钮
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        String[] buttonLabels = {"查看药品库存列表", "查看所有处理的处方", "处理处方", "注销"};
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Serif", Font.PLAIN, 16));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleButtonAction(label);
                }
            });
            if (label.equals("注销")) {
                button.setForeground(Color.RED);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new LoginFrame().setVisible(true);
                        dispose();
                    }
                });
            }
            leftPanel.add(button);
        }

        // 创建中间主面板（实际内容显示区域）
        centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10), // 内部边距
                BorderFactory.createLineBorder(Color.BLACK, 2)   // 内部边框
        ));
        centerPanel.setBackground(Color.LIGHT_GRAY);
        JLabel instructionLabel = new JLabel("欢迎使用医院库存管理系统，请点击左侧按钮选择功能");
        instructionLabel.setFont(new Font("Serif", Font.BOLD, 16));
        centerPanel.add(instructionLabel);

        // 将各部分添加到主面板
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // 将主面板添加到窗口
        add(mainPanel);
    }

    private void handleButtonAction(String label) {
        if (label.equals("查看药品库存列表")) {
            showDrugInventoryList();
        } else if (label.equals("查看所有处理的处方")) {
            showHandledPrescriptions();
        } else if (label.equals("处理处方")) {
            showUnhandledPrescriptions();
        }
    }

    private void showDrugInventoryList() {
        List<DrugViewItem> drugList = nurseService.viewInventory();
        centerPanel.removeAll(); // 清空面板内容
        centerPanel.setLayout(new BorderLayout());

        String[] columnNames = {"药品编号", "药品名称", "保质期（天）", "库存数量"};
        Object[][] data = new Object[drugList.size()][4];
        for (int i = 0; i < drugList.size(); i++) {
            DrugViewItem drug = drugList.get(i);
            data[i][0] = drug.getPdno();
            data[i][1] = drug.getPdname();
            data[i][2] = drug.getPdlife();
            data[i][3] = drug.getPdnum();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void showHandledPrescriptions() {
        String nno = UserSession.getLoggedInUserId(); // 获取当前登录护士编号
        Map<Integer, List<PrescriptionDetail>> prescriptions = nurseService.viewHandledPrescriptions(nno);

        centerPanel.removeAll(); // 清空面板内容
        centerPanel.setLayout(new BorderLayout());

        String[] columnNames = {"处方编号", "医生名称", "药品名称", "数量"};
        int rowCount = prescriptions.values().stream().mapToInt(List::size).sum();
        Object[][] data = new Object[rowCount][4];

        int rowIndex = 0;
        for (Map.Entry<Integer, List<PrescriptionDetail>> entry : prescriptions.entrySet()) {
            Integer pno = entry.getKey();
            boolean firstEntry = true;
            for (PrescriptionDetail detail : entry.getValue()) {
                if (firstEntry) {
                    data[rowIndex][0] = pno;
                    data[rowIndex][1] = detail.getDname();
                    firstEntry = false;
                } else {
                    data[rowIndex][0] = "";
                    data[rowIndex][1] = "";
                }
                data[rowIndex][2] = detail.getPdname();
                data[rowIndex][3] = detail.getPdnum();
                rowIndex++;
            }
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void showUnhandledPrescriptions() {
        Map<Integer, List<PrescriptionDetail>> prescriptions = nurseService.viewUnhandledPrescriptions();

        centerPanel.removeAll(); // 清空面板内容
        centerPanel.setLayout(new BorderLayout());

        String[] columnNames = {"处方编号", "医生名称", "药品名称", "数量"};
        int rowCount = prescriptions.values().stream().mapToInt(List::size).sum();
        Object[][] data = new Object[rowCount][4];

        int rowIndex = 0;
        for (Map.Entry<Integer, List<PrescriptionDetail>> entry : prescriptions.entrySet()) {
            Integer pno = entry.getKey();
            boolean firstEntry = true;
            for (PrescriptionDetail detail : entry.getValue()) {
                if (firstEntry) {
                    data[rowIndex][0] = pno;
                    data[rowIndex][1] = detail.getDname();
                    firstEntry = false;
                } else {
                    data[rowIndex][0] = "";
                    data[rowIndex][1] = "";
                }
                data[rowIndex][2] = detail.getPdname();
                data[rowIndex][3] = detail.getPdnum();
                rowIndex++;
            }
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // 添加处理按钮
        JPanel buttonPanel = new JPanel();
        JButton handleButton = new JButton("处理选中处方");
        handleButton.setFont(new Font("Serif", Font.PLAIN, 16));
        handleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Integer selectedPno = (Integer) table.getValueAt(selectedRow, 0);
                    if (selectedPno != null) {
                        String nno = UserSession.getLoggedInUserId(); // 获取当前登录护士编号
                        if (nurseService.isPrescriptionProcessable(selectedPno)) {
                            boolean success = nurseService.handlePrescription(selectedPno, nno);
                            if (success) {
                                JOptionPane.showMessageDialog(centerPanel, "处方处理成功！");
                                showUnhandledPrescriptions(); // 刷新未处理处方列表
                            } else {
                                JOptionPane.showMessageDialog(centerPanel, "处方处理失败！");
                            }
                        } else {
                            JOptionPane.showMessageDialog(centerPanel, "库存不足，无法处理该处方！");
                        }
                    } else {
                        JOptionPane.showMessageDialog(centerPanel, "请选择有效的处方！");
                    }
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "请选择要处理的处方！");
                }
            }
        });
        buttonPanel.add(handleButton);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NurseFrame().setVisible(true);
            }
        });
    }
}
