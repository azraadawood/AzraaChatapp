<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register Page</title>
   
    <style>
/*Css Styling*/
        body {
            font-family: Arial, sans-serif;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }

        h2 {
            color: blue;
            margin-bottom: 20px;
        }
        
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            padding-left: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        input[type="submit"] {
            background-color: blue;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            width: 100%;
            cursor: pointer;
            margin: 10px 0;
        }

        input[type="submit"]:hover {
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
            font-weight: bold;
        }
    </style>
</head>
<body>
    <!-- Welcome message-->
    <h2>Welcome to Mzansi Chat Zone</h2>
    <h2>Register</h2>
    <form action="register" method="post">
        <input type="text" id="username" name="username" placeholder="Username" required><br>
        <input type="password" id="password" name="password" placeholder="Password" required><br>
        <input type="submit" value="Register">
    </form>
    <!-- Have an account, directs to login page-->
    <p>Have an account already? <a href="login.jsp">Login here</a></p>
    <!-- Error message-->
    <% 
        String error = request.getParameter("error");
        if (error != null && !error.isEmpty()) {
    %>
        <p class="error-message"><%= error %></p>
    <% 
        }
    %>
</body>
</html>


