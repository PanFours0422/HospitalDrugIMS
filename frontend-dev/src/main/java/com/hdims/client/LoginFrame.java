package com.hdims.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField idField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton loginButton;
    private JCheckBox rememberMeCheckBox;
    private JLabel resultLabel;

    public LoginFrame() {
        setTitle("医院药品库存管理系统 - 登录");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // 创建顶部面板（标题部分）
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.LIGHT_GRAY);
        JLabel titleLabel = new JLabel("医院药品库存管理系统");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titlePanel.add(titleLabel);

        // 创建中心面板（登录表单部分）
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Serif", Font.BOLD, 18));
        resultLabel.setForeground(Color.RED);
        centerPanel.add(resultLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(new JLabel("请选择一个登录角色"), gbc);

        gbc.gridx = 1;
        roleComboBox = new JComboBox<>(new String[]{"Admin", "Doctor", "Nurse"});
        centerPanel.add(roleComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(new JLabel("编号"), gbc);

        gbc.gridx = 1;
        idField = new JTextField(20);
        centerPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(new JLabel("登录密码"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        centerPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rememberMeCheckBox = new JCheckBox("记住我");
        centerPanel.add(rememberMeCheckBox, gbc);

        gbc.gridx = 1;
        loginButton = new JButton("登录");
        centerPanel.add(loginButton, gbc);

        // 将各部分添加到主面板
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        String role = (String) roleComboBox.getSelectedItem();
        String id = idField.getText();
        String password = new String(passwordField.getPassword());

        Client client = new Client();
        String result = client.login(role, id, password);

        // 解析服务器响应并进行处理
        if (result.equals("登录成功！")) {
            // 保存登录信息
            UserSession.setLoggedInUserId(id);
            UserSession.setLoggedInUserRole(role);

            switch (role) {
                case "Admin":
                    new AdminFrame().setVisible(true);
                    break;
                case "Doctor":
                    new DoctorFrame().setVisible(true);
                    break;
                case "Nurse":
                    new NurseFrame().setVisible(true);
                    break;
            }
            this.dispose();
        } else {
            resultLabel.setText(result);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}
