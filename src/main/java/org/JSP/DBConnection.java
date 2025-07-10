package org.JSP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public Connection con;

    public DBConnection() {
        try {
            // Load the MySQL JDBC driver (optional in newer versions)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/NaagarikFeedbackSwing",  // Database URL
                    "root",                                               // Username (change if different)
                    "admin"                                       // Password (replace with your real password)
            );
            System.out.println("✅ Database connected successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("❌ Failed to connect to database.");
            e.printStackTrace();
        }
    }

}
