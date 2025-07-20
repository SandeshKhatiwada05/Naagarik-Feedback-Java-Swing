package org.JSP.Admin;

import org.JSP.Admin.AdminCredentials;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AdminLoginDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public AdminLoginDialog(JFrame parent) {
        super(parent, "Admin Login", true);
        setLayout(new GridBagLayout());
        setSize(350, 200);
        setLocationRelativeTo(parent);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username Label & Field
        JLabel userLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(userLabel, gbc);

        usernameField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        // Password Label & Field
        JLabel passLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passLabel, gbc);

        passwordField = new JPasswordField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        // Login Button
        JButton loginBtn = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(loginBtn, gbc);

        loginBtn.addActionListener(this::handleLogin);
    }

    private void handleLogin(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals(AdminCredentials.ADMIN_USERNAME) && password.equals(AdminCredentials.ADMIN_PASSWORD)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            dispose(); // Close login dialog
            new AdminRefactor(); // ✅ FIXED THIS LINE!
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
