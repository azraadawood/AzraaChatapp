package com.a.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        
        if (session != null && session.getAttribute("username") != null) {
            // Forward the request to home.jsp if the user is logged in
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } else {
            // Redirect to login.jsp 
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        // Check if the session exists and contains a "username" attribute
        if (session != null && session.getAttribute("username") != null) {
            // Retrieve the action parameter from the request
            String action = request.getParameter("action");

            if (action != null) {
   
                switch (action) {
                    default -> {
                        // Forward the request to home.jsp for any unspecified action
                        request.getRequestDispatcher("home.jsp").forward(request, response);
                    }
                }
            } else {
                
                request.getRequestDispatcher("home.jsp").forward(request, response);
            }
        } else {
            // Redirect to login.jsp
            response.sendRedirect("login.jsp");
        }
    }
}



