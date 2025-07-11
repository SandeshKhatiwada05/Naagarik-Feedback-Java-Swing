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

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = 0;

        nameLabel = new JLabel("Name:");
        txtName = new JTextField(20);
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(txtName, gbc);

        gbc.gridx = 0; gbc.gridy++;
        passwordLabel = new JLabel("Password:");
        txtPassword = new JPasswordField(20);
        formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnSignIn = new JButton("Sign In");

        btnSignIn.setBackground(new Color(30, 144, 255));
        btnSignIn.setForeground(Color.WHITE);
        btnSignIn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSignIn.setFocusPainted(false);
        btnSignIn.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 2, true));
        btnSignIn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSignIn.setPreferredSize(new Dimension(140, 40));

        btnSignIn.addActionListener(this);
        formPanel.add(btnSignIn, gbc);

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
