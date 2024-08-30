package com.a.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/Users")
public class NewChat extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String user = (String) request.getSession().getAttribute("username");
        System.out.println(user);
        List<String> users = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection())
        {
            String SQL = "SELECT username FROM users WHERE username != ?";

            try (PreparedStatement statement = con.prepareStatement(SQL)) {
                statement.setString(1, user);
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        users.add(rs.getString("username"));
                    }
                }
            } catch (SQLException e) {
            }

        } catch (SQLException e) {
            response.sendRedirect("index.html-CONERRER");
        }

        if (users.isEmpty()) {
            request.setAttribute("message", "no users are found!");
        }

        request.setAttribute("users", users);
        request.getRequestDispatcher("conversations.jsp").forward(request, response);
}

}