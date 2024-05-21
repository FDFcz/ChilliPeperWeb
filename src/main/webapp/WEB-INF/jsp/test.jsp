<%@ page import="com.example.Controlers.ChiliPeperApplication" %>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta charset="utf-8" />
    <title>Debug View</title>
    <link rel="icon" href="../../static/images/chiliLogo.jpg" type="image/icon">
    <link href="../../static/css/Chilli.CSS" rel="Stylesheet">
</head>
<body>
<main>
    ${ChiliPeperApplication.getHTMLTable("teracotaForHarvest")}<hr>
        ${ChiliPeperApplication.getHTMLTable("VIP_Customers")}<hr>

</main>
</body>