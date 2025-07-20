package org.JSP.Admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AdminLoginDialog extends JDialog {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public AdminLoginDialog(JFrame parent) {
        super(parent, "Admin Login", true);
        setSize(420, 300);
        setLocationRelativeTo(parent);
        setUndecorated(true); // remove default border
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        setLayout(new BorderLayout());

        // 🟣 Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(40, 40, 40));
        headerPanel.setPreferredSize(new Dimension(400, 50));
        JLabel title = new JLabel("Admin Login");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        headerPanel.add(title);
        add(headerPanel, BorderLayout.NORTH);

        // 🔵 Center Login Panel
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(30, 30, 30));
        loginPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Username Label + Field
        JLabel userLabel = new JLabel("👤 Username");
        userLabel.setForeground(Color.WHITE);
        loginPanel.add(userLabel, gbc);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        usernameField.setBackground(new Color(50, 50, 50));
        usernameField.setForeground(Color.WHITE);
        usernameField.setCaretColor(Color.WHITE);
        usernameField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        loginPanel.add(usernameField, gbc);

        // Password Label + Field
        JLabel passLabel = new JLabel("🔒 Password");
        passLabel.setForeground(Color.WHITE);
        loginPanel.add(passLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setBackground(new Color(50, 50, 50));
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        loginPanel.add(passwordField, gbc);

        // Login Button
        JButton loginBtn = new JButton("→ Login");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginBtn.setBackground(new Color(70, 130, 180));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginBtn.addActionListener(this::handleLogin);
        loginPanel.add(loginBtn, gbc);

        add(loginPanel, BorderLayout.CENTER);
    }

    private void handleLogin(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals(AdminCredentials.ADMIN_USERNAME) &&
                password.equals(AdminCredentials.ADMIN_PASSWORD)) {
            dispose();
            new AdminRefactor();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
