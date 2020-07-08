<%--
  Created by IntelliJ IDEA.
  User: sheyk
  Date: 7/8/2020
  Time: 12:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
    <title>Task manager</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark" style="background-color: cornflowerblue">
        <div>
            <a> Task Manager </a>
        </div>

        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/list" class="nav-link">Tasks</a></li>
        </ul>
    </nav>
</header>
<br>

<div class="row">

    <div class="container">
        <h3 class="text-center">List of Goals</h3>
        <hr>
        <div class="container text-left">
            <a href="<%=request.getContextPath()%>newGoal" class="btn btn-success">Add
                New Goal</a>
        </div>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Goal ID</th>
                <th>Name</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach var="goal" items="${goalList}">

                <tr>
                    <td>
                        <c:out value="${goal.goalId}"/>
                    </td>
                    <td>
                        <c:out value="${goal.goalName}"/>
                    </td>
                    <td>
                        <a href="edit?id=<c:out value='${goal.id}' />">Edit</a>
                        <a href="delete?id=<c:out value='${goal.id}' />">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>

</html>
