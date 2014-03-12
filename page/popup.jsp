<%
    if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
%>
You are not logged in<br/>
<a target=_parent href="index.jsp">Please Login</a>
<%} else {
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import ="data.types.Value" %>
<%@ page import ="gui.widgets.panels.*" %>
<%@ page import ="java.util.List" %>
<%@ page import ="java.util.ArrayList" %>
<%@ page import ="java.text.*" %>
<%@ page import ="java.util.HashMap" %>
<%@ page import ="java.util.Map" %>
<%@ page import ="javax.swing.JPanel" %>
<%@ page import ="constants.Constants" %>
<%@ page import ="java.util.Iterator" %>
<%@ page import ="java.util.LinkedHashMap" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<title>page1</title>
<base target="_self" />
</head>
<body style="height: 250px; width: 850px; background-color: #C0C0C0">
</body>
</html>
<%
    }
%>
