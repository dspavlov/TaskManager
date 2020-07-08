<%--
  Created by IntelliJ IDEA.
  User: sheyk
  Date: 7/7/2020
  Time: 9:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
    <title>Task Management Application</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark" style="background-color: cornflowerblue">
        <div>
            <a> Task manager </a>
        </div>

        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/list" class="nav-link">Tasks</a></li>
        </ul>
    </nav>
</header>
<br>
<div class="container col-md-5">
    <div class="card">
        <div class="card-body">
                <c:if test="${goal == null}">
                <form action="newGoal" method="post">
                    </c:if>

                    <caption>
                        <h2>
                            <c:if test="${goal != null}">
                                Edit Goal
                            </c:if>
                            <c:if test="${goal == null}">
                                Add New Goal
                            </c:if>
                        </h2>
                    </caption>

                    <c:if test="${goal != null}">
                        <input type="hidden" name="id" value="<c:out value='${goal.id}' />"/>
                    </c:if>

                    <fieldset class="form-group">
                        <label>Goal</label> <input type="text" value="<c:out value='${goal.name}' />"
                                                   class="form-control" name="name" required="required">
                    </fieldset>

                    <button type="submit" class="btn btn-success">Save</button>
                </form>
        </div>
    </div>
</div>
</body>

</html>
