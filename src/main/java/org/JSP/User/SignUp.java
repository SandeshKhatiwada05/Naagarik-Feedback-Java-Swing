package org.JSP.User;

import org.JSP.DBConnection;
import org.JSP.InitialPage;
import org.JSP.WeatherPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

public class SignUp extends JFrame implements ActionListener {

    DBConnection dbc = new DBConnection();

    JLabel name, password, age, location, phoneNumber;
    JTextField txtName, txtAge, txtLocation, txtPhoneNumber;
    JPasswordField txtPassword;
    JButton btnSignUp;

    public SignUp() {
        setTitle("Naagarik Feedback - Sign Up");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(size);

        BackgroundPanel backgroundPanel = new BackgroundPanel("src/main/resources/Naagarik.png");
        backgroundPanel.setLayout(null);

        // ---------- Weather Dropdown + Panel ----------
        int weatherWidth = 320;
        int weatherHeight = 140;
        int weatherX = 30;
        int weatherY = 30;

        String[] cities = {"Sindhuli", "Kathmandu", "Pokhara", "Biratnagar", "Lalitpur", "Butwal"};
        JComboBox<String> cityDropdown = new JComboBox<>(cities);
        cityDropdown.setBounds(weatherX, weatherY, weatherWidth, 30);
        backgroundPanel.add(cityDropdown);

        WeatherPanel weatherPanel = new WeatherPanel((String) cityDropdown.getSelectedItem());
        weatherPanel.setBounds(weatherX, weatherY + 35, weatherWidth, weatherHeight);
        backgroundPanel.add(weatherPanel);

        cityDropdown.addActionListener(e -> {
            backgroundPanel.remove(weatherPanel);
            WeatherPanel newWeather = new WeatherPanel((String) cityDropdown.getSelectedItem());
            newWeather.setBounds(weatherX, weatherY + 35, weatherWidth, weatherHeight);
            backgroundPanel.add(newWeather);
            backgroundPanel.revalidate();
            backgroundPanel.repaint();
        });

        // ---------- Signup Form Panel ----------
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBackground(new Color(255, 255, 255, 180));
        formPanel.setBounds(size.width / 2 - 200, size.height / 2 - 175, 400, 350);

        // Close Button
        ImageIcon icon = new ImageIcon("src/main/resources/Close.png");
        Image img = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JButton closeButton = new JButton(scaledIcon);
        closeButton.setBounds(370, 8, 24, 24);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeButton.setToolTipText("Close");
        closeButton.addActionListener(e -> dispose());
        formPanel.add(closeButton);

        // Inner content panel for fields
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBounds(0, 30, 400, 320);
        formPanel.add(contentPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        name = new JLabel("Name:");
        txtName = new JTextField(20);
        contentPanel.add(name, gbc);
        gbc.gridx = 1;
        contentPanel.add(txtName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        password = new JLabel("Password:");
        txtPassword = new JPasswordField(20);
        contentPanel.add(password, gbc);
        gbc.gridx = 1;
        contentPanel.add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        age = new JLabel("Age:");
        txtAge = new JTextField(20);
        contentPanel.add(age, gbc);
        gbc.gridx = 1;
        contentPanel.add(txtAge, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        location = new JLabel("Location:");
        txtLocation = new JTextField(20);
        contentPanel.add(location, gbc);
        gbc.gridx = 1;
        contentPanel.add(txtLocation, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        phoneNumber = new JLabel("Phone Number:");
        txtPhoneNumber = new JTextField(20);
        contentPanel.add(phoneNumber, gbc);
        gbc.gridx = 1;
        contentPanel.add(txtPhoneNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnSignUp = new JButton("Sign Up");

        btnSignUp.setBackground(new Color(34, 139, 34));  // Forest Green
        btnSignUp.setForeground(Color.WHITE);
        btnSignUp.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSignUp.setFocusPainted(false);
        btnSignUp.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 0), 2, true));
        btnSignUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignUp.setPreferredSize(new Dimension(140, 40));

        btnSignUp.addActionListener(this);
        contentPanel.add(btnSignUp, gbc);

        backgroundPanel.add(formPanel);
        setContentPane(backgroundPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSignUp) {
            try {
                PreparedStatement statement = dbc.con.prepareStatement(
                        "INSERT INTO people (name, age, location, phone_number, password) VALUES (?, ?, ?, ?, ?)"
                );

                statement.setString(1, txtName.getText());
                statement.setInt(2, Integer.parseInt(txtAge.getText()));
                statement.setString(3, txtLocation.getText());
                statement.setString(4, txtPhoneNumber.getText());
                statement.setString(5, new String(txtPassword.getPassword()));

                int result = statement.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(this,
                            "Account Created!\nRemember:\nName: " + txtName.getText() +
                                    "\nPassword: " + new String(txtPassword.getPassword()));
                    new InitialPage();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Signup failed.");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error during Sign Up.");
            }
        }
    }

    // Background panel class
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
