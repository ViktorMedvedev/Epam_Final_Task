<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="../css/style.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.page.title.registration" var="pageTitle"/>
    <fmt:message bundle="${locale}" key="locale.user.label.email" var="email"/>
    <fmt:message bundle="${locale}" key="locale.user.label.login" var="login"/>
    <fmt:message bundle="${locale}" key="locale.user.label.password" var="password"/>
    <fmt:message bundle="${locale}" key="locale.user.button.signup" var="button"/>
    <fmt:message bundle="${locale}" key="locale.user.text.haveAccountAlready" var="toLogin"/>
    <fmt:message bundle="${locale}" key="locale.user.button.signin" var="signIn"/>
    <fmt:message bundle="${locale}" key="locale.user.register.fail" var="registerFailMessage"/>
    <fmt:message bundle="${locale}" key="locale.user.login.formatMessage" var="loginFormatMessage"/>
    <fmt:message bundle="${locale}" key="locale.user.password.formatMessage" var="passwordFormatMessage"/>
    <title>${pageTitle} | tattoo parlor </title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<div class="container">
    <div class="input-container">
        <form name="registerForm" method="POST" action="app">
            <input type="hidden" name="command" value="register"/>
            <span>${email}</span>
            <input type="email" name="email"
                   maxlength="60"
                   minlength="10"
                   required>
            <span>${login}</span>
            <input name="login"
                   maxlength="20"
                   pattern="[A-Za-z0-9._]{4,}"
                   title="${loginFormatMessage}"
                   required>
            <span>${password}</span>
            <input type="password" name="password"
                   maxlength="32"
                   pattern="[^<>]{8,}"
                   title="${passwordFormatMessage}"
                   required>
            <input class="button button-blue" type="submit" value=${button}>
            <c:choose>
                <c:when test="${not empty sessionScope.dataExists}">
                    ${registerFailMessage}
                </c:when>
            </c:choose>
        </form>
        <form action="login">
            <input class="button button-gray" type="submit" value=${signIn}>
            ${sessionScope.dataExists = null}
        </form>
    </div>
</div>
</body>
</html>