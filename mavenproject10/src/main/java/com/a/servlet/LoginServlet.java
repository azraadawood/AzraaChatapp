package com.a.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

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
            
            // Establish a connection to the database
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

                //  SQL query to check 
                String sql = "SELECT * FROM users WHERE username=? AND password=?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, username);
                    statement.setString(2, password);

                    // Execute the query 
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            // User found, create a session and redirect to the home page
                            HttpSession session = request.getSession();
                            session.setAttribute("username", username);
                            response.sendRedirect("home.jsp");
                        } else {
                            // Invalid credentials, redirect back to login page with an error message
                            response.sendRedirect("login.jsp?error=Invalid username or password. Try again.");
                        }
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
        // Redirect to the login page
        response.sendRedirect("login.jsp");
    }
}



