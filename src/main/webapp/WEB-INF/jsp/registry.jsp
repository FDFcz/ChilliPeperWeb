<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta charset="utf-8" />
    <title>Debug View</title>
    <link rel="icon" href="../../static/images/chiliLogo.jpg" type="image/icon">
    <link href="../../static/css/Chilli.CSS" rel="Stylesheet">
</head>
<body>
<main>
    <h4>Registry your account</h4>
<form method="post">
    <label for="user name">User name:</label>
    <input type="text" id="user name" name="username"><br><br>
    <label for="password">Password:</label>
    <input type="text" id="password" name="password"><br><br>
    <label for="password2">Password*:</label>
    <input type="text" id="password2" name="password2"><br><br>
    <input type="submit" value="Submit">
</form>
    <%
        String currentUser = request.getParameter("username");
        if(currentUser !=null){
    %> <p style="color:red;";>User not added.<p> <%
        }
    %>
    <br>
    <img src="../../static/images/Chily.png">
</main>
</body>