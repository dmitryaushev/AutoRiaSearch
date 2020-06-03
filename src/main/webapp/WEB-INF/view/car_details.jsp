<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>${car.title}</title>
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<c:import url="${contextPath}/WEB-INF/view/navibar.jsp"/>
<table class="zui-table">
    <thead>
    <tr>
        <th>Фото</th>
        <td><img src="${car.photoData.seoLinkSX}"></td>
    </tr>
    <tr>
        <th>Название</th>
        <td>${car.title}</td>
    </tr>
    <tr>
        <th>Город</th>
        <td>${car.locationCityName}</td>
    </tr>
    <tr>
        <th>Год</th>
        <td>${car.autoData.year}</td>
    </tr>
    <tr>
        <th>Цена $</th>
        <td>${car.USD}</td>
    </tr>
    <tr>
        <th>Цена €</th>
        <td>${car.EUR}</td>
    </tr>
    <tr>
        <th>Цена ₴</th>
        <td>${car.UAH}</td>
    </tr>
    <tr>
        <th>Пробег</th>
        <td>${car.autoData.race}</td>
    </tr>
    <tr>
        <th>Коробка передач</th>
        <td>${car.autoData.gearboxName}</td>
    <tr>
        <th>Тип топлива</th>
        <td>${car.autoData.fuelName}</td>
    </tr>
    <tr>
        <th>Описание</th>
        <td>${car.autoData.description}</td>
    </tr>
    </thead>
</table>
</body>
</html>