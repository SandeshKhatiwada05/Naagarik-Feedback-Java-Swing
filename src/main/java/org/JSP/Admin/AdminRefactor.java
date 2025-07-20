package org.JSP;

import javax.swing.*;

public class AdminRefactor extends JFrame {
    public AdminRefactor() {
        setTitle("Admin Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Welcome to Admin Panel!", SwingConstants.CENTER);
        add(label);

        setVisible(true);
    }
}
