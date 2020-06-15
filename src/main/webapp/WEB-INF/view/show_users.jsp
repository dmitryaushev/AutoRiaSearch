<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Users</title>
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<c:import url="${contextPath}/WEB-INF/view/navibar.jsp"/>
<c:if test="${not empty users}">
    <table class="zui-table">
        <thead>
        <tr>
            <th>Full name</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>
                        ${user.firstName} ${user.lastName}
                </td>
                <td>
                    <c:choose>
                        <c:when test="${user.userStatus eq 'ACTIVE'}">
                            <a href="${pageContext.request.contextPath}/admin/deactivate?id=${user.id}"
                               class="button" role="button" tabindex="0">Deactivate</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/admin/activate?id=${user.id}"
                               class="button" role="button" tabindex="0">Activate</a>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>