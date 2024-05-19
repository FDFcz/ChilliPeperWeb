<%@ page import="com.example.Controlers.ChiliPeperApplication" %>
<%@ page import="com.example.Structures.Teracota" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.Structures.Schedule" %>
<%@ page import="com.example.Structures.Cron" %>
<%Teracota currentTeracota = ChiliPeperApplication.getTeracota(Integer.valueOf(request.getParameter("teracota")));%>
<%List<Cron> crons = ChiliPeperApplication.getCronsForTeracota(currentTeracota.getId());%>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta charset="utf-8" />
    <title>your Teracota:<%=currentTeracota%></title>
    <link rel="icon" href="../../static/images/chiliLogo.jpg" type="image/icon">
    <link href="../../static/css/Chilli.CSS" rel="Stylesheet">
</head>
<body>
<span class="dayToGrow"><%=ChiliPeperApplication.getPlant(currentTeracota.getPlantID()).getGrowDays()%></span>
<main>
    <h4>Teracota <%=currentTeracota.getNome()%></h4>
    <p>days to grow: <span class="dayLeft"><%=currentTeracota.getPlantedAt()%>  </span>
    temperature: <span><%=currentTeracota.getActualTemp()%>  </span>
    light: <span><%=currentTeracota.getActuallight()%>  </span></p>
    <hr>
        <form class="container" method="post">
            <h5>Schedule settings</h5>
            <%for (int i = 0;i<crons.size();i++) {%>
            <h6>Schedule <%=i%></h6>
		<div class="container2">
            <input type="hidden" id="cronID" name="cronID" value=<%=crons.get(i).getId()%>>
            <input type="hidden" id="schedlID" name="schedlID"  value=<%=crons.get(i).getSchedule().getId()%>
            <%String start = "start";%>
            <label for=<%=start%>>Start:</label>
            <select name=<%=start%> id=<%=start%>>
                    <%for(int j=0;j<24;j++){%>
                <option <%if(j==crons.get(i).getStartTime()){%>selected="selected"<%}%> value=<%=j%>><%=j%></option>
                    <%}%>
            </select>
            <%String temp = "temp";%>
            <label for=<%=temp%>>Temp:</label><input type="range" value=<%=crons.get(i).getSchedule().getTemperature()%> id=<%=temp%> name=<%=temp%> min="10" max="50" step="0.5"/>
            <%String light = "light";%>
            <label for=<%=light%>>Light:</label><input type="range" value=<%=crons.get(i).getSchedule().getLight()%> id=<%=light%> name=<%=light%> min="0" max="1" step="0.05"/>

            <%String end = "end";%>
                <label for=<%=end%>>end:</label>
                <select name=<%=end%> id=<%=end%>>
                    <%for(int j=0;j<24;j++){%>
                    <option <%if(j==crons.get(i).getEndTime()){%>selected="selected"<%}%> value=<%=j%>><%=j%></option>
                    <%}%>
                </select>
            <%String deleteCronRedirect = "ChangeCronNumber?id="+ request.getParameter("id")+"&&teracota="+currentTeracota.getId()+"&&cronID="+crons.get(i).getId()+"&&method=Delete";%>
            <a href=<%=deleteCronRedirect%>>X</a>
        </div>
        <%}%>
            <%String AddCronRedirect = "ChangeCronNumber?id="+ request.getParameter("id")+"&&teracota="+currentTeracota.getId()+"&&cronID=-1"+"&&method=Post";%>
            <br> <a href=<%=AddCronRedirect%>>ADD</a> <br><br>
            <input type="submit" value="Save">
    </form>
    <hr>
    <%String redirectDelete = "teracotaDetail?id="+ request.getParameter("id")+"&&teracota="+currentTeracota.getId()+"&&method=Delete";%>
    <td><a href=<%=redirectDelete%>>!DELETE TERAKOTA!</a></td>
</main>
<script type="text/javascript" src="../../static/js/teracotaDetailUI.js"></script>
</body>