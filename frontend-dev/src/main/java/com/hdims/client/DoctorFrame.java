package com.hdims.client;

import com.hdims.model.DrugViewItem;
import com.hdims.model.PrescriptionDrug;
import com.hdims.service.DoctorService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class DoctorFrame extends JFrame {
    private JPanel centerPanel;
    private DoctorService doctorService;

    public DoctorFrame() {
        setTitle("医生界面");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        doctorService = new DoctorService(); // 初始化服务层

        // 主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20), // 外部边距
                BorderFactory.createLineBorder(Color.BLACK, 2)  // 内部边框
        ));

        // 创建顶部面板（欢迎信息）
        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel welcomeLabel = new JLabel("欢迎，医生！");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 18));
        topPanel.add(welcomeLabel);

        // 创建左侧面板（功能按钮）
        JPanel leftPanel = new JPanel(new GridLayout(5, 1, 10, 10)); // 4个功能按钮 + 1个注销按钮
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        String[] buttonLabels = {"查看药品库存列表", "查看自己开出的处方", "开处方", "修改处方", "注销"};
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
        } else if (label.equals("查看自己开出的处方")) {
            showDoctorPrescriptions();
        } else if (label.equals("修改处方")) {
            showModifyPrescriptionForm();
        }
        // 其他功能按钮的处理可以在这里添加
    }

    private void showDrugInventoryList() {
        List<DrugViewItem> drugList = doctorService.viewInventory();
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

    private void showDoctorPrescriptions() {
        String dno = UserSession.getLoggedInUserId(); // 获取当前登录医生编号
        Map<Integer, List<PrescriptionDrug>> prescriptions = doctorService.viewPrescriptions(dno);

        centerPanel.removeAll(); // 清空面板内容
        centerPanel.setLayout(new BorderLayout());

        String[] columnNames = {"处方编号", "药品名称", "数量"};
        int rowCount = prescriptions.values().stream().mapToInt(List::size).sum();
        Object[][] data = new Object[rowCount][3];

        int rowIndex = 0;
        for (Map.Entry<Integer, List<PrescriptionDrug>> entry : prescriptions.entrySet()) {
            Integer pno = entry.getKey();
            boolean firstEntry = true;
            for (PrescriptionDrug drug : entry.getValue()) {
                if (firstEntry) {
                    data[rowIndex][0] = pno;
                    firstEntry = false;
                } else {
                    data[rowIndex][0] = "";
                }
                data[rowIndex][1] = drug.getPdname();
                data[rowIndex][2] = drug.getPdnum();
                rowIndex++;
            }
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void showModifyPrescriptionForm() {
        centerPanel.removeAll(); // 清空面板内容
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // 获取医生的处方编号和药品名称
        String dno = UserSession.getLoggedInUserId();
        Map<Integer, List<PrescriptionDrug>> prescriptions = doctorService.viewPrescriptions(dno);

        JLabel pnoLabel = new JLabel("处方编号:");
        pnoLabel.setFont(new Font("Serif", Font.BOLD, 16));
        JComboBox<Integer> pnoComboBox = new JComboBox<>(prescriptions.keySet().toArray(new Integer[0]));
        pnoComboBox.setPreferredSize(new Dimension(150, 25)); // 设置长度与药品数量输入框一致

        JLabel oldDrugNameLabel = new JLabel("旧药品名称:");
        oldDrugNameLabel.setFont(new Font("Serif", Font.BOLD, 16));
        JComboBox<String> oldDrugNameComboBox = new JComboBox<>();
        oldDrugNameComboBox.setPreferredSize(new Dimension(150, 25)); // 设置长度与药品数量输入框一致

        JLabel newDrugNameLabel = new JLabel("新药品名称:");
        newDrugNameLabel.setFont(new Font("Serif", Font.BOLD, 16));
        JComboBox<String> newDrugNameComboBox = new JComboBox<>(new String[]{
                "头孢曲松钠", "磷霉素钠", "头孢噻肟钠", "氨苄西林钠", "复合维生素片", "头孢吡肟", "草木犀流浸液片", "伏格列波糖", "利血平",
                "地高辛", "利伐沙班片", "阿莫西林胶囊", "头孢拉定胶囊", "青霉素钠", "氯芬黄敏片", "舍雷肽酶肠溶片", "莫匹罗星软膏",
                "愈创罂粟待因片", "噻奈普汀片", "氧氟沙星眼膏", "复方消化酶片", "克拉霉素片", "替米沙坦片", "痫愈胶囊", "骨筋丸胶囊",
                "四季感冒片", "复方樟薄软膏", "午时茶颗粒", "复方穿心莲片", "十三味马钱子丸"
        });
        newDrugNameComboBox.setPreferredSize(new Dimension(150, 25)); // 设置长度与药品数量输入框一致

        JLabel drugNumLabel = new JLabel("药品数量:");
        drugNumLabel.setFont(new Font("Serif", Font.BOLD, 16));
        JTextField drugNumField = new JTextField(10);
        drugNumField.setPreferredSize(new Dimension(150, 25)); // 设置长度

        // 更新旧药品名称下拉框
        pnoComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldDrugNameComboBox.removeAllItems();
                Integer selectedPno = (Integer) pnoComboBox.getSelectedItem();
                if (selectedPno != null) {
                    for (PrescriptionDrug drug : prescriptions.get(selectedPno)) {
                        oldDrugNameComboBox.addItem(drug.getPdname());
                    }
                }
            }
        });

        JButton updateNameButton = new JButton("修改药品名称");
        updateNameButton.setFont(new Font("Serif", Font.BOLD, 16));
        updateNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer pno = (Integer) pnoComboBox.getSelectedItem();
                String oldDrugName = (String) oldDrugNameComboBox.getSelectedItem();
                String newDrugName = (String) newDrugNameComboBox.getSelectedItem();

                boolean success = doctorService.updatePrescriptionDrugName(pno, oldDrugName, newDrugName);
                if (success) {
                    JOptionPane.showMessageDialog(centerPanel, "药品名称修改成功！");
                    pnoComboBox.setSelectedIndex(0);
                    oldDrugNameComboBox.removeAllItems();
                    newDrugNameComboBox.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "药品名称修改失败，请检查输入信息！");
                }
            }
        });

        JButton updateNumButton = new JButton("修改药品数量");
        updateNumButton.setFont(new Font("Serif", Font.BOLD, 16));
        updateNumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer pno = (Integer) pnoComboBox.getSelectedItem();
                String drugName = (String) oldDrugNameComboBox.getSelectedItem();
                int drugNum;
                try {
                    drugNum = Integer.parseInt(drugNumField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(centerPanel, "药品数量必须是一个整数！");
                    return;
                }

                boolean success = doctorService.updatePrescriptionDrugQuantity(pno, drugName, drugNum);
                if (success) {
                    JOptionPane.showMessageDialog(centerPanel, "药品数量修改成功！");
                    pnoComboBox.setSelectedIndex(0);
                    oldDrugNameComboBox.removeAllItems();
                    drugNumField.setText("");
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "药品数量修改失败，请检查输入信息！");
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(pnoLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(pnoComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(oldDrugNameLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(oldDrugNameComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(newDrugNameLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(newDrugNameComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(drugNumLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(drugNumField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(updateNameButton, gbc);

        gbc.gridy++;
        centerPanel.add(updateNumButton, gbc);

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DoctorFrame().setVisible(true);
            }
        });
    }
}
