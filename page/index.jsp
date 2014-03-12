<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>RaspPiControl Web Interface</title>
    </head>
    <body style="font-family:Arial,sans-serif; font-size:14px; color:black">
        <form method="post" action="<%=request.getContextPath()%>/page/login.jsp">
            <center>
            <table border="2" width="30%" cellpadding="4" frame="void">
                <thead>
                    <tr>
                        <th colspan="2">piWeb Login</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>User Name</td>
                        <td><input type="text" name="uname" value="" /></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="pass" value="" /></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Login" /></td>
                        <td><input type="reset" value="Reset" /></td>
                    </tr>
                </tbody>
            </table>
            </center>
        </form>
    </body>
</html>