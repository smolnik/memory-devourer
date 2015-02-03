<!DOCTYPE HTML>
<%@page import="net.adamsmolnik.md.Result"%>
<%@page import="java.util.Date"%>
<%@page import="net.adamsmolnik.md.InstanceMetadata"%>
<html>
<%
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0
    httpResponse.setDateHeader("Expires", 0); // Proxies.
%>
<head>
<title>Memory Devourer</title>
<link rel="stylesheet" type="text/css" href="simple.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<%
	    String name = request.getParameter("name");
	%>
	Hello!&nbsp;<%=name == null ? "Anonymous" : name%>
	<br>
	<br> Who am I? Take a look at:
	<br>
	<div id="info" class="border">
		<%=new InstanceMetadata().fetch()%>
	</div>
	<div id="info" class="border">
		<%="date: " + new Date()%>
	</div>
	<br>
	<form action="go" method="POST">

		<div>
			Enter&nbsp;url:&nbsp;<input name="url" type="text" size="80">&nbsp;<input type="submit" value="Send request" size="30">
			<br>
			<%
			    Result result = (Result) request.getAttribute("result");
			    if (result != null) {
			%>
			<br>Status:&nbsp;<b><%=result.getStatus()%></b> for url&nbsp;<%=result.getUrl()%>
			<br>
			<textarea cols="100" rows="5" readonly="readonly"><%=result.getHtml()%></textarea>
			<br>
			<%
			    }
			%>
		</div>
	</form>
</body>
</html>