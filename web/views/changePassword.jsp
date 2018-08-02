<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.page.title.auth" var="pageTitle"/>
    <fmt:message bundle="${locale}" key="locale.user.label.login" var="loginLabel"/>
    <fmt:message bundle="${locale}" key="locale.user.label.password" var="passwordLabel"/>
    <fmt:message bundle="${locale}" key="locale.user.button.signin" var="button"/>

    <fmt:message bundle="${locale}" key="locale.user.button.signup" var="signUp"/>

    <fmt:message bundle="${locale}" key="locale.user.auth.fail" var="authFailMessage"/>
    <fmt:message bundle="${locale}" key="locale.user.login.formatMessage" var="loginFormatMessage"/>
    <fmt:message bundle="${locale}" key="locale.user.password.formatMessage" var="passwordFormatMessage"/>


    <title>${pageTitle} | tattoo Parlor</title>
    <link rel="stylesheet" href="../css/style.css">

</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<c:choose>
    <c:when test="${not empty sessionScope.user}">
        <jsp:forward page="/home"/>
    </c:when>
</c:choose>

<div class="container">
    <div class="input-container">
        <form name="loginForm" method="POST" action="app">
            <input type="hidden" name="command" value="login"/>
            <span>${loginLabel}</span>
            <input type="text"
                   name="login"
                   maxlength="16"
                   pattern="[A-Za-z0-9._]{4,}"
                   title="${loginFormatMessage}"
                   required>
            <span>${passwordLabel}</span>
            <input type="password"
                   name="password"
                   maxlength="32"
                   pattern="[^<>]{8,}"
                   title="${passwordFormatMessage}"
                   required>
            <input class="button button-blue" type="submit" value=${button}>
            <br/>
            <c:choose>
                <c:when test="${not empty sessionScope.wrongData}">
                    ${authFailMessage}
                </c:when>
            </c:choose>

        </form>
        <form action="register">
            <input class="button button-gray" type="submit" value=${signUp}>
            ${sessionScope.wrongData = null}
        </form>
    </div>
</div>
</body>
</html>