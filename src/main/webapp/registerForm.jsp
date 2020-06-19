<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Registration</title>
</head>
<body>
<div align="center">
    <h1>Registration form</h1>
    <form action="<%= request.getContextPath() %>/register" method="post">
        <table style="with: 80%">
            <tr>
                <td>UserName</td>
                <td><input type="text" name="userName"/></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" name="password"/></td>
            </tr>
        </table>
        <input type="submit" value="Submit"/>
    </form>
    <a href="<%= request.getContextPath() %>/loginForm.jsp">Or login</a>
</div>
</body>
</html>