<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.card.info.style" var="style"/>
    <fmt:message bundle="${locale}" key="locale.card.info.size" var="size"/>
    <fmt:message bundle="${locale}" key="locale.card.info.price" var="price"/>
    <fmt:message bundle="${locale}" key="locale.card.info.rating" var="rating"/>
    <fmt:message bundle="${locale}" key="locale.action.order" var="order"/>
    <meta charset="UTF-8">
    <title>Заказать</title>
    <link rel="stylesheet" href="../css/style.css">
    <script src="../WEB-INF/js/jquery-3.3.1.js"></script>
    <script src="../WEB-INF/js/main.js"></script>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<div class="order-card-container">
    <div class="order-card">
        <div class="order-card_img-container">
            <img class="blured" src="image?imageId=${sessionScope.tattoo.id}">
            <img class="photo" src="image?imageId=${sessionScope.tattoo.id}">
        </div>
        <div class="order-card_info-container">
            <p class="title">
                ${sessionScope.tattoo.name}
            </p>
            <p class="all-info">
                <span class="info-line">
                    <span class="key">${style}</span>
                    <span class="value">${sessionScope.tattoo.style}</span>
                </span>
                <span class="info-line">
                    <span class="key">${size}</span>
                    <span class="value">${sessionScope.tattoo.size}</span>
                </span>
                <span class="info-line">
                    <span class="key">${price}</span>
                    <span class="value">$${sessionScope.tattoo.price}</span>
                </span>
                <span class="info-line">
                    <span class="key">${rating}</span>
                    <span class="value">${sessionScope.tattoo.rating}</span>
                </span>

            </p>
            <form>
                <div class="rating">
                    <span>☆</span><span>☆</span><span>☆</span><span>☆</span><span>☆</span>
                </div>
                <input type="hidden">
            </form>
            <form action="app">
                <input type="hidden" name="command" value="register-order">
                <input type="hidden" name="tattooId" value="${sessionScope.tattoo.id}">
                <input type="hidden" name="userId" value="${sessionScope.user.id}">
                <input type="submit" value="order"/>
            </form>
        </div>
    </div>
</div>
</body>
</html>