package org.JSP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitialPage extends JFrame implements ActionListener {

    JButton signin, signup;

    public InitialPage() {
        // Set title and close behavior
        setTitle("Naagarik Feedback");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set fullscreen size
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(size);

        // Create background panel
        BackgroundPanel backgroundPanel = new BackgroundPanel("src/main/resources/Naagarik.png"); // Change to your image path
        backgroundPanel.setLayout(null); // Using null layout for manual positioning

        // Create a panel (login box) to hold the buttons
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20); // spacing between buttons
        gbc.gridx = 0;

        signin = new JButton("Sign In");
        signup = new JButton("Sign Up");

        // Style the Sign In button
        signin.setBackground(new Color(30, 144, 255));  // Dodger Blue
        signin.setForeground(Color.WHITE);
        signin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        signin.setFocusPainted(false);
        signin.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 2, true));
        signin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signin.setPreferredSize(new Dimension(140, 40));

        // Style the Sign Up button
        signup.setBackground(new Color(34, 139, 34));  // Forest Green
        signup.setForeground(Color.WHITE);
        signup.setFont(new Font("Segoe UI", Font.BOLD, 16));
        signup.setFocusPainted(false);
        signup.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 0), 2, true));
        signup.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signup.setPreferredSize(new Dimension(140, 40));

        loginPanel.add(signin, gbc);
        gbc.gridy = 1;
        loginPanel.add(signup, gbc);

        loginPanel.setBackground(new Color(255, 255, 255, 180)); // white with transparency
        loginPanel.setBounds(size.width / 2 - 150, size.height / 2 - 75, 300, 150); // centered box

        // Add login panel to background
        backgroundPanel.setLayout(null); // make sure layout is null
        backgroundPanel.add(loginPanel);

        // Add action listeners
        signup.addActionListener(this);
        signin.addActionListener(this);

        // Add panel to frame
        setContentPane(backgroundPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle sign in / sign up click
        if (e.getSource() == signin) {
            new SignIn();
        } else if (e.getSource() == signup) {
            new SignUp();
        }
    }

    // Custom JPanel to draw background image
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw image to fill the entire panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) {
        setDefaultLookAndFeelDecorated(true);
        new InitialPage();
    }
}
