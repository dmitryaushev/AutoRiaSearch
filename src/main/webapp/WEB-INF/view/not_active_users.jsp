<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Users</title>
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<c:import url="${contextPath}/WEB-INF/view/navibar.jsp"/>
<form:form method="post" modelAttribute="notActiveUsers" class="form-signin" action="activateUsers">
    <c:choose>
        <c:when test="${not empty users}">
            <table class="zui-table">
                <thead>
                <tr>
                    <th>Full name</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>
                            <form:checkbox path="notActiveUsers" checked="checked" value="${user}"
                                           label="${user.firstName} ${user.lastName}"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <button class="button" type="submit">Activate</button><br>
        </c:when>
        <c:otherwise>
            <p>All users activated</p>
        </c:otherwise>
    </c:choose>
</form:form>
</body>
</html>