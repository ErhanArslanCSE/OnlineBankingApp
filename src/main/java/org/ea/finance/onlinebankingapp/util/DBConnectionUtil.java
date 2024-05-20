package org.ea.finance.onlinebankingapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/finansapps";
    private static final String USERNAME = "finansapps";
    private static final String PASSWORD = "123456";

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // Handle driver class not found exception
            throw new IllegalStateException("MySQL JDBC driver not found", e);
        }
    }

    // Private constructor to prevent instantiation from outside
    private DBConnectionUtil() {
    }

    // Static method to get the singleton instance of the connection
    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DBConnectionUtil.class) {
                if (connection == null) {
                    try {
                        // Create the connection
                        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        // Handle any errors
                    }
                }
            }
        }
        return connection;
    }
}
