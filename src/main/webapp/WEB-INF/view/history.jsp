<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Search history</title>
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<c:import url="${contextPath}/WEB-INF/view/navibar.jsp"/>
<c:if test="${not empty searchList}">
    <p></p>
    <table class="zui-table">
        <thead>
        <tr>
            <th>Кузов</th>
            <th>Марка</th>
            <th>Модель</th>
            <th>Область</th>
            <th>Город</th>
            <th>Топливо</th>
            <th>Коробка передач</th>
            <th>Цвет</th>
            <th>Период подачи</th>
            <th>Цена от</th>
            <th>Цена до</th>
            <th>Валюта</th>
            <th>Дата</th>
        </thead>
        <tbody>
        <c:forEach items="${searchList}" var="search">
            <tr>
                <td>
                        ${search.bodyStyle.name}
                </td>
                <td>
                        ${search.brand.name}
                </td>
                <td>
                        ${search.model.name}
                </td>
                <td>
                        ${search.state.name}
                </td>
                <td>
                        ${search.city.name}
                </td>
                <td>
                        ${search.type.name}
                </td>
                <td>
                        ${search.gearBox.name}
                </td>
                <td>
                        ${search.color.name}
                </td>
                <td>
                        ${search.top.name}
                </td>
                <td>
                        ${search.priceFrom}
                </td>
                <td>
                        ${search.priceTo}
                </td>
                <td>
                        ${search.currency.name}
                </td>
                <td>
                    ${fn:replace(search.date, 'T', " ")}
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>