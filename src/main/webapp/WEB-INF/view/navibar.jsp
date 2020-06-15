<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div class="navbar">
    <a href="${pageContext.request.contextPath}/">Home</a>
    <security:authorize access="hasRole('ROLE_ADMIN')">
        <div class="dropdown">
            <button class="dropbtn">Users
                <i></i>
            </button>
            <div class="dropdown-content">
                <a href="${pageContext.request.contextPath}/admin/showUsers">Show all</a>
                <a href="${pageContext.request.contextPath}/admin/notActiveUsers">Not active</a>
                <a href="${pageContext.request.contextPath}/admin/findUser">Find</a>
            </div>
        </div>
    </security:authorize>
    <div class="dropdown">
        <button class="dropbtn">Search
            <i></i>
        </button>
        <div class="dropdown-content">
            <a href="${pageContext.request.contextPath}/search/searchForm">Search Form</a>
            <security:authorize access="hasRole('ROLE_ADMIN')">
                <a href="${pageContext.request.contextPath}/admin/time">Set Time</a>
            </security:authorize>
        </div>
    </div>
    <div class="dropdown" style="float: right">
        <button class="dropbtn"><security:authentication property="principal.name"/>
            <i></i>
        </button>
        <div class="dropdown-content">
            <a href="${pageContext.request.contextPath}/user/get?id=
<security:authentication property="principal.id"/>">Details</a>
            <a href="${pageContext.request.contextPath}/search/history">Search history</a>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </div>
    </div>
</div>
