<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chat Page</title>
    <style>
        /*Css styling*/
        #chat-window {
            border: 2px solid black;
            width: 290px;
            height: 300px;
            overflow-y: scroll;
            padding: 5px;
            background-color: white;
        }

        .message {
            margin: 5px 0;
            padding: 5px;
            border-radius: 4px;
            background-color: whitesmoke;
        }

        .sender {
            font-weight: bold;
        }

        input[type="text"] {
            width: 290px;
            height: 30px;
            padding: 5px;
            border: 1px solid;
            border-radius: 5px;
            margin-top: 0px;
        }

        button {
            padding: 10px 20px;
            color: white;
            background-color: blue;
            border: none;
            border-radius: 5px;
            font-size: 1em;
            cursor: pointer;
            outline: none;
            display: flex;
        }

        .timestamp {
            font-size: small;
            color: gray;
        }

        #recipient-name {
            font-size: 1.2em;
            font-weight: bold;
            margin-bottom: 10px;
        }
        .container {
            background-color: lightsteelblue;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            width: 300px;
            padding: 20px;
           
        }
    </style>
    <script>
        // JavaScript 

        // Get the username and recipient 
        let username = "<%= request.getSession().getAttribute("username") %>";
        let recipient = "<%= request.getParameter("recipient") %>";

        //  WebSocket connection to the server
        let socket = new WebSocket("ws://localhost:8080/mavenproject10/chat?username=" + username + "&recipient=" + recipient);

        // WebSocket connection is established
        socket.onopen = function() {
            console.log("WebSocket connection established.");
        };

        
        socket.onmessage = function(event) {
            let chatWindow = document.getElementById("chat-window");

            
            let message = document.createElement("div");
            message.className = "message";
            message.textContent = event.data;

            // Append the message to the chat window and scroll to the bottom
            chatWindow.appendChild(message);
            chatWindow.scrollTop = chatWindow.scrollHeight;
        };

        // Event handler for when there is an error with the WebSocket connection
        socket.onerror = function(error) {
            console.error("WebSocket Error: " + error.message);
        };

        // Event handler for when the WebSocket connection is closed
        socket.onclose = function() {
            console.log("WebSocket connection closed.");
        };

        // Function to send a message through the WebSocket connection
        function sendMessage() {
            let messageInput = document.getElementById("message");
            let message = username + ":" + recipient + ":" + messageInput.value;

            
            if (socket.readyState === WebSocket.OPEN) {
                socket.send(message);

                
                messageInput.value = "";
            } else {
                console.error("WebSocket is not open. Cannot send message.");
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <!-- Display the recipient's name at the top of the chat window -->
        <div id="recipient-name"> <%= request.getParameter("recipient") %></div>

        <!-- Chat window to display messages -->
        <div id="chat-window">
            
            <c:forEach var="msg" items="${messages}">
                <div class="message">
                    <span class="sender">${msg.sender}</span>: 
                    ${msg.message} 
                    <span class="timestamp">(${msg.timestamp})</span>
                </div>
            </c:forEach>
        </div>
        
        <!-- Input field for typing a message -->
        <input type="text" id="message" placeholder="Type message here..." />
        
        <!-- Button to send the typed message -->
        <button onclick="sendMessage()">Send</button>

        <!-- Link to go back to the conversations -->
        <a href="conversations.jsp">Back to Home</a>
   </div>
</body>
</html>