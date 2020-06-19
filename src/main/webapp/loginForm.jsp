<%--
  Created by IntelliJ IDEA.
  User: sheyk
  Date: 6/18/2020
  Time: 2:15 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>LogScreen</title>
</head>
<body>
<div align="center">
    <h1>Login Form</h1>
    <form action="<%=request.getContextPath()%>/login" method="post">
        <table style="with: 100%">
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
    <div>Forgot your password? Sorry, I cannot send you an e-mail. Just push this button and create new account lol</div>
    <a href="<%= request.getContextPath() %>/registerForm.jsp">The Button</a>
</div>
</body>
</html>