<!DOCTYPE HTML>
<%@page import="net.adamsmolnik.md.Result"%>
<%@page import="java.util.Date"%>
<%@page import="net.adamsmolnik.md.InstanceMetadata"%>
<html>
<%
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0
    httpResponse.setHeader("Cache-Control", "max-age=0");
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
	NO cache!&nbsp;<%= i == null ? "param 'i' == null :( " : "param 'i' is NOT null and i = " + i + " :)"%>
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