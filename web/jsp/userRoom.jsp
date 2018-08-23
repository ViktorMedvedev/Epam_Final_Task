<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Личный кабинет</title>
    <link rel="stylesheet" href="../css/style.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.page.title.auth" var="pageTitle"/>
    <fmt:message bundle="${locale}" key="locale.user.label.email" var="emailLabel"/>
    <fmt:message bundle="${locale}" key="locale.user.label.login" var="loginLabel"/>
    <fmt:message bundle="${locale}" key="locale.user.label.discount" var="discountLabel"/>
    <fmt:message bundle="${locale}" key="locale.user.label.password" var="passwordLabel"/>

    <fmt:message bundle="${locale}" key="locale.userRoom.button.changePassword" var="changePassword"/>
    <fmt:message bundle="${locale}" key="locale.userRoom.button.logout" var="logout"/>
    <fmt:message bundle="${locale}" key="locale.userRoom.button.myOrders" var="myOrders"/>
    <fmt:message bundle="${locale}" key="locale.adminRoom.button.orders" var="orders"/>
    <fmt:message bundle="${locale}" key="locale.adminRoom.button.users" var="users"/>
    <fmt:message bundle="${locale}" key="locale.adminRoom.button.offered" var="offered"/>
    <fmt:message bundle="${locale}" key="locale.userRoom.button.offer" var="suggestPhoto"/>
    <fmt:message bundle="${locale}" key="locale.userRoom.button.delete" var="delete"/>
    <fmt:message bundle="${locale}" key="locale.action.uploadPhoto" var="upload"/>
    <fmt:message bundle="${locale}" key="locale.page.toUserRoom" var="userRoom"/>
</head>
<body>
<header>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
</header>
<c:choose>
    <c:when test="${empty sessionScope.user}">
        <jsp:forward page="/login"/>
    </c:when>
</c:choose>
<div class="user-cabinet-container">
    <div class="user-card-container">
        <div class="user-card">
            <div class="username">
                ${sessionScope.user.login}
            </div>
            <div class="info">
                <p>
                    <span class="key">${emailLabel}</span>
                    <span class="value">${sessionScope.user.email}</span>
                </p>
                <p>
                    <span class="key">${discountLabel}</span>
                    <span class="value">${sessionScope.user.discountPct}</span>
                </p>
            </div>
            <div class="button-container">
                <form action="app" method="post">
                    <input type="hidden" name="command" value="logout">
                    <input class="gray" type="submit" value=${logout}>
                </form>
                <c:choose>
                    <c:when test="${sessionScope.user.role != 'USER'}">
                        <form action="uploadPhotoPage">
                            <input class="blue" type="submit" value=${upload}>
                        </form>
                    </c:when>
                </c:choose>
            </div>
            <div class="button-container">
                <form action="changePassword">
                    <input class="blue" type="submit" value=${changePassword}>
                </form>
                <form action="app">
                    <input type="hidden" name="command" value="order-list">
                    <input class="blue" type="submit" value=${orders}>
                </form>
                <c:choose>
                    <c:when test="${sessionScope.user.role == 'USER'}">
                        <form action="uploadPhotoPage">
                            <input class="blue" type="submit" value=${suggestPhoto}>
                        </form>
                        <form action="deleteAccount" method="post">
                            <input class="gray" type="submit" value=${delete}/>
                        </form>
                    </c:when>
                    <c:when test="${sessionScope.user.role != 'USER'}">
                        <form action="app">
                            <input type="hidden" name="command" value="user-list">
                            <input class="blue" type="submit" value=${users}>
                        </form>
                        <form action="app">
                            <input type="hidden" name="command" value="offer-list">
                            <input class="blue" type="submit" value=${offered}/>
                        </form>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </div>
</div>
<footer>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</footer>
</body>
</html>