<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>${user.firstName} ${user.lastName}</title>
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<c:import url="${contextPath}/WEB-INF/view/navibar.jsp"/>
<table class="zui-table">
    <form:form id="form" name="form" method="post" action="changePassword" modelAttribute="user">
    <form:hidden path="id" id="id"/>
    <form:hidden path="email" id="email"/>
    <form:hidden path="password" id="password"/>
    <form:hidden path="firstName" id="firstName"/>
    <form:hidden path="lastName" id="lastName"/>
    <form:hidden path="userRole" id="userRole"/>
    <form:hidden path="userStatus" id="userStatus"/>
    <form:hidden path="searchList" id="searchList"/>
    <h1>Change password</h1>
    <tr>
        <td>
            <p>Password</p>
        </td>
        <td>
            <input type="password" name="oldPassword" required>
                ${message}
        </td>
    </tr>
    <tr>
        <td>
            <p>New password</p>
        </td>
        <td>
            <input type="password" id="newPassword" name="newPassword" required>
        </td>
    </tr>
    <tr>
        <td>
            <p>Confirm password</p>
        </td>
        <td>
            <input type="password" id="confirmNewPassword" name="confirmNewPassword" required>
            <span id='message'></span>
        </td>
    </tr>
</table>
<script>
    var password = document.getElementById("newPassword"),
        confirm_password = document.getElementById("confirmNewPassword");

    function validatePassword() {
        if (password.value != confirm_password.value) {
            confirm_password.setCustomValidity("Passwords Don't Match");
        } else {
            confirm_password.setCustomValidity('');
        }
    }

    password.onchange = validatePassword;
    confirm_password.onkeyup = validatePassword;
</script>
<button type="submit" class="button">Change</button>
</form:form>
</body>
</html>