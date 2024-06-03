<%@ page import="com.example.Controlers.ChiliPeperApplication" %>
<%@ page import="com.example.Structures.Teracota" %>
<%@ page import="java.util.List" %>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta charset="utf-8" />
    <title>user page</title>
    <link rel="icon" href="../../static/images/chiliLogo.jpg" type="image/icon">
    <link href="../../static/css/Chilli.CSS" rel="Stylesheet">
</head>
<body><main>
<h1>Welcome <%=ChiliPeperApplication.getUser(Integer.valueOf(request.getParameter("id"))).getName()%></h1>
<table border="1">
    <tr>
        <th colspan="3">Teracotas</th>
    </tr>
    <tr>
        <th width="20%">Name:</th>
        <th width="15%" >Grow days/%:</th>
        <th>details:</th>
    </tr>
    <%
        List<Teracota> teracotas = ChiliPeperApplication.getUserWithTeracotas(Integer.valueOf(request.getParameter("id"))).getOwnedTeracotas();
        for(int i = 0; i < teracotas.size(); i++) {
    %>
    <tr>
        <td><%=teracotas.get(i).getNome()%></td>
        <%String redirect = "teracotaDetail?id="+ request.getParameter("id")+"&&teracota="+teracotas.get(i).getId();%>
        <td class="persents"><span class="dayLeft"><%=teracotas.get(i).getPlantedAt()%></span><span class="dayToGrow"><%=teracotas.get(i).getGrowDays()%></span></td>
        <td><a href=<%=redirect%>>detail</a></td>
    </tr>
    <% } %>
</table>

<%String toOrder = "newTeracota?id="+ request.getParameter("id");%>
<a href=<%=toOrder%>>Order a teracota</a>
    <%String toChangePass = "ChangePassword?id="+ request.getParameter("id");%>
    <a href=<%=toChangePass%>>Change Password</a>
    <%String toLogout = "logOut?id="+ request.getParameter("id");%>
    <a href=<%=toLogout%>>LogOut</a>
    <br>
    <img src="../../static/images/Chily.png">
</main>
<script type="text/javascript" src="../../static/js/persentsCounterUI.js"></script>
</body>