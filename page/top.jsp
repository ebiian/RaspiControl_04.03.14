<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<title>Top</title>
<base target="_self" />
<style type="text/css">
.auto-style2 {
	font-family: Arial, Helvetica, sans-serif;
	text-align: left;
}
.auto-style3 {
	font-size: small;
}
</style>
</head>

<body style="background-color: #C0C0C0" >

<h3 class="auto-style2" style="height: 43px"><strong>Welcome '<%=session.getAttribute("userid")%>' to RaspPiControl WebInterface! </strong>&nbsp;<a target=_parent href='<%=request.getContextPath()%>/page/logout.jsp'><span class="auto-style3">Log out</span></a></h3>

</body>

</html>
