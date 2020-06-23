<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Find user</title>
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<c:import url="${contextPath}/WEB-INF/view/navibar.jsp"/>
<form method="get" action="find">
    <label>
        <span class="small">User email</span>
        <input name="email">
        <button type="submit" class="button">Find</button>
    </label>
</form>
<c:if test="${not empty error}">
    ${error}
</c:if>
</body>
</html>