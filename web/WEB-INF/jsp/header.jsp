<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="../../css/style.css">

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.lang.text.english" var="en"/>
    <fmt:message bundle="${locale}" key="locale.lang.text.russian" var="ru"/>
    <fmt:message bundle="${locale}" key="locale.header.userRoom" var="userRoom"/>
</head>
<body>
<div class="header">
    <c:choose>
        <c:when test="${not empty sessionScope.user}">
            <form action="app">
                <input type="hidden" name="command" value="home">
                <div class="logo-container">
                    <input type="image" src="../../res/img/logo.png">
                </div>
            </form>
        </c:when>
        <c:otherwise>
            <form action="login">
                <div class="logo-container">
                    <input type="image" src="../../res/img/logo.png">
                </div>
            </form>
        </c:otherwise>
    </c:choose>

    <%--<a href="../../jsp/main.jsp"><img src="../../res/img/logo.png"></a>--%>

    <div class="search-container">
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <form class="search">
                    <input type="text"/>
                    <input type="submit" value="Поиск...">
                </form>
            </c:when>
        </c:choose>

    </div>
    <div class="lang-button-container">
        <form method="POST" action="app">
            <input type="hidden" name="command" value="locale"/>
            <input class="lang-button" type="submit" name="lang" value=${en}>
        </form>
        <form method="POST" action="app">
            <input type="hidden" name="command" value="locale"/>
            <input class="lang-button" type="submit" name="lang" value=${ru}>
        </form>
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <form action="userRoom">
                    <input class="lang-button" type="submit" value=${userRoom}>
                </form>
            </c:when>
        </c:choose>
    </div>
</div>
</body>
</html>