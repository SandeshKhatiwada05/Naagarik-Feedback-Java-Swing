package org.JSP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SignIn extends JFrame implements ActionListener {

    DBConnection dbc = new DBConnection();

    JLabel nameLabel, passwordLabel;
    JTextField txtName;
    JPasswordField txtPassword;
    JButton btnSignIn;

    public SignIn() {
        setTitle("Naagarik Feedback - Sign In");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(size);

        BackgroundPanel backgroundPanel = new BackgroundPanel("src/main/resources/Naagarik.png");
        backgroundPanel.setLayout(null);

        // ----------- Weather Section -------------
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

        // ----------- Sign In Panel -------------
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(255, 255, 255, 180));
        formPanel.setBounds(size.width / 2 - 200, size.height / 2 - 100, 400, 200);
        formPanel.setLayout(null); // use null layout for precise control

        // Load and resize icon to 16x16
        ImageIcon icon = new ImageIcon("src/main/resources/Close.png");
        Image img = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);

        // Create close button
        JButton closeButton = new JButton(scaledIcon);
        closeButton.setBounds(370, 8, 24, 24);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        formPanel.add(closeButton);

        // Close action
        closeButton.addActionListener(e -> dispose());


        // Form layout (manual)
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 40, 80, 25);
        formPanel.add(nameLabel);

        txtName = new JTextField();
        txtName.setBounds(120, 40, 220, 25);
        formPanel.add(txtName);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 80, 80, 25);
        formPanel.add(passwordLabel);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(120, 80, 220, 25);
        formPanel.add(txtPassword);

        btnSignIn = new JButton("Sign In");
        btnSignIn.setBounds(130, 130, 140, 40);
        btnSignIn.setBackground(new Color(30, 144, 255));
        btnSignIn.setForeground(Color.WHITE);
        btnSignIn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSignIn.setFocusPainted(false);
        btnSignIn.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 2, true));
        btnSignIn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        formPanel.add(btnSignIn);
        btnSignIn.addActionListener(this);

        backgroundPanel.add(formPanel);
        setContentPane(backgroundPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSignIn) {
            String name = txtName.getText();
            String password = new String(txtPassword.getPassword());

            try {
                PreparedStatement statement = dbc.con.prepareStatement(
                        "SELECT * FROM people WHERE name = ? AND password = ?"
                );
                statement.setString(1, name);
                statement.setString(2, password);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    new LandingPage(name);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid name or password.");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error during Sign In.");
            }
        }
    }

    // Background Panel class
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
