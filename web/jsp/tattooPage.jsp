<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.pa" var="style"/>
    <fmt:message bundle="${locale}" key="locale.card.info.style" var="style"/>
    <fmt:message bundle="${locale}" key="locale.card.info.size" var="size"/>
    <fmt:message bundle="${locale}" key="locale.card.info.price" var="price"/>
    <fmt:message bundle="${locale}" key="locale.card.info.rating" var="rating"/>
    <fmt:message bundle="${locale}" key="locale.action.order" var="order"/>
    <fmt:message bundle="${locale}" key="locale.action.cancel" var="cancel"/>
    <fmt:message bundle="${locale}" key="locale.action.delete" var="delete"/>
    <fmt:message bundle="${locale}" key="locale.adminRoom.button.orders" var="orders"/>
    <meta charset="UTF-8">
    <title>Заказать</title>
    <link rel="stylesheet" href="../css/style.css">
    <script src="../js/jquery-3.3.1.js"></script>
    <script src="../js/main.js"></script>

</head>
<body>
<header>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
</header>
<div class="order-card-container">
    <div class="order-card">
        <div class="order-card_img-container">
            <img class="photo" src="image?imageId=${sessionScope.tattoo.id}&table=tattoo">
        </div>
        <div class="order-card_info-container">
            <p class="title">
                ${style}: ${sessionScope.tattoo.style}
            </p>
            <p class="all-info">
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
            <c:choose>
                <c:when test="${empty sessionScope.hasRated}">
                    <form action="app" method="post">
                        <input type="hidden" name="command">
                        <input type="hidden" name="rating">
                        <div class="rating">
                            <span>☆</span><span>☆</span><span>☆</span><span>☆</span><span>☆</span>
                        </div>
                    </form>
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${not empty sessionScope.user.role && sessionScope.user.role != 'USER'}">
                    <form action="app" method="post">
                        <input type="hidden" name="command" value="delete-tattoo-page">
                        <input type="hidden" name="tattooId" value="${sessionScope.tattoo.id}">
                        <input type="submit" value="${delete}">
                    </form>
                </c:when>
                <c:when test="${sessionScope.user.role != 'ADMIN'||sessionScope.user.role !='MODERATOR'}">
                    <c:choose>
                        <c:when test="${empty sessionScope.order}">
                            <form action="app" method="post">
                                <input type="hidden" name="command" value="register-order">
                                <input type="submit" value="${order}">
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form action="app" method="post">
                                <input type="hidden" name="command" value="cancel-order">
                                <input type="submit" value="${cancel}">
                            </form>
                        </c:otherwise>
                    </c:choose>
                    <form action="app">
                        <input type="hidden" name="command" value="order-list">
                        <input class="blue" type="submit" value=${orders}>
                    </form>
                </c:when>
            </c:choose>
        </div>
    </div>
</div>
<footer>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</footer>
</body>
</html>