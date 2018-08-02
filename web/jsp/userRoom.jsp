<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Личный кабинет</title>
    <link rel="stylesheet" href="../css/style.css">
    <script src="../WEB-INF/js/jquery-3.3.1.js"></script>
    <script src="../WEB-INF/js/main.js"></script>
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
    <fmt:message bundle="${locale}" key="locale.userRoom.button.offer" var="suggestPhoto"/>
    <fmt:message bundle="${locale}" key="locale.userRoom.button.delete" var="delete"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
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
                <form action="app">
                    <input type="hidden" name="command" value="logout">
                    <input class="gray" type="submit" value="${logout}"/>
                </form>
            </div>
            <div class="button-container">
                <form action="changePassword">
                    <input class="blue" type="submit" value=${changePassword}/>
                </form>
                <form action="app">
                    <input type="hidden" name="command" value="order-list">
                    <input type="hidden" name="role" value="${sessionScope.user.role}">
                    <input type="hidden" name="userId" value="${sessionScope.user.id}">
                    <input class="blue" type="submit" value=${orders}>
                </form>
                <form>
                    <input class="blue" type="submit" value=${suggestPhoto}/>
                </form>
                <form>
                    <input class="gray" type="submit" value=${delete}/>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>