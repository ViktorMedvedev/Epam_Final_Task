<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.action.orderTitle" var="pageTitle"/>
    <fmt:message bundle="${locale}" key="locale.card.info.style" var="style"/>
    <fmt:message bundle="${locale}" key="locale.card.info.size" var="size"/>
    <fmt:message bundle="${locale}" key="locale.action.acceptOrder" var="accept"/>
    <fmt:message bundle="${locale}" key="locale.action.declineOrder" var="decline"/>
    <fmt:message bundle="${locale}" key="locale.adminRoom.button.orders" var="orders"/>
    <fmt:message bundle="${locale}" key="locale.table.userLogin" var="login"/>
    <fmt:message bundle="${locale}" key="locale.table.userEmail" var="email"/>
    <fmt:message bundle="${locale}" key="locale.table.totalPrice" var="total"/>
    <meta charset="UTF-8">
    <title>${pageTitle}</title>
    <link rel="stylesheet" href="../css/style.css">
    <script src="../js/jquery-3.3.1.js"></script>
    <script src="../js/main.js"></script>

</head>
<body>
<header>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
</header>
<c:choose>
    <c:when test="${sessionScope.user.role == 'USER' || empty sessionScope.user}">
        <jsp:forward page="/login"/>
    </c:when>
</c:choose>
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
                    <span class="key">${login}</span>
                    <span class="value">${sessionScope.userFromTable.login}</span>
                </span>
                <span class="info-line">
                    <span class="key">${email}</span>
                    <span class="value">${sessionScope.userFromTable.email}</span>
                </span>
                <span class="info-line">
                    <span class="key">${total}</span>
                    <span class="value">${sessionScope.order.totalPrice}</span>
                </span>
            </p>
            <c:choose>
                <c:when test="${sessionScope.order.status == 'Processing'}">
                    <form action="app" method="post">
                        <input type="hidden" name="command" value="update-order">
                        <input type="hidden" name="status" value="Accepted">
                        <input type="submit" value="${accept}">
                    </form>
                    <form action="app" method="post">
                        <input type="hidden" name="command" value="update-order">
                        <input type="hidden" name="status" value="Declined">
                        <input type="submit" value="${decline}">
                    </form>
                </c:when>
                <c:when test="${sessionScope.order.status == 'Accepted'}">
                    <form action="app" method="post">
                        <input type="hidden" name="command" value="update-order">
                        <input type="hidden" name="status" value="Declined">
                        <input type="submit" value="${decline}">
                    </form>
                </c:when>
                <c:otherwise>
                    <form action="app" method="post">
                        <input type="hidden" name="command" value="update-order">
                        <input type="hidden" name="status" value="Accepted">
                        <input type="submit" value="${accept}">
                    </form>
                </c:otherwise>
            </c:choose>
            <form action="app">
                <input type="hidden" name="command" value="order-list">
                <input class="blue" type="submit" value=${orders}>
            </form>
        </div>
    </div>
</div>
<footer>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</footer>
</body>
</html>