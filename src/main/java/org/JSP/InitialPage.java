package org.JSP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitialPage extends JFrame implements ActionListener {

    JButton signin, signup;

    public InitialPage() {
        setTitle("Naagarik Feedback");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(size);

        BackgroundPanel backgroundPanel = new BackgroundPanel("src/main/resources/Naagarik.png");
        backgroundPanel.setLayout(null);

        // Weather dropdown and panel
        int weatherWidth = 270;
        int weatherHeight = 140;
        int weatherX = 50;
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

        // Login Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.gridx = 0;

        signin = new JButton("Sign In");
        signup = new JButton("Sign Up");

        signin.setBackground(new Color(30, 144, 255));
        signin.setForeground(Color.WHITE);
        signin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        signin.setFocusPainted(false);
        signin.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 2, true));
        signin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signin.setPreferredSize(new Dimension(140, 40));

        signup.setBackground(new Color(34, 139, 34));
        signup.setForeground(Color.WHITE);
        signup.setFont(new Font("Segoe UI", Font.BOLD, 16));
        signup.setFocusPainted(false);
        signup.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 0), 2, true));
        signup.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signup.setPreferredSize(new Dimension(140, 40));

        loginPanel.add(signin, gbc);
        gbc.gridy = 1;
        loginPanel.add(signup, gbc);

        loginPanel.setBackground(new Color(255, 255, 255, 180));
        loginPanel.setBounds(size.width / 2 - 150, size.height / 2 - 75, 300, 150);
        backgroundPanel.add(loginPanel);

        signup.addActionListener(this);
        signin.addActionListener(this);

        setContentPane(backgroundPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signin) {
            new SignIn();
        } else if (e.getSource() == signup) {
            new SignUp();
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

    public static void main(String[] args) {
        setDefaultLookAndFeelDecorated(true);
        new InitialPage();
    }
}
