<%@page import="data.DataHandler"%>
<style type="text/css">
.auto-style1 {
	font-family: Arial, Helvetica, sans-serif;
	font-size: small;
}
</style>
<style type="text/css">
<!--
.diginMark {
 font-family:Arial,sans-serif; 
 font-size:14px; 
 font-weight: bold;
 color: green;
}
-->
</style>
<style type="text/css">
<!--
.digoutMark {
 font-family:Arial,sans-serif; 
 font-size:14px; 
 font-weight: bold;
 color: red;
}
-->
</style>
<style type="text/css">
<!--
.activeMark {
 font-family:Arial,sans-serif; 
 font-size:14px; 
 font-weight: bold;
 color: cyan;
}
-->
</style>
<style type="text/css">
<!--
.offMark {
 font-family:Arial,sans-serif; 
 font-size:14px; 
 font-weight: bold;
 color: darkgrey;
}
-->
</style>

<%
    if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
%>
<span class="auto-style1"><br>You are not logged in</span><br class="auto-style1"/>
<span class="auto-style1">
<a target=_parent href="index.jsp">Please Login</a></span>
<span class="auto-style1">
<%} else {
%>
<script language="JavaScript"><!--
window.setTimeout("location.reload()",60000);
--></script>
</span>
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
<%@ page import ="com.pi4j.io.gpio.PinMode" %>

<span class="auto-style1">

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
</span>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<span class="auto-style1">
<title>actPage</title>
</span>
<base target="_self" />
</head>

<body style="height: 400px; width: 850px; background-color: #C0C0C0">

<%
Map<JPanel, Value[]> panelsAndValues = new LinkedHashMap<JPanel, Value[]>();
panelsAndValues = data.DataHandler.getInstance().getPanelsAndValues();
Iterator it = panelsAndValues.entrySet().iterator();
Value[] panelValues;
%>
<p style="font-family:Arial,sans-serif; font-size:14px; color:black"><b><%=DataHandler.getInstance().getActScreenPage().getPageName()%>  </b><a href="javascript:location.reload()">(manual reload)</a></p>			
<%

while (it.hasNext()) 
	{
	Map.Entry<JPanel, Value[]> pairs = (Map.Entry) it.next();
	panelValues = (Value[]) pairs.getValue();
%>
<%
			
			String assignedPage;
			int iActPageNumber;
			try{
				assignedPage = ((JPanel)(((Value)panelValues[0]).getAssignedPanel())).getName();
				iActPageNumber=DataHandler.getInstance().getActScreenPage().getPageNumber();
			}catch(Exception e)
			{
				assignedPage="";
				iActPageNumber=0;
			}
			if(panelValues[0] != null && assignedPage.equals(Constants.PAGE+iActPageNumber))
			{

			%>
			  <table border=0 cellpadding=7 style="font-family:Arial,sans-serif; font-size:14px; color:black">
				<colgroup>
			     <col width="200" />
			     <col width="80" />
			     <col width="40" />
			     <col width="200" />
			     <col width="80" />
			     <col width="40" />
			     <col width="50" />
			   </colgroup>  <tr>  
			<%
%>		      
		      <td><b><%=((Value)panelValues[0]).getLongText()%></b></td>
<%
			 if(((Value)panelValues[0]).isReadOnly())
			 {

				if(((Value)panelValues[0]).getValue() instanceof Boolean)
				{
			    	  if((boolean)(((Value)panelValues[0]).getValue()))
			    	  {
			    		  if(((Value)panelValues[0]).getGpioPin()!=null)
			    		  {
				    		  if(((Value)panelValues[0]).getGpioPin().getMode()==PinMode.DIGITAL_OUTPUT)
				    		  {
		%>
				    			<td align="center" bgcolor="lightgrey"> <span class="digoutMark">&#9670;</span></td>      
		<% 
				    		  }
				    		  if(((Value)panelValues[0]).getGpioPin().getMode()==PinMode.DIGITAL_INPUT)
				    		  {
		%>
				    			<td align="center" bgcolor="lightgrey"> <span class="diginMark">&#9670;</span></td>      
		<% 
	
				    		  }
			    		  }else
			    		  {
		%>
				    			<td align="center" bgcolor="lightgrey"> <span class="activeMark">&#9670;</span></td>      
		<% 
			    			  
			    		  }
			    	  }else
			    	  {
	%>
			    			<td align="center" bgcolor="lightgrey"><span class="offMark">&#9671;</span></td>      
	<%  
			    	  }
				}
				else
				{
%>		      
		      		<td align="right" bgcolor="lightgrey"><%=((Value)panelValues[0]).getValue()%></td>
<%
				}
   			 }
			 else
			 {
				if(data.DataHandler.getInstance().getValueToChange() == ((Value)panelValues[0]) )
				{//wert soll geändert werden:
			    	if(((Value)panelValues[0]).getValue() instanceof Integer )
			    	{
			    		int iMin = (int)panelValues[0].getPlaus()[0];
			    		int iMax = (int)panelValues[0].getPlaus()[1];
%>
						<form style="font-family:Arial,sans-serif; font-size:14px; color:black" method="POST" action="<%=request.getContextPath()%>/servlet">
						<td width="170" align="right" bgcolor="white" >
						 	<select id=<%=((Value)panelValues[0]).getVarName()+"int"%> name=int style="width: 50px" onchange="runi('<%=((Value)panelValues[0]).getVarName()%>')">
<%			
							int iVal=(int)((Value)panelValues[0]).getValue();
							for(int i=iMin; i<=iMax; i++)
							{
								if(iVal== i)
								{
%>			 
							 		<option selected><%=i%></option>
<%									
								}
								else
								{
%>			 
							 		<option><%=i%></option>
<%
								}
							} 
%>
							</select>
							<input type="hidden" id=<%=((Value)panelValues[0]).getVarName()%> name=<%=((Value)panelValues[0]).getVarName()%>>				
							<input type="submit" onClick = runi('<%=((Value)panelValues[0]).getVarName()%>') value="OK" />
							<input type="submit" onClick="doCanceli('<%=((Value)panelValues[0]).getVarName()%>')" value="Cancel" />



							<script type="text/javascript">
							function runi(id) {
								document.getElementById(id).value=document.getElementById(id+"int").value;	
							}
							function doCanceli(id) 
							{
								document.getElementById(id).value=<%=iVal%>;
							}
							</script>
							
							</td>
						</form>
<%
			    	}
			    	if(((Value)panelValues[0]).getValue() instanceof Boolean)
			    	{
						boolean bVal=(boolean)((Value)panelValues[0]).getValue();
			    		
			%>
						<form style="font-family:Arial,sans-serif; font-size:14px; color:black" method="POST" action="<%=request.getContextPath()%>/servlet">
							<td width="170" align="right" bgcolor="white" >
						 	<select id=<%=((Value)panelValues[0]).getVarName()+"bool"%> name=bool style="width: 50px">
<%							
							if(bVal)
							{
								%>
							 <option selected>true</option>
							 <option>false</option>
								<% 
								
							}else
							{
								%>
							 <option>true</option>
							 <option selected>false</option>
								<% 
							}
%>							 
							 </select>
							<input type="hidden" id=<%=((Value)panelValues[0]).getVarName()%> name=<%=((Value)panelValues[0]).getVarName()%>>				
							<input type="submit" onClick = runb('<%=((Value)panelValues[0]).getVarName()%>') value="OK" />
							<input type="submit" onClick="doCancelb('<%=((Value)panelValues[0]).getVarName()%>')" value="Cancel" />
							<script type="text/javascript">
							function runb(id) {
								document.getElementById(id).value=document.getElementById(id+"bool").value;	
							}
							function doCancelb(id) 
							{
								document.getElementById(id).value=<%=bVal%>;
							}
							</script>
							</td>
						</form>
			<%			    		
			    	}
			    	if(((Value)panelValues[0]).getValue() instanceof Double)
			    	{
			    		double dMin = ((Value)panelValues[0]).getPlaus()[0];
			    		double dMax = ((Value)panelValues[0]).getPlaus()[1];
			    		DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
			    		dfs.setDecimalSeparator('.');
			    		DecimalFormat f = new DecimalFormat("#0.0",dfs);
						double dVal=(double)((Value)panelValues[0]).getValue();
			    		System.out.println(""+dVal);
			%>
						<form style="font-family:Arial,sans-serif; font-size:14px; color:black" method="POST" action="<%=request.getContextPath()%>/servlet">
							<td width="170" align="right" bgcolor="white" >
							 <select id=<%=((Value)panelValues[0]).getVarName()+"double"%> name=double style="width: 50px">
			<%			

						for(double d=dMin; d<=dMax; d+=0.1)
						{
							if((""+dVal).equals(f.format(d)))
							{
			%>			 
								 <option selected><%=f.format(d)%></option>
			<%									
							}
							else
							{
			%>			 
								 <option><%=f.format(d)%></option>
			<%
							}
						} 
			%>
							</select>
							<input type="hidden" id=<%=((Value)panelValues[0]).getVarName()%> name=<%=((Value)panelValues[0]).getVarName()%>>				
							<input type="submit" onClick = rund('<%=((Value)panelValues[0]).getVarName()%>') value="OK" />
							<input type="submit" onClick="doCanceld('<%=((Value)panelValues[0]).getVarName()%>')" value="Cancel" />



							<script type="text/javascript">
							function rund(id) {
								document.getElementById(id).value=document.getElementById(id+"double").value;	
							}
							function doCanceld(id) 
							{
								document.getElementById(id).value=<%=dVal%>;
							}
							</script>
						</form>
						</td>
			<%		
			    	}
			    	if(((Value)panelValues[0]).getValue() instanceof String)
			    	{
					 	int h,m,s;
					 	try{
						 	String[] t =((String)((Value)panelValues[0]).getValue()).split(":");
						 	h=Integer.parseInt(t[0]);
						 	m=Integer.parseInt(t[1]);
						 	s=Integer.parseInt(t[2]);
						 }catch(Exception e){
					 		h=999;
					 		m=999;
					 		s=999;
					 	}

			    		%>
						
						<form style="font-family:Arial,sans-serif; font-size:14px; color:black" method="POST" action="<%=request.getContextPath()%>/servlet">
						<td width="350" align="right" bgcolor="white" >
							 <select id=<%=((Value)panelValues[0]).getVarName()+"hour"%> name=hour style="width: 50px" onchange="runx('<%=((Value)panelValues[0]).getVarName()%>')">
							 <%
							 	String stemp;
							 	for(int i=0; i<24; i++)
							 	{
							 		if(i<10)
							 			stemp="0"+i;
							 		else
							 			stemp=""+i;
							 		if(i==h)
							 		{
								 		%>
								 		<option selected><%=stemp%></option>
								 		<%
							 		}
							 		else
							 		{
							 		%>
							 		<option><%=stemp%></option>
							 		<%
							 		}
							 	}
							 %>
							 </select>
							 
							 <select id=<%=((Value)panelValues[0]).getVarName()+"minute"%> name=minute style="width: 50px" onchange="runx('<%=((Value)panelValues[0]).getVarName()%>')">
							 <%
							 	for(int i=0; i<60; i++)
							 	{
							 		if(i<10)
							 			stemp="0"+i;
							 		else
							 			stemp=""+i;
							 		if(i==m)
							 		{
								 		%>
								 		<option selected><%=stemp%></option>
								 		<%
							 		}
							 		else
							 		{
							 		%>
							 		<option><%=stemp%></option>
							 		<%
							 		}
							 	}
							 %>
							</select>
							
							 <select id=<%=((Value)panelValues[0]).getVarName()+"second"%> name=second style="width: 50px" onchange="runx('<%=((Value)panelValues[0]).getVarName()%>')">
							 <%
							 	for(int i=0; i<60; i++)
							 	{
							 		if(i<10)
							 			stemp="0"+i;
							 		else
							 			stemp=""+i;
							 		if(i==s)
							 		{
								 		%>
								 		<option selected><%=stemp%></option>
								 		<%
							 		}
							 		else
							 		{
							 		%>
							 		<option><%=stemp%></option>
							 		<%
							 		}
							 	}
							 %>
							</select>

							<input type="hidden" id=<%=((Value)panelValues[0]).getVarName()%> name=<%=((Value)panelValues[0]).getVarName()%>>				
							<input type="submit" onClick = runx('<%=((Value)panelValues[0]).getVarName()%>') value="OK" />
							<input type="submit" onClick="doCancel('<%=((Value)panelValues[0]).getVarName()%>')" value="Cancel" />

							<script type="text/javascript">
							function runx(id) {
			     				document.getElementById(id).value = document.getElementById(id+"hour").value+":"+document.getElementById(id+"minute").value+":"+document.getElementById(id+"second").value;
			     				}
			 				function doCancel(id) {
<%			 					
			 					String sh=""+h;
			 					String sm=""+m;
			 					String ss=""+s;
			 					if(h<10)
			 						sh="0"+h;
			 					if(m<10)
			 						sm="0"+m;
			 					if(s<10)
			 						ss="0"+s;
%>
			 					document.getElementById(id).value = "<%=sh%>:<%=sm%>:<%=ss%>";
			     				}
							</script>
			<%
						}
			%>
						</form>
						</td>
			<%			    		
				}
				else
				{
%>							
					<form id="change_<%=((Value)panelValues[0]).getVarName()%>" style="font-family:Arial,sans-serif; font-size:14px; color:black" method="POST" action="<%=request.getContextPath()%>/servlet">
			      	<td align="right" bgcolor="white" onClick=runChange("change_<%=((Value)panelValues[0]).getVarName()%>")><%=((Value)panelValues[0]).getValue()%></td>
					<input name=<%=((Value)panelValues[0]).getVarName()%> type=hidden value=<%=((Value)panelValues[0]).getVarName()%>>
			      	</form>
					
					<script type="text/javascript">
					function runChange(id) { 
	     				document.getElementById(id).submit(); 
	 				}
					</script>
<%
			 	}
			 }
%>
		      <td><b><%=((Value)panelValues[0]).getUnitText()%></b></td>
<%
				if(panelValues[1] == null)
				{
%>
				  <td> </td>
				  <td align="right"> </td>
				  <td> </td>
<%
				}
				else
				{
%>
			      <td><b><%=((Value)panelValues[1]).getLongText()%></b></td>
<%
			 	if(((Value)panelValues[1]).isReadOnly())
			 	{
%>		      
			      <td align="right" bgcolor="lightgrey"><%=((Value)panelValues[1]).getValue()%></td>
<%
   			 	}
			 	else
			 	{

					if(data.DataHandler.getInstance().getValueToChange() == ((Value)panelValues[1]) )
					{//wert soll geändert werden:
				    	if(((Value)panelValues[1]).getValue() instanceof Integer )
				    	{
				    		int iMin = (int)panelValues[1].getPlaus()[0];
				    		int iMax = (int)panelValues[1].getPlaus()[1];
	%>
							<form style="font-family:Arial,sans-serif; font-size:14px; color:black" method="POST" action="<%=request.getContextPath()%>/servlet">
							<td width="170" align="right" bgcolor="white" >
							 	<select id=<%=((Value)panelValues[1]).getVarName()+"int"%> name=int style="width: 50px" onchange="runi('<%=((Value)panelValues[1]).getVarName()%>')">
	<%			
								int iVal=(int)((Value)panelValues[1]).getValue();
								for(int i=iMin; i<=iMax; i++)
								{
									if(iVal== i)
									{
	%>			 
								 		<option selected><%=i%></option>
	<%									
									}
									else
									{
	%>			 
								 		<option><%=i%></option>
	<%
									}
								} 
	%>
								</select>
								<input type="hidden" id=<%=((Value)panelValues[1]).getVarName()%> name=<%=((Value)panelValues[1]).getVarName()%>>				
								<input type="submit" onClick = runi2('<%=((Value)panelValues[1]).getVarName()%>') value="OK" />
								<input type="submit" onClick="doCanceli2('<%=((Value)panelValues[1]).getVarName()%>')" value="Cancel" />

								<script type="text/javascript">
								function runi2(id) {
									document.getElementById(id).value=document.getElementById(id+"int").value;	
								}
								function doCanceli2(id) 
								{
									document.getElementById(id).value=<%=iVal%>;
								}
								</script>
								</td>
							</form>
	<%
				    	}
				    	if(((Value)panelValues[1]).getValue() instanceof Boolean)
				    	{
							boolean bVal=(boolean)((Value)panelValues[1]).getValue();
			    		
						%>
									<form style="font-family:Arial,sans-serif; font-size:14px; color:black" method="POST" action="<%=request.getContextPath()%>/servlet">
										<td width="170" align="right" bgcolor="white" >
									 	<select id=<%=((Value)panelValues[1]).getVarName()+"bool"%> name=bool style="width: 50px">
			<%							
										if(bVal)
										{
											%>
										 <option selected>true</option>
										 <option>false</option>
											<% 
											
										}else
										{
											%>
										 <option>true</option>
										 <option selected>false</option>
											<% 
										}
			%>							 
										 </select>
										<input type="hidden" id=<%=((Value)panelValues[1]).getVarName()%> name=<%=((Value)panelValues[1]).getVarName()%>>				
										<input type="submit" onClick = runb2('<%=((Value)panelValues[1]).getVarName()%>') value="OK" />
										<input type="submit" onClick="doCancelb2('<%=((Value)panelValues[1]).getVarName()%>')" value="Cancel" />
										<script type="text/javascript">
										function runb2(id) {
											document.getElementById(id).value=document.getElementById(id+"bool").value;	
										}
										function doCancelb2(id) 
										{
											document.getElementById(id).value=<%=bVal%>;
										}
										</script>
										</td>
									</form>
				<%			    		
				    	}
				    	if(((Value)panelValues[1]).getValue() instanceof Double)
				    	{
				    		double dMin = ((Value)panelValues[1]).getPlaus()[0];
				    		double dMax = ((Value)panelValues[1]).getPlaus()[1];
				    		DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
				    		dfs.setDecimalSeparator('.');
				    		DecimalFormat f = new DecimalFormat("#0.0",dfs);
							double dVal=(double)((Value)panelValues[1]).getValue();
				    		System.out.println(""+dVal);
				%>
							<form style="font-family:Arial,sans-serif; font-size:14px; color:black" method="POST" action="<%=request.getContextPath()%>/servlet">
								<td width="170" align="right" bgcolor="white" >
								 <select id=<%=((Value)panelValues[1]).getVarName()+"double"%> name=double style="width: 50px">
				<%			

							for(double d=dMin; d<=dMax; d+=0.1)
							{
								if((""+dVal).equals(f.format(d)))
								{
				%>			 
									 <option selected><%=f.format(d)%></option>
				<%									
								}
								else
								{
				%>			 
									 <option><%=f.format(d)%></option>
				<%
								}
							} 
				%>
								</select>
								<input type="hidden" id=<%=((Value)panelValues[1]).getVarName()%> name=<%=((Value)panelValues[1]).getVarName()%>>				
								<input type="submit" onClick = rund2('<%=((Value)panelValues[1]).getVarName()%>') value="OK" />
								<input type="submit" onClick="doCanceld2('<%=((Value)panelValues[1]).getVarName()%>')" value="Cancel" />



								<script type="text/javascript">
								function rund2(id) {
									document.getElementById(id).value=document.getElementById(id+"double").value;	
								}
								function doCanceld2(id) 
								{
									document.getElementById(id).value=<%=dVal%>;
								}
								</script>
							</form>
							</td>
				<%		
				    	}
				    	if(((Value)panelValues[1]).getValue() instanceof String)
				    	{
						 	int h,m,s;
						 	try{
							 	String[] t =((String)((Value)panelValues[1]).getValue()).split(":");
							 	h=Integer.parseInt(t[0]);
							 	m=Integer.parseInt(t[1]);
							 	s=Integer.parseInt(t[2]);
							 }catch(Exception e){
						 		h=999;
						 		m=999;
						 		s=999;
						 	}

				%>
							<form style="font-family:Arial,sans-serif; font-size:14px; color:black" method="POST" action="<%=request.getContextPath()%>/servlet">
						<td width="350" align="right" bgcolor="white" >
							 <select id=<%=((Value)panelValues[1]).getVarName()+"hour"%> name=hour style="width: 50px" onchange="runx2('<%=((Value)panelValues[1]).getVarName()%>')">
							 <%
							 	String stemp;
							 	for(int i=0; i<24; i++)
							 	{
							 		if(i<10)
							 			stemp="0"+i;
							 		else
							 			stemp=""+i;
							 		if(i==h)
							 		{
								 		%>
								 		<option selected><%=stemp%></option>
								 		<%
							 		}
							 		else
							 		{
							 		%>
							 		<option><%=stemp%></option>
							 		<%
							 		}
							 	}
							 %>
							 </select>
							 
							 <select id=<%=((Value)panelValues[1]).getVarName()+"minute"%> name=minute style="width: 50px" onchange="runx2('<%=((Value)panelValues[1]).getVarName()%>')">
							 <%
							 	for(int i=0; i<60; i++)
							 	{
							 		if(i<10)
							 			stemp="0"+i;
							 		else
							 			stemp=""+i;
							 		if(i==m)
							 		{
								 		%>
								 		<option selected><%=stemp%></option>
								 		<%
							 		}
							 		else
							 		{
							 		%>
							 		<option><%=stemp%></option>
							 		<%
							 		}
							 	}
							 %>
							</select>
							
							 <select id=<%=((Value)panelValues[1]).getVarName()+"second"%> name=second style="width: 50px" onchange="runx2('<%=((Value)panelValues[1]).getVarName()%>')">
							 <%
							 	for(int i=0; i<60; i++)
							 	{
							 		if(i<10)
							 			stemp="0"+i;
							 		else
							 			stemp=""+i;
							 		if(i==s)
							 		{
								 		%>
								 		<option selected><%=stemp%></option>
								 		<%
							 		}
							 		else
							 		{
							 		%>
							 		<option><%=stemp%></option>
							 		<%
							 		}
							 	}
							 %>
							</select>

							<input type="hidden" id=<%=((Value)panelValues[1]).getVarName()%> name=<%=((Value)panelValues[1]).getVarName()%>>				
							<input type="submit" onClick = runx2('<%=((Value)panelValues[1]).getVarName()%>') value="OK" />
							<input type="submit" onClick="doCancel2('<%=((Value)panelValues[1]).getVarName()%>')" value="Cancel" />

							<script type="text/javascript">
							function runx2(id) {
			     				document.getElementById(id).value = document.getElementById(id+"hour").value+":"+document.getElementById(id+"minute").value+":"+document.getElementById(id+"second").value;
			     				}
			 				function doCancel2(id) {
<%			 					
			 					String sh=""+h;
			 					String sm=""+m;
			 					String ss=""+s;
			 					if(h<10)
			 						sh="0"+h;
			 					if(m<10)
			 						sm="0"+m;
			 					if(s<10)
			 						ss="0"+s;
%>
			 					document.getElementById(id).value = "<%=sh%>:<%=sm%>:<%=ss%>";
			     				}
							</script>
			<%
						}
			%>
						</form>
						</td>
				<%			    		
				}
					else
				
				
				{
%>							

				<form id="change_<%=((Value)panelValues[1]).getVarName()%>" style="font-family:Arial,sans-serif; font-size:14px; color:black" method="POST" action="<%=request.getContextPath()%>/servlet">
		      	<td align="right" bgcolor="white" onClick=runChangeVal2("change_<%=((Value)panelValues[1]).getVarName()%>")><%=((Value)panelValues[1]).getValue()%></td>
				<input name=<%=((Value)panelValues[1]).getVarName()%> type=hidden value=<%=((Value)panelValues[1]).getVarName()%>>
		      	</form>
				<script type="text/javascript">
 				function runChangeVal2(id) {
     				document.getElementById(id).submit();
 				}
				</script>
<%
				}
			 	}
%>
			      <td><b><%=((Value)panelValues[1]).getUnitText()%></b></td>
<%
				}
%>

<%
				if(panelValues[2] == null)
				{
%>
				  <td> </td>
<%
				}
				else
				{
			      if(((Value)panelValues[2]).getValue() instanceof Boolean)
			      {
			    	  if((boolean)(((Value)panelValues[2]).getValue()))
			    	  {
%>
			    			<td align="center" bgcolor="lightgrey"> <span class="activeMark">&#9670;</span></td>      
<% 
			    	  }else
			    	  {
%>
			    			<td align="center" bgcolor="lightgrey"><span class="offMark">&#9671;</span></td>      
<%  
			    	  }
			      }else
			      {
%>
			      <td bgcolor="lightgrey"><%=((Value)panelValues[2]).getValue()%></td>
<%
			      }
				}
%>				
</table>
<%
		   }
	}
%>
<div id="Ebene1" style="position: absolute; width: 901px; height: 36px; z-index: 1; left: 13px; top: 420px">
<hr>
<% 
	String actOpMode=DataHandler.getInstance().getOpMode();
%>	
	  <table border=0 cellpadding=3 style="font-family:Arial,sans-serif; font-size:14px; color:black">
		<colgroup>
	     <col width="200" />
	     <col width="80" />
	     <col width="40" />
	   </colgroup>  <tr>  
      <td><b>Betriebsmodus</b></td>
<%
	if(DataHandler.getInstance().isOpModeChangeRequested()) //betriesmodus soll geändert werden
	{
		String sActOpMode=DataHandler.getInstance().getOpMode();
%>
		<form style="font-family:Arial,sans-serif; font-size:14px; color:black" method="POST" action="<%=request.getContextPath()%>/servlet">
			<td width="240" align="right" bgcolor="white" >
		 	<select id="operationmode_selector" name="operationmode_selector" style="width: 100px">
<%							
			if(sActOpMode.equals(Constants.OPMODE_MANUAL))
			{
				%>
			 <option selected>Manual</option>
			 <option>Automatic</option>
				<% 
				
			}else 
			{
				%>
			 <option>Manual</option>
			 <option selected>Automatic</option>
				<% 
			}
%>							 
			 </select>
			<input type="hidden" id="operationmode" name="operationmode"/>				
			<input type="submit" onClick = runom() value="OK" />
			<input type="submit" onClick="doCancelom()" value="Cancel" />
			<script type="text/javascript">
			function runom() {
				document.getElementById("operationmode").value=document.getElementById("operationmode_selector").value;	
			}
			function doCancelom() 
			{
				document.getElementById("operationmode").value=sActOpMode;
			}
			</script>
			</td>
		</form>

<%
	}
	else//nur anzeigen
	{		
%>
		<form id="change_opmode" style="font-family:Arial,sans-serif; font-size:14px; color:black" method="POST" action="<%=request.getContextPath()%>/servlet">
      	<td align="center" bgcolor="white" onClick=runChangeOpMode()><%=DataHandler.getInstance().getOpMode()%></td>
		<input name="opmode" type=hidden value="opmode">
      	</form>
		</tr>
		</table>
				
		<script type="text/javascript">
		function runChangeOpMode() { 
				document.getElementById("change_opmode").submit();
			}
		</script>

<% 
	}
%>		
		
</div>
</body>
</html>
<%
}
%>
