package com.a.servlet;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat")
public class ChatEndpoint {

    // A map to store user sessions
    private static final Map<String, Session> userSessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        // username and recipient from the request parameters
        Map<String, List<String>> parameters = session.getRequestParameterMap();
        List<String> usernameList = parameters.get("username");
        String username = usernameList != null && !usernameList.isEmpty() ? usernameList.get(0) : null;
        List<String> recipientList = parameters.get("recipient");
        String recipient = recipientList != null && !recipientList.isEmpty() ? recipientList.get(0) : null;

        // If a username is provided, store it 
        if (username != null) {
            session.getUserProperties().put("username", username);
            userSessions.put(username, session);
            System.out.println("User connected: " + username);

            
            if (recipient != null) {
                try {
                    loadMessageHistory(session, username, recipient);
                } catch (IOException e) {
                }
            }
        } else {
            // If no username is provided, close the session 
            System.out.println("No username provided in the request.");
            try {
                session.close();
            } catch (IOException e) {
            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        // Split the message 
        String[] parts = message.split(":", 3);
        if (parts.length != 3) {
            System.out.println("Invalid message format: " + message);
            return; 
        }

        String sender = parts[0];
        String recipient = parts[1];
        String messageText = parts[2];

        // Save the message to the database
        saveMessageToDatabase(sender, recipient, messageText);

        // Retrieve the recipient's session and send the message if the session is open
        Session recipientSession = userSessions.get(recipient);
        if (recipientSession != null && recipientSession.isOpen()) {
            recipientSession.getBasicRemote().sendText(sender + ": " + messageText);
        }

        // Send the message back to the sender with "You:" prefix
        session.getBasicRemote().sendText("You: " + messageText);
    }

    @OnClose
    public void onClose(Session session) {
        // Remove the user's session when they disconnect
        String username = (String) session.getUserProperties().get("username");
        if (username != null) {
            userSessions.remove(username);
            System.out.println("User disconnected: " + username);
        }
    }

    // Save a message to the database
    private void saveMessageToDatabase(String sender, String recipient, String message) {
        try (Connection con = DatabaseConnection.getConnection()) {
            String SQL = "INSERT INTO messages (sender, recipient, message) VALUES (?, ?, ?)";
            try (PreparedStatement statement = con.prepareStatement(SQL)) {
                statement.setString(1, sender);
                statement.setString(2, recipient);
                statement.setString(3, message);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
        }
    }

   
    private void loadMessageHistory(Session session, String sender, String recipient) throws IOException {
        try (Connection con = DatabaseConnection.getConnection()) {
            String SQL = "SELECT sender, message, timestamp FROM messages WHERE " +
                         "(sender = ? AND recipient = ?) OR (sender = ? AND recipient = ?) ORDER BY timestamp ASC";
            try (PreparedStatement statement = con.prepareStatement(SQL)) {
                statement.setString(1, sender);
                statement.setString(2, recipient);
                statement.setString(3, recipient);
                statement.setString(4, sender);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String msgSender = resultSet.getString("sender");
                        String msgText = resultSet.getString("message");
                        String msgTimestamp = resultSet.getString("timestamp");

                        // Format the message with timestamp 
                        String formattedMessage = msgSender + " (" + msgTimestamp + "): " + msgText;
                        session.getBasicRemote().sendText(formattedMessage);
                    }
                }
            }
        } catch (SQLException e) {
        }
    }
}
