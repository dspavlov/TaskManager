<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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

    <div  align="left">
        <strong>Password: </strong>
        <br>
        -At least 8 chars
        <br>
        -Contains at least one digit
        <br>
        -Contains at least one lower alpha char and one upper alpha char
        <br>
        -Contains at least one char within a set of special chars (@#%$^ etc.)
        <br>
        -Does not contain space, tab, etc.
    </div>
</div>
</body>
</html>