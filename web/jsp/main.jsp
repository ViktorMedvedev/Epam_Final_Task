<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Главная</title>
    <link rel="stylesheet" href="../css/style.css">
    <script src="../WEB-INF/js/jquery-3.3.1.js"></script>
    <script src="../WEB-INF/js/main.js"></script>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.card.info.name" var="name"/>
    <fmt:message bundle="${locale}" key="locale.card.info.style" var="style"/>
    <fmt:message bundle="${locale}" key="locale.card.info.size" var="size"/>
    <fmt:message bundle="${locale}" key="locale.card.info.price" var="price"/>
    <fmt:message bundle="${locale}" key="locale.card.info.rating" var="rating"/>
    <fmt:message bundle="${locale}" key="locale.card.button.open" var="open"/>
    <fmt:message bundle="${locale}" key="locale.page.title.main" var="pageTitle"/>
    <title>${pageTitle} | tattoo Parlor</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<div class="tattoo-card-container">
    <c:forEach var = "tattoo" items="${sessionScope.tattoos}" varStatus="status">
    <div class='tatoo-card'>
        <!--<div class="delete-button">X</div>-->
        <div class='img-container'>
                <img src="image?imageId=${tattoo.id}"/>
        </div>
        <div class='info-container'>
            <div class='info'>
                <span class='title'>${tattoo.name}</span>
                <span class='info-line'>${style}: ${tattoo.style}</span>
                <span class='info-line'>${size}: ${tattoo.size}</span>
                <span class='info-line'>${price}: $"${tattoo.price}"</span>
                <span class='info-line'>${rating}: ${tattoo.rating}</span>
            </div>
            <div>
                <form action="app">
                    <input type="hidden" name="command" value="order">
                    <input type="hidden" name="tattooId" value="${tattoo.id}">
                    <input type="hidden" name="${tattoo.imageBytes}">
                    <input type="submit" value="${open}" class="order-button">
                </form>
            </div>
        </div>
    </div>
    </c:forEach>
</div>
</body>
</html>