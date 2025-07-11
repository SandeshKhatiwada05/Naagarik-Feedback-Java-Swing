package org.JSP;

import javax.swing.*;
import java.awt.*;

public class InitialPage extends JFrame {
    JButton signin, signup;

    public InitialPage() {
        setTitle("Naagarik Feedback");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(size);

        BackgroundPanel backgroundPanel = new BackgroundPanel("src/main/resources/Naagarik.png");
        backgroundPanel.setLayout(new BorderLayout());

        // Container panel on top (for weather and city selection)
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // top-bottom padding

        // City dropdown centered
        String[] cities = {"Sindhuli", "Kathmandu", "Pokhara", "Biratnagar", "Lalitpur", "Butwal"};
        JComboBox<String> cityDropdown = new JComboBox<>(cities);
        cityDropdown.setMaximumSize(new Dimension(200, 30));
        cityDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(cityDropdown);
        topPanel.add(Box.createVerticalStrut(10)); // spacing

        // Weather panel centered and sized smaller
        WeatherPanel weatherPanel = new WeatherPanel((String) cityDropdown.getSelectedItem());
        weatherPanel.setPreferredSize(new Dimension(250, 110));
        weatherPanel.setMaximumSize(new Dimension(250, 110));
        weatherPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(weatherPanel);

        cityDropdown.addActionListener(e -> {
            WeatherPanel newWeather = new WeatherPanel((String) cityDropdown.getSelectedItem());
            newWeather.setPreferredSize(new Dimension(250, 110));
            newWeather.setMaximumSize(new Dimension(250, 110));
            newWeather.setAlignmentX(Component.CENTER_ALIGNMENT);

            topPanel.remove(2);  // remove old weather panel
            topPanel.add(newWeather, 2);
            topPanel.revalidate();
            topPanel.repaint();
        });

        // Login panel for Sign In / Sign Up buttons
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.gridx = 0;

        signin = createStyledButton("Sign In", new Color(30, 144, 255));
        signup = createStyledButton("Sign Up", new Color(34, 139, 34));

        loginPanel.add(signin, gbc);
        gbc.gridy = 1;
        loginPanel.add(signup, gbc);

        // Container panel for bottom login buttons, centered
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
        bottomPanel.add(loginPanel);

        // Add topPanel at NORTH, bottomPanel at SOUTH
        backgroundPanel.add(topPanel, BorderLayout.NORTH);
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

        signin.addActionListener(e -> new SignIn());
        signup.addActionListener(e -> new SignUp());

        setContentPane(backgroundPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(bgColor.darker(), 2, true));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(140, 40));
        return button;
    }

    class BackgroundPanel extends JPanel {
        private final Image backgroundImage;

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
