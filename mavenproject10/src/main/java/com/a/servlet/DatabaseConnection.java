package com.a.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String jdbcURL = "jdbc:mysql://localhost:3306/chatapp";
    private static final String jdbcUsername = "root";
    private static final String jdbcPassword = ""; 

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found");
        }
         return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }
}





  