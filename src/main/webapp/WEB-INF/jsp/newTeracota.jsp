<%@ page import="com.example.Structures.Teracota" %>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta charset="utf-8" />
    <title>Order a teracota</title>
    <link rel="icon" href="../../static/images/chiliLogo.jpg" type="image/icon">
    <link href="../../static/css/Chilli.CSS" rel="Stylesheet">
</head>
<body><main>
    <h4>Select teracota</h4>
<form method = "post">
    <label for="name">Name:</label>
    <input type="text" id="name" name="name"><br><br>
    <label for="plantType">Choose a chilly:</label>
    <select name="plantType" id="plantType">
       <%for(Teracota.PlantTypes pt: Teracota.PlantTypes.values()){%>

           <option value=<%=pt%>><%=pt%></option>
<%}%>
    </select>
    <input type="submit" value="Order">
</form>
    <br>
    <img src="../../static/images/Chily.png">
</main>
</body>