<%@page import="gui.MainFrame"%>
<%@page import="data.DataHandler"%>
<%@page import ="constants.Constants" %>
<%@page import ="gui.widgets.panels.ScreenPage" %>
<%@ page import ="java.util.List" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<title>Toc</title>
<base target="_self" />
</head>
<style type="text/css">
input[type=submit] {
    width: 8em;  height: 3em;
    font-family:Arial,sans-serif; 
    font-size:14px;
    font-weight:bold; 
    color:black;
}
</style>
<body style="background-color: #C0C0C0">

<p><b></b></p>			

<form name="pagebuttons" style="font-family:Arial,sans-serif; font-size:14px; color:black" method="post" action="<%=request.getContextPath()%>/servlet" target="Hauptframe"/>
<input type="hidden" id="scrpage" name="scrpage"/>	
<%
	List<ScreenPage> availablePages = DataHandler.getInstance().getAvailableScreenPages();
	String scrName;
	int scrPageNumber;
	for(ScreenPage scrPage: availablePages)
	{
		scrName=scrPage.getPageName();
		scrPageNumber=scrPage.getPageNumber();
		System.out.println(scrName+" "+scrPageNumber);
		%>
		<input type="submit" name="page_<%=scrPageNumber%>" value="<%=scrName%>" onclick="setPage('<%=scrPageNumber%>')"/>			
		<%
	}
%>			
<script type="text/javascript">
	function setPage(id)
	{
		document.forms.pagebuttons.elements.scrpage.value =id;
	}
</script>
</form>

</body>
</html>
