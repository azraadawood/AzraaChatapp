<script>
    let socket = new WebSocket("ws://localhost:8080/chat-app/chat");

    socket.onopen = function() {
        console.log("WebSocket connection established.");
        updateConnectionStatus("Connected");
    };

    socket.onmessage = function(event) {
        let chatWindow = document.getElementById("chat-window");
        let message = document.createElement("div");
        message.textContent = event.data;
        chatWindow.appendChild(message);
        chatWindow.scrollTop = chatWindow.scrollHeight;
    };

    socket.onerror = function(error) {
        console.error("WebSocket Error: " + error.message);
        updateConnectionStatus("Error");
    };

    socket.onclose = function() {
        console.log("WebSocket connection closed.");
        updateConnectionStatus("Disconnected");
    };

    function sendMessage() {
        let messageInput = document.getElementById("message");
        let message = messageInput.value;

        if (socket.readyState === WebSocket.OPEN) {
            socket.send(message);
            messageInput.value = "";
        } else {
            console.error("WebSocket is not open. Cannot send message.");
            updateConnectionStatus("Disconnected");
        }
    }

    function updateConnectionStatus(status) {
        let statusElement = document.getElementById("connection-status");
        statusElement.textContent = "Status: " + status;
    }
</script>
