<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Search Form</title>
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<c:import url="${contextPath}/WEB-INF/view/navibar.jsp"/>
<form:form id="form" name="form" method="post" action="searchForm" modelAttribute="search">
    <table>
        <tr>
            <th>
                <form:select path="bodyStyle">
                    <form:option value="">Тип кузова</form:option>
                    <form:options items="${bodyStyle}" itemValue="value" itemLabel="name"/>
                </form:select>
            </th>
            <th>
                <form:select path="brand">
                    <form:option value="">Марка</form:option>
                    <form:options items="${brand}" itemValue="value" itemLabel="name"/>
                </form:select>
            </th>
            <th>
                <form:select path="model">
                    <form:option value="">Модель</form:option>
                    <form:options items="${model}" itemValue="value" itemLabel="name"/>
                </form:select>
            </th>
            <th>
                <form:select path="state">
                    <form:option value="">Область</form:option>
                    <form:options items="${state}" itemValue="value" itemLabel="name"/>
                </form:select>
            </th>
            <th>
                <form:select path="city">
                    <form:option value="">Город</form:option>
                    <form:options items="${city}" itemValue="value" itemLabel="name"/>
                </form:select>
            </th>
            <th>
                <form:select path="type">
                    <form:option value="">Тип топива</form:option>
                    <form:options items="${type}" itemValue="value" itemLabel="name"/>
                </form:select>
            </th>
            <th>
                <form:select path="gearBox">
                    <form:option value="">Коробка передач</form:option>
                    <form:options items="${gearBox}" itemValue="value" itemLabel="name"/>
                </form:select>
            </th>
            <th>
                <form:select path="color">
                    <form:option value="">Цвет</form:option>
                    <form:options items="${color}" itemValue="value" itemLabel="name"/>
                </form:select>
            </th>
            <th>
                <form:select path="top">
                    <form:option value="">Период подачи</form:option>
                    <form:options items="${top}" itemValue="value" itemLabel="name"/>
                </form:select>
            </th>
            <th>
                <form:input path="priceFrom" cssStyle="width:60px" placeholder="Цена от"
                            onchange="validateNumber(this)"/>
            </th>
            <th>
                <form:input path="priceTo" cssStyle="width:60px" placeholder="Цена до"
                            onchange="validateNumber(this)"/>
            </th>
            <th>
                <form:select path="currency" items="${currency}" itemValue="value" itemLabel="name"/>
            </th>
            <th>
                <form:input path="countPage" cssStyle="width:70px" placeholder="Количество"
                            onchange="validateNumber(this)"/>
            </th>
            <th>
                <form:checkbox path="mailing" value="true" label="Рассылка"/>
            </th>
            <th>
                <c:if test="${not empty searchList}">
                    Ваши недавние поиски<br>
                    <c:forEach items="${searchList}" var="search">
                        ${search.title}<br>
                    </c:forEach>
                </c:if>
            </th>
        </tr>
        <tr>
            <td>
                <form:errors path="bodyStyle" cssClass="error"/>
            </td>
            <td>
                <form:errors path="brand" cssClass="error"/>
            </td>
            <td>
                <form:errors path="model" cssClass="error"/>
            </td>
        </tr>
    </table>
    <script>
        var validNumber = new RegExp(/^\d*\.?\d*$/);
        var lastValid;

        function validateNumber(elem) {
            if (validNumber.test(elem.value)) {
                lastValid = elem.value;
            } else {
                elem.value = lastValid;
            }
        }
    </script>
    <button type="submit" class="button">Поиск</button>
</form:form>


<c:if test="${not empty cars}">
    <p></p>
    <table class="zui-table">
        <thead>
        <tr>
            <th>Фото</th>
            <th>Название</th>
            <th>Город</th>
            <th>Год</th>
            <th>Цена $</th>
            <th>Цена €</th>
            <th>Цена ₴</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cars}" var="car">
            <tr>
                <td>
                    <img src="${car.photoData.seoLinkSX}">
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/search/car?id=${car.autoData.autoId}">${car.title}</a>
                </td>
                <td>
                        ${car.locationCityName}
                </td>
                <td>
                        ${car.autoData.year}
                </td>
                <td>
                        ${car.USD}
                </td>
                <td>
                        ${car.EUR}
                </td>
                <td>
                        ${car.UAH}
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>