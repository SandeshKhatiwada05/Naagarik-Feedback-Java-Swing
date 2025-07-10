package org.JSP;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class LandingPage extends JFrame {

    DBConnection dbc = new DBConnection();
    JTable feedbackTable;
    DefaultTableModel tableModel;
    String userName;

    public LandingPage(String userName) {
        this.userName = userName;

        setTitle("Naagarik Feedback - Dashboard");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(size);

        BackgroundPanel backgroundPanel = new BackgroundPanel("src/main/resources/MainPageImage.png");
        backgroundPanel.setLayout(null);

        // Buttons
        JButton btnAdd = new JButton("Add Feedback");
        JButton btnEdit = new JButton("Edit Selected");
        JButton btnDelete = new JButton("Delete Selected");

        btnAdd.setBounds(50, 30, 150, 30);
        btnEdit.setBounds(220, 30, 150, 30);
        btnDelete.setBounds(390, 30, 170, 30);

        // Button actions
        btnAdd.addActionListener(e -> showAddFeedbackForm());
        btnEdit.addActionListener(e -> showEditFeedbackForm());
        btnDelete.addActionListener(e -> deleteSelectedFeedback());

        backgroundPanel.add(btnAdd);
        backgroundPanel.add(btnEdit);
        backgroundPanel.add(btnDelete);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Message", "Submitted At"}, 0);
        feedbackTable = new JTable(tableModel);
        feedbackTable.setRowHeight(28);
        feedbackTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        feedbackTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        feedbackTable.getColumnModel().getColumn(1).setPreferredWidth(600);
        feedbackTable.getColumnModel().getColumn(2).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(feedbackTable);
        scrollPane.setBounds(50, 80, size.width - 200, size.height - 200);
        backgroundPanel.add(scrollPane);

        // Load user's feedbacks
        loadFeedbackData();

        setContentPane(backgroundPanel);
        setVisible(true);
    }

    private void loadFeedbackData() {
        try {
            tableModel.setRowCount(0);
            String query = "SELECT feedback.feedback_id, feedback.message, feedback.submitted_at FROM feedback JOIN people ON feedback.person_id = people.id WHERE people.name = ? ORDER BY submitted_at DESC";
            PreparedStatement stmt = dbc.con.prepareStatement(query);
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("feedback_id");
                String msg = rs.getString("message");
                Timestamp submitted = rs.getTimestamp("submitted_at");
                tableModel.addRow(new Object[]{id, msg, submitted});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showAddFeedbackForm() {
        JTextArea feedbackArea = new JTextArea(5, 30);
        feedbackArea.setLineWrap(true);
        feedbackArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(feedbackArea);

        int option = JOptionPane.showConfirmDialog(this, scrollPane, "Add Feedback", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String message = feedbackArea.getText().trim();
            if (!message.isEmpty()) {
                try {
                    PreparedStatement stmt = dbc.con.prepareStatement(
                            "INSERT INTO feedback (person_id, message, submitted_at) VALUES ((SELECT id FROM people WHERE name = ?), ?, CURRENT_TIMESTAMP)"
                    );
                    stmt.setString(1, userName);
                    stmt.setString(2, message);
                    stmt.executeUpdate();
                    loadFeedbackData();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void showEditFeedbackForm() {
        int selectedRow = feedbackTable.getSelectedRow();
        if (selectedRow != -1) {
            int feedbackId = (int) tableModel.getValueAt(selectedRow, 0);
            String currentMessage = (String) tableModel.getValueAt(selectedRow, 1);

            JTextArea feedbackArea = new JTextArea(currentMessage, 5, 30);
            feedbackArea.setLineWrap(true);
            feedbackArea.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(feedbackArea);

            int option = JOptionPane.showConfirmDialog(this, scrollPane, "Edit Feedback", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String newMessage = feedbackArea.getText().trim();
                if (!newMessage.isEmpty()) {
                    try {
                        PreparedStatement stmt = dbc.con.prepareStatement(
                                "UPDATE feedback SET message = ? WHERE feedback_id = ? AND person_id = (SELECT id FROM people WHERE name = ?)"
                        );
                        stmt.setString(1, newMessage);
                        stmt.setInt(2, feedbackId);
                        stmt.setString(3, userName);
                        stmt.executeUpdate();
                        loadFeedbackData();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a feedback to edit.");
        }
    }

    private void deleteSelectedFeedback() {
        int selectedRow = feedbackTable.getSelectedRow();
        if (selectedRow != -1) {
            int feedbackId = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this feedback?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    PreparedStatement stmt = dbc.con.prepareStatement(
                            "DELETE FROM feedback WHERE feedback_id = ? AND person_id = (SELECT id FROM people WHERE name = ?)"
                    );
                    stmt.setInt(1, feedbackId);
                    stmt.setString(2, userName);
                    stmt.executeUpdate();
                    loadFeedbackData();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a feedback to delete.");
        }
    }

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}