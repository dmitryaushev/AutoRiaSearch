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
            <th>Change status</th>
            <th>Change role</th>
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
                            <div id="line3">
                                <a href="${pageContext.request.contextPath}/admin/deactivate?id=${user.id}"
                                   class="button" role="button" tabindex="0">
                                    <span id="e">Active</span><span id="f">Deactivate</span></a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div id="line4">
                                <a href="${pageContext.request.contextPath}/admin/activate?id=${user.id}"
                                   class="button" role="button" tabindex="0">
                                    <span id="g">Not active</span><span id="h">Activate</span></a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${user.userRole eq 'ROLE_ADMIN'}">
                            <div id="line1">
                                <a href="${pageContext.request.contextPath}/admin/role?id=${user.id}"
                                   class="button" role="button" tabindex="0">
                                    <span id="a">Admin</span><span id="b">User</span></a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div id="line2">
                                <a href="${pageContext.request.contextPath}/admin/role?id=${user.id}"
                                   class="button" role="button" tabindex="0">
                                    <span id="c">User</span><span id="d">Admin</span></a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/admin/showUsers?page=${prev}"
       class="button" role="button" tabindex="0">Prev</a>
    ${current}
    <a href="${pageContext.request.contextPath}/admin/showUsers?page=${next}"
       class="button" role="button" tabindex="0">Next</a>
</c:if>
</body>
</html>