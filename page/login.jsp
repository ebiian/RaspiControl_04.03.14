<%@ page import ="java.util.List" %>
<%@ page import ="java.util.ArrayList" %>
<%
    String userid = request.getParameter("uname");   
    String pwd = request.getParameter("pass");
    List<String[]> users = gui.MainFrame.getInstance().getUsers(); 
    boolean bFound=false;
    for(String[] user: users)
	{
        if (userid.equals(user[0]) && pwd.equals(user[1])) {
            session.setAttribute("userid", userid);
            response.sendRedirect(request.getContextPath()+"/page/piweb.jsp");
            bFound=true;
            break;
        }
	}
   	if(!bFound)
   	{
           out.println("Invalid password <a href='"+request.getContextPath()+"/page/index.jsp'>try again</a>");
   	}
%>