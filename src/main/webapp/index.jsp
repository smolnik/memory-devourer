<!DOCTYPE HTML>
<%@page import="net.adamsmolnik.md.InstanceMetadata"%>
<html>
<head>
<title>Memory Devourer</title>
<link rel="stylesheet" type="text/css" href="simple.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div id="info" class="border">
		<%=new InstanceMetadata().fetch()%>
	</div>
</body>
</html>