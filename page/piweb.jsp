<%
    if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
%>
You are not logged in<br/>
<a target=_parent href="index.jsp">Please Login</a>
<%} else {
%>

<html>

<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
<title>RaspPiControl WebInterface</title>
</head>

<frameset rows="48,*" border="1" frameborder="1" framespacing="2">
	<frame name="Banner" noresize="noresize" scrolling="no" src="top.jsp" marginheight="10" marginwidth="10" target="_self">
	<frameset cols="126,*">
		<frame name="Inhalt" scrolling="no" src="toc.jsp" noresize="noresize" target="_self">
		<frame name="Hauptframe" scrolling="auto" src="center.jsp" noresize="noresize" target="_self">
	</frameset>
	<noframes>
	<body>

	<p>Diese Seite verwendet Frames. Frames werden von Ihrem Browser aber nicht 
	unterstützt.</p>

	</body>
	</noframes>
</frameset>

</html>
<%
    }
%>
