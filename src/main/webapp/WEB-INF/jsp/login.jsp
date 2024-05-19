<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta charset="utf-8" />
    <title>Chillipepers login</title>
    <link rel="icon" href="../../static/images/chiliLogo.jpg" type="image/icon">
    <link href="../../static/css/Chilli.CSS" rel="Stylesheet">
</head>
<body>
<main>
    <div>
<form method = "post">
    <h4>login</h4>
    <label for="user name">User name:</label>
    <input type="text" id="user name" name="username"><br><br>
    <label for="password">Password:</label>
    <input type="text" id="password" name="password"><br><br>
    <input type="submit" value="Submit">
    <%
        String currentUser = request.getParameter("username");
        if(currentUser !=null){
            %> <p style="color:red;";>WrongPass<p> <%
        }
    %>
</form></div>
    <br>
    <img src="../../static/images/Chily.png">
</main>
</body>