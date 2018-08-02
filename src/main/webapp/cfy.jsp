<!DOCTYPE HTML>
<%@page import="net.adamsmolnik.md.Result"%>
<%@page import="java.util.Date"%>
<%@page import="net.adamsmolnik.md.InstanceMetadata"%>
<html>
<%
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    httpResponse.setHeader("Cache-Control", "max-age=90");
%>
<head>
<title>Memory Devourer</title>
<link rel="stylesheet" type="text/css" href="simple.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<%
	    String i = request.getParameter("i");
	%>
	Cache-Control max-age=30&nbsp;<%= i == null ? "param 'i' == null :( " : "param 'i' is NOT null and i = " + i + " :)"%>
	<br>
	<br> Who am I? Take a look at:
	<br>
	<div id="info" class="border">
		<%=new InstanceMetadata().fetch()%>
	</div>
	<div id="info" class="border">
		<%="date: " + new Date()%>
	</div>
	<div id="info" class="border">
		<%="client IP: " + request.getRemoteAddr()%>
	</div>
	<img src="valencia.jpg" />
	<br>
</body>
</html>