<%--
  Created by IntelliJ IDEA.
  User: sheyk
  Date: 6/18/2020
  Time: 2:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Logged in</title>
</head>
<body>
<div align="center">
    <h1>Oh, hi there</h1>
    <a href="<%= request.getContextPath() %>/list">Continue</a>
    <div align="center">
        <a href="<%= request.getContextPath() %>/logout">Logout</a>
    </div>
</div>
</body>
</html>