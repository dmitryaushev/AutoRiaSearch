<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>${user.firstName} ${user.lastName}</title>
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<c:import url="${contextPath}/WEB-INF/view/navibar.jsp"/>
<table class="zui-table">
    <thead>
    <tr>
        <th>Full name</th>
        <th>Email</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>
            ${user.firstName} ${user.lastName}
        </td>
        <td>
            ${user.email}
        </td>
    </tr>
    </tbody>
</table>
<a href="${pageContext.request.contextPath}/user/edit" class="button" role="button" tabindex="0">Edit</a>
<a href="${pageContext.request.contextPath}/user/password" class="button" role="button" tabindex="0">Change password</a>
<br>
${message}
</body>
</html>