<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home Page</title>
    <style>
         /* CSS styling */
        body {
            font-family: Arial, sans-serif;
            background-color: white;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background-color: lightsteelblue;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            width: 300px;
            padding: 20px;
            text-align: center;
        }
        
        .menu {
            margin-top: 20px;
        }
        
        .menu form {
            margin-bottom: 10px;
        }
        .menu button {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            background-color: white;
            transition: background-color 0.3s;
        }
        
        
    </style>
</head>
<body>
    <div class="container">
         <!-- Profile section -->
        <div class="profile">
             <!-- Welcome message -->
            <h2>Welcome, <%= request.getSession().getAttribute("username") %>!</h2>
            <p>You have new messages!</p>
        </div>
        <div class="menu">
         <!-- Navigate to the Conversations page -->
            <form method="get" action="Users">
    <button type="submit">Conversations</button>
</form>
           <!-- Navigate to the Contacts page -->
               <form method="get" action="Users">
    <button type="submit">Contacts</button>
</form>
            
             <!-- Navigate to the memories page -->
                <form method="get" action="memories.jsp">
    <button type="submit">Memories</button>
</form>
            
        <br><br>
          <!-- Link to logout -->
        <a href="logout.jsp">Logout</a>
        
    </div>
</div>
</body>
</html>



       
        