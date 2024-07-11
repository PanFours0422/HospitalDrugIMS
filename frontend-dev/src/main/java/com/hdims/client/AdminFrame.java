package com.hdims.client;

import com.hdims.model.DrugViewItem;
import com.hdims.model.ExpiredDrugBatch;
import com.hdims.service.AdminService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminFrame extends JFrame {
    private JPanel centerPanel;
    private AdminService adminService;

    public AdminFrame() {
        setTitle("库存管理员界面");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        adminService = new AdminService(); // 初始化服务层

        // 主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20), // 外部边距
                BorderFactory.createLineBorder(Color.BLACK, 2)  // 内部边框
        ));

        // 创建顶部面板（欢迎信息）
        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel welcomeLabel = new JLabel("欢迎，库存管理员！");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 18));
        topPanel.add(welcomeLabel);

        // 创建左侧面板（功能按钮）
        JPanel leftPanel = new JPanel(new GridLayout(6, 1, 10, 10)); // 5个功能按钮 + 1个注销按钮
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        String[] buttonLabels = {"查看药品库存列表", "药品入库", "处理过期药品", "添加供应商详细记录", "修改供应商详细记录", "注销"};
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
        } else if (label.equals("药品入库")) {
            showAddDrugForm();
        } else if (label.equals("处理过期药品")) {
            showExpiredDrugList();
        } else if (label.equals("添加供应商详细记录")) {
            showAddSupplierForm();
        } else if (label.equals("修改供应商详细记录")) {
            showModifySupplierForm();
        }
        // 其他功能按钮的处理可以在这里添加
    }

    private void showDrugInventoryList() {
        List<DrugViewItem> drugList = adminService.viewInventory();
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

    private void showAddDrugForm() {
        centerPanel.removeAll(); // 清空面板内容
        centerPanel.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;

        String[] drugNames = {
                "", "头孢曲松钠", "磷霉素钠", "头孢噻肟钠", "氨苄西林钠", "复合维生素片", "头孢吡肟", "草木犀流浸液片", "伏格列波糖", "利血平",
                "地高辛", "利伐沙班片", "阿莫西林胶囊", "头孢拉定胶囊", "青霉素钠", "氯芬黄敏片", "舍雷肽酶肠溶片", "莫匹罗星软膏", "愈创罂粟待因片",
                "噻奈普汀片", "氧氟沙星眼膏", "复方消化酶片", "克拉霉素片", "替米沙坦片", "痫愈胶囊", "骨筋丸胶囊", "四季感冒片", "复方樟薄软膏",
                "午时茶颗粒", "复方穿心莲片", "十三味马钱子丸"
        };

        String[] supplierIds = {
                "", "SDS0001", "SFT0001", "SGT0001", "SGYKG0001", "SHY0001",
                "SKYQD0001", "STJ0001", "STY0001", "SXZT0001", "SYC0001",
                "SYT0001", "SZX0001"
        };

        String[] adminIds = {
                "", "admin0001", "admin0002", "admin0003", "admin0004", "admin0005"
        };

        JLabel pdnameLabel = new JLabel("药品名称:");
        pdnameLabel.setFont(new Font("Serif", Font.PLAIN, 18)); // 墩字体
        JComboBox<String> pdnameComboBox = new JComboBox<>(drugNames);
        pdnameComboBox.setPreferredSize(new Dimension(200, 30)); // 缩小选择框

        JLabel pDbatchLabel = new JLabel("批次 (格式: YYYY-MM-DD):");
        pDbatchLabel.setFont(new Font("Serif", Font.PLAIN, 18)); // 墩字体
        JTextField pDbatchField = new JTextField();
        pDbatchField.setPreferredSize(new Dimension(200, 30)); // 缩小输入框
        pDbatchField.setToolTipText("格式: YYYY-MM-DD");

        JLabel pdnumLabel = new JLabel("数量:");
        pdnumLabel.setFont(new Font("Serif", Font.PLAIN, 18)); // 墩字体
        JTextField pdnumField = new JTextField();
        pdnumField.setPreferredSize(new Dimension(200, 30)); // 缩小输入框

        JLabel snoLabel = new JLabel("供应商编号:");
        snoLabel.setFont(new Font("Serif", Font.PLAIN, 18)); // 墩字体
        JComboBox<String> snoComboBox = new JComboBox<>(supplierIds);
        snoComboBox.setPreferredSize(new Dimension(200, 30)); // 缩小选择框

        JLabel sanoLabel = new JLabel("管理员编号:");
        sanoLabel.setFont(new Font("Serif", Font.PLAIN, 18)); // 墩字体
        JComboBox<String> sanoComboBox = new JComboBox<>(adminIds);
        sanoComboBox.setPreferredSize(new Dimension(200, 30)); // 缩小选择框

        JButton addButton = new JButton("添加");
        addButton.setFont(new Font("Serif", Font.BOLD, 18)); // 墩字体
        addButton.setPreferredSize(new Dimension(100, 30)); // 缩小按钮
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pdname = (String) pdnameComboBox.getSelectedItem();
                String pDbatch = pDbatchField.getText();
                int pdnum;
                try {
                    pdnum = Integer.parseInt(pdnumField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(centerPanel, "数量必须是一个整数！");
                    return;
                }
                String sno = (String) snoComboBox.getSelectedItem();
                String sano = (String) sanoComboBox.getSelectedItem();

                if (pdname.isEmpty() || sno.isEmpty() || sano.isEmpty()) {
                    JOptionPane.showMessageDialog(centerPanel, "请确保所有选择框都已选择！");
                    return;
                }

                if (!pDbatch.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    JOptionPane.showMessageDialog(centerPanel, "批次日期格式不正确，请输入 YYYY-MM-DD 格式！");
                    return;
                }

                boolean success = adminService.addInventoryDrug(pdname, pDbatch, pdnum, sno, sano);
                if (success) {
                    JOptionPane.showMessageDialog(centerPanel, "药品入库成功！");
                    pdnameComboBox.setSelectedIndex(0);
                    pDbatchField.setText("");
                    pdnumField.setText("");
                    snoComboBox.setSelectedIndex(0);
                    sanoComboBox.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "药品入库失败，请检查输入信息！");
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(pdnameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(pdnameComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(pDbatchLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(pDbatchField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(pdnumLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(pdnumField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(snoLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(snoComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(sanoLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(sanoComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(addButton, gbc);

        centerPanel.add(formPanel, BorderLayout.CENTER);

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void showExpiredDrugList() {
        List<ExpiredDrugBatch> expiredDrugBatches = adminService.getExpiredDrugBatches();
        centerPanel.removeAll(); // 清空面板内容
        centerPanel.setLayout(new BorderLayout());

        String[] columnNames = {"药品编号", "药品名称", "批次", "数量", "供应商编号", "管理员编号", "入库时间"};
        Object[][] data = new Object[expiredDrugBatches.size()][7];
        for (int i = 0; i < expiredDrugBatches.size(); i++) {
            ExpiredDrugBatch batch = expiredDrugBatches.get(i);
            data[i][0] = batch.getPdno();
            data[i][1] = batch.getPdname();
            data[i][2] = batch.getPdbatch();
            data[i][3] = batch.getPdnum();
            data[i][4] = batch.getSno();
            data[i][5] = batch.getSano();
            data[i][6] = batch.getStime();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        JButton destroyButton = new JButton("销毁选中药品");
        destroyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(centerPanel, "请选择要销毁的药品！");
                    return;
                }

                String pdno = (String) table.getValueAt(selectedRow, 0);
                String pdbatch = table.getValueAt(selectedRow, 2).toString();
                String sano = "admin0001"; // 假设当前登录管理员编号为 admin0001
                boolean success = adminService.destroyExpiredDrugBatch(pdno, pdbatch, sano);

                if (success) {
                    JOptionPane.showMessageDialog(centerPanel, "药品销毁成功！");
                    showExpiredDrugList(); // 刷新列表
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "药品销毁失败，请重试！");
                }
            }
        });

        centerPanel.add(destroyButton, BorderLayout.SOUTH);

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void showAddSupplierForm() {
        centerPanel.removeAll(); // 清空面板内容
        centerPanel.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel snoLabel = new JLabel("供应商编号:");
        snoLabel.setFont(new Font("Serif", Font.PLAIN, 18)); // 墩字体
        JTextField snoField = new JTextField(20);
        snoField.setPreferredSize(new Dimension(200, 30)); // 缩小输入框

        JLabel snameLabel = new JLabel("供应商名称:");
        snameLabel.setFont(new Font("Serif", Font.PLAIN, 18)); // 墩字体
        JTextField snameField = new JTextField(20);
        snameField.setPreferredSize(new Dimension(200, 30)); // 缩小输入框

        JLabel saddrLabel = new JLabel("供应商地址:");
        saddrLabel.setFont(new Font("Serif", Font.PLAIN, 18)); // 墩字体
        JTextField saddrField = new JTextField(20);
        saddrField.setPreferredSize(new Dimension(200, 30)); // 缩小输入框

        JLabel sphoneLabel = new JLabel("供应商电话:");
        sphoneLabel.setFont(new Font("Serif", Font.PLAIN, 18)); // 墩字体
        JTextField sphoneField = new JTextField(20);
        sphoneField.setPreferredSize(new Dimension(200, 30)); // 缩小输入框

        JButton addButton = new JButton("添加供应商");
        addButton.setFont(new Font("Serif", Font.BOLD, 18)); // 墩字体
        addButton.setPreferredSize(new Dimension(150, 40)); // 缩小按钮
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sno = snoField.getText();
                String sname = snameField.getText();
                String saddr = saddrField.getText();
                String sphone = sphoneField.getText();

                if (sno.isEmpty() || sname.isEmpty() || saddr.isEmpty() || sphone.isEmpty()) {
                    JOptionPane.showMessageDialog(centerPanel, "所有字段都是必填项！");
                    return;
                }

                boolean success = adminService.addSupplier(sno, sname, saddr, sphone);
                if (success) {
                    JOptionPane.showMessageDialog(centerPanel, "供应商添加成功！");
                    snoField.setText("");
                    snameField.setText("");
                    saddrField.setText("");
                    sphoneField.setText("");
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "供应商添加失败，请重试！");
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(snoLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(snoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(snameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(snameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(saddrLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(saddrField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(sphoneLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(sphoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(addButton, gbc);

        centerPanel.add(formPanel, BorderLayout.CENTER);

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void showModifySupplierForm() {
        centerPanel.removeAll(); // 清空面板内容
        centerPanel.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;

        String[] supplierIds = {
                "", "SDS0001", "SFT0001", "SGT0001", "SGYKG0001", "SHY0001",
                "SKYQD0001", "STJ0001", "STY0001", "SXZT0001", "SYC0001",
                "SYT0001", "SZX0001"
        };

        JLabel snoLabel = new JLabel("供应商编号:");
        snoLabel.setFont(new Font("Serif", Font.PLAIN, 18)); // 墩字体
        JComboBox<String> snoComboBox = new JComboBox<>(supplierIds);
        snoComboBox.setPreferredSize(new Dimension(200, 30)); // 缩小选择框

        JLabel snameLabel = new JLabel("供应商名称:");
        snameLabel.setFont(new Font("Serif", Font.PLAIN, 18)); // 墩字体
        JTextField snameField = new JTextField(20);
        snameField.setPreferredSize(new Dimension(200, 30)); // 缩小输入框

        JLabel saddrLabel = new JLabel("供应商地址:");
        saddrLabel.setFont(new Font("Serif", Font.PLAIN, 18)); // 墩字体
        JTextField saddrField = new JTextField(20);
        saddrField.setPreferredSize(new Dimension(200, 30)); // 缩小输入框

        JLabel sphoneLabel = new JLabel("供应商电话:");
        sphoneLabel.setFont(new Font("Serif", Font.PLAIN, 18)); // 墩字体
        JTextField sphoneField = new JTextField(20);
        sphoneField.setPreferredSize(new Dimension(200, 30)); // 缩小输入框

        JButton updateButton = new JButton("修改供应商");
        updateButton.setFont(new Font("Serif", Font.BOLD, 18)); // 墩字体
        updateButton.setPreferredSize(new Dimension(150, 40)); // 缩小按钮
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sno = (String) snoComboBox.getSelectedItem();
                String sname = snameField.getText();
                String saddr = saddrField.getText();
                String sphone = sphoneField.getText();

                if (sno.isEmpty() || sname.isEmpty() || saddr.isEmpty() || sphone.isEmpty()) {
                    JOptionPane.showMessageDialog(centerPanel, "所有字段都是必填项！");
                    return;
                }

                boolean success = adminService.updateSupplier(sno, sname, saddr, sphone);
                if (success) {
                    JOptionPane.showMessageDialog(centerPanel, "供应商信息修改成功！");
                    snoComboBox.setSelectedIndex(0);
                    snameField.setText("");
                    saddrField.setText("");
                    sphoneField.setText("");
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "供应商信息修改失败，请重试！");
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(snoLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(snoComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(snameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(snameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(saddrLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(saddrField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(sphoneLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(sphoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(updateButton, gbc);

        centerPanel.add(formPanel, BorderLayout.CENTER);

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminFrame().setVisible(true);
            }
        });
    }
}
