package com.a.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/conversations")
public class ConversationsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the username 
        String username = (String) request.getSession().getAttribute("username");
        
        // If the user is not logged in, redirect to the login page
        if (username == null) {
            response.sendRedirect("login.jsp");
            return; 
        }

        // Establish a database connection
        try (Connection con = DatabaseConnection.getConnection()) {
            // SQL query 
String SQL = "SELECT DISTINCT recipient FROM messages WHERE sender = ? UNION SELECT DISTINCT sender FROM messages WHERE recipient = ?";
            try (PreparedStatement statement = con.prepareStatement(SQL)) {
                
                statement.setString(1, username);
                statement.setString(2, username);
                
                // Execute the query 
                try (ResultSet resultSet = statement.executeQuery()) {
                    StringBuilder conversations = new StringBuilder();
                    while (resultSet.next()) {
                       
                        String conversationPartner = resultSet.getString(1);
                       
                        conversations.append("<li><a href=\"/chat?recipient=")
                                     .append(conversationPartner)
                                     .append("\">")
                                     .append(conversationPartner)
                                     .append("</a></li>");
                    }
                    
                    request.setAttribute("conversations", conversations.toString());
                    // Forward the request to chat.jsp 
                    request.getRequestDispatcher("chat.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
           
            throw new ServletException("Database error retrieving conversations", e);
        }
    }
}
