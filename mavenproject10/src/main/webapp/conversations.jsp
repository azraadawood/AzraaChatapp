<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Conversation Page</title>
    <style>
/* css styling*/
        body {
            font-family: Arial, sans-serif;
            background-color: white;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        form {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
            text-align: center;
        }

        h3 {
            margin-top: 0;
            color: black;
        }

        h4 {
            font-weight: normal;
        }


        ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        ul li {
            margin-bottom: 10px;
        }

        ul li a {
            text-decoration: none;
            color: blue;
            font-weight: bold;
            display: block;
            padding: 10px;
            background-color: #f9f9f9;
            border-radius: 4px;
            transition: background-color 0.3s;
        }

        .backbtn {
            text-decoration: none;
            color: white;
            background-color: blue;
            padding: 10px 20px;
            border-radius: 4px;
            display: inline-block;
            margin-top: 20px;
            transition: background-color 0.3s;
        } 
    </style>
</head>
<body>
    <!-- Display a list of users available for chat -->
    <form>
        <h3>Start a New Chat</h3> 
        <h4>Chat With Users In Our Database!</h4> 
        <hr> 

        <ul>
            <c:forEach var="user" items="${users}">
               <!-- Each user is displayed, leading to a chat with that user -->
                <li><a href="chat.jsp?recipient=${user}">${user}</a></li>
            </c:forEach>
        </ul> 
        <!-- Back button to navigate to the home page -->
        <a href="home" class="backbtn">Back</a>
    </form>
</body>
</html>





