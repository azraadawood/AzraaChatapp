package com.a.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    // Database connection 
    private static final String DB_URL = "jdbc:mysql://localhost:3306/chatapp";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get username and password 
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                
                // Check if the username exists in the database
                String checkSql = "SELECT COUNT(*) FROM users WHERE username=?";
                try (PreparedStatement checkStatement = connection.prepareStatement(checkSql)) {
                    checkStatement.setString(1, username);
                    
                    try (ResultSet resultSet = checkStatement.executeQuery()) {
                        if (resultSet.next() && resultSet.getInt(1) > 0) {
                            // Username exists, redirect to registration page with an error message
                            response.sendRedirect("register.jsp?error=Username already exists.");
                            return; 
                        }
                    }
                }

                // Insert the user into the database
                String insertSql = "INSERT INTO users (username, password) VALUES (?, ?)";
                try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                    insertStatement.setString(1, username);
                    insertStatement.setString(2, password);

                    int rowsInserted = insertStatement.executeUpdate();
                    if (rowsInserted > 0) {
                        // Registration successful, redirect to login page 
                        response.sendRedirect("login.jsp?message=Registration successful, please log in.");
                    } else {
                        // Registration failed, redirect back to registration page with an error message
                        response.sendRedirect("register.jsp?error=Registration failed, please try again.");
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            
            throw new ServletException("MySQL JDBC Driver not found", e);
        } catch (SQLException e) {
            
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect to the registration page
        response.sendRedirect("register.jsp");
    }
}
