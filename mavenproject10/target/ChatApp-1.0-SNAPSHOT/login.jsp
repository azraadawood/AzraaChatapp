<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <style> 
        /*Css styling*/
        body {
            font-family: Arial, sans-serif;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }

        h1 {
            color: blue;
            margin-bottom: 20px;
        }

        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid blue;
            border-radius: 5px;
            box-sizing: border-box;
        }

        input[type="text"]:focus, input[type="password"]:focus {
            border-color: darkblue;
            outline: none;
        }

        button {
            background-color: blue;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            width: 100%;
            cursor: pointer;
            margin: 10px 0;
        }

        button:hover {
            background-color: darkblue;
        }

        a {
            color: blue;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        .error-message {
            color: red;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <!-- Welcome message-->
    <h1>Welcome to Mzansi Chat Zone</h1>
    <h1>Login</h1>

    <!--  error message  -->
    <%
        String errorMessage = request.getParameter("error");
        if (errorMessage != null) {
    %>
        <p class="error-message"><%= errorMessage %></p>
    <%
        }
    %>
       <!-- Input field for the username and password-->
    <form action="login" method="post">
        <input type="text" id="username" name="username" placeholder="Username" required><br>
        <input type="password" id="password" name="password" placeholder="Password" required><br>
        <button type="submit">Login</button>
    </form>
       <!-- Directs to register if you have no account -->
    <p>Don't have an account? <a href="register.jsp">Register</a></p>
</body>
</html>




