package org.JSP.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminRefactor extends JFrame {
    JTable table;
    DefaultTableModel model;

    public AdminRefactor() {
        setTitle("Admin Panel - User Overview");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Model with hidden ID column (not shown in table)
        model = new DefaultTableModel(new String[]{"ID", "Name", "Location", "Phone", "Feedback"}, 0);
        table = new JTable(model) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Hide ID column from view
        table.removeColumn(table.getColumnModel().getColumn(0));

        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        loadUsers();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Right-click menu for delete
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Delete User");
        popupMenu.add(deleteItem);

        deleteItem.addActionListener(e -> deleteUser());

        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                if (row == -1) return; // No row selected

                table.setRowSelectionInterval(row, row);

                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenu.show(table, e.getX(), e.getY());
                } else if (e.getClickCount() == 1 && col == 3) {  // Feedback column is at index 3 after hiding ID
                    showFeedbackPopup(row);
                }
            }
        });

        setVisible(true);
    }

    private void loadUsers() {
        try {
            Connection con = new org.JSP.DBConnection().con;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, name, location, phone_number FROM people");
            model.setRowCount(0);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String location = rs.getString("location");
                String phone = rs.getString("phone_number");

                model.addRow(new Object[]{id, name, location, phone, "+ View Feedbacks"});
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading users.");
        }
    }

    private void showFeedbackPopup(int row) {
        int personId = (int) model.getValueAt(row, 0);  // Hidden ID column
        String name = model.getValueAt(row, 1).toString();

        JDialog feedbackDialog = new JDialog(this, "Feedback from " + name, true);
        feedbackDialog.setSize(500, 400);
        feedbackDialog.setLocationRelativeTo(this);
        feedbackDialog.setLayout(new BorderLayout());

        JTextArea feedbackArea = new JTextArea();
        feedbackArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        feedbackArea.setEditable(false);

        try {
            Connection con = new org.JSP.DBConnection().con;
            PreparedStatement ps = con.prepareStatement("SELECT message, submitted_at FROM feedback WHERE person_id = ?");
            ps.setInt(1, personId);
            ResultSet rs = ps.executeQuery();

            StringBuilder allFeedback = new StringBuilder();
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("submitted_at");
                String dateStr = ts != null ? ts.toString() : "Unknown date";
                allFeedback.append("- [").append(dateStr).append("] ").append(rs.getString("message")).append("\n\n");
            }

            feedbackArea.setText(allFeedback.length() == 0 ? "No feedback available." : allFeedback.toString());
        } catch (Exception e) {
            feedbackArea.setText("Error loading feedback.");
            e.printStackTrace();
        }

        JScrollPane scroll = new JScrollPane(feedbackArea);
        feedbackDialog.add(scroll, BorderLayout.CENTER);

        JButton closeBtn = new JButton("Close");
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        closeBtn.addActionListener(e -> feedbackDialog.dispose());
        feedbackDialog.add(closeBtn, BorderLayout.SOUTH);

        feedbackDialog.setVisible(true);
    }

    private void deleteUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) return;

        int personId = (int) model.getValueAt(selectedRow, 0);
        String name = model.getValueAt(selectedRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete all information for user: " + name + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection con = new org.JSP.DBConnection().con;
                PreparedStatement ps1 = con.prepareStatement("DELETE FROM feedback WHERE person_id = ?");
                ps1.setInt(1, personId);
                ps1.executeUpdate();

                PreparedStatement ps2 = con.prepareStatement("DELETE FROM people WHERE id = ?");
                ps2.setInt(1, personId);
                ps2.executeUpdate();

                loadUsers();
                JOptionPane.showMessageDialog(this, "User deleted.");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting user.");
            }
        }
    }
}
