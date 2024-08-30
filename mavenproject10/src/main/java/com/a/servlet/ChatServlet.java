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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/chat")
public class ChatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the sender 
        String sender = (String) request.getSession().getAttribute("username");
        // Get the recipient's username 
        String recipient = request.getParameter("recipient");

        // If either sender or recipient is missing, redirect.
        if (sender == null || recipient == null) {
            response.sendRedirect("conversations");
            return; 
        }

        // Establish a database connection 
        try (Connection con = DatabaseConnection.getConnection()) {
            // SQL query to select messages between the sender and recipient, ordered by timestamp
            String SQL = "SELECT sender, message, timestamp FROM messages WHERE " +
                         "(sender = ? AND recipient = ?) OR (sender = ? AND recipient = ?) ORDER BY timestamp";
            try (PreparedStatement statement = con.prepareStatement(SQL)) {
                
                statement.setString(1, sender);
                statement.setString(2, recipient);
                statement.setString(3, recipient);
                statement.setString(4, sender);

                // Execute the query 
                try (ResultSet resultSet = statement.executeQuery()) {
                    // Create a list to hold the messages
                    List<Message> messages = new ArrayList<>();
                    while (resultSet.next()) {
                     
                        Message message = new Message();
                        message.setSender(resultSet.getString("sender"));
                        message.setMessage(resultSet.getString("message"));
                        message.setTimestamp(resultSet.getString("timestamp"));
                        // Add the message to the list
                        messages.add(message);
                    }
                    
                    request.setAttribute("messages", messages);
                    request.setAttribute("recipient", recipient);
                    // Forward the request to chat.jsp 
                    request.getRequestDispatcher("chat.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            
            throw new ServletException("Database error retrieving chat history", e);
        }
    }
}
