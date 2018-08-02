<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.page.title.auth" var="pageTitle"/>]
    <fmt:message bundle="${locale}" key="locale.password.oldPassword" var="oldPassword"/>
    <fmt:message bundle="${locale}" key="locale.password.newPassword" var="newPassword"/>
    <fmt:message bundle="${locale}" key="locale.action.confirm" var="confirm"/>
    <fmt:message bundle="${locale}" key="locale.user.button.signin" var="button"/>
    <fmt:message bundle="${locale}" key="locale.user.button.signup" var="signUp"/>
    <fmt:message bundle="${locale}" key="locale.message.failChangePassword" var="failMessage"/>
    <fmt:message bundle="${locale}" key="locale.user.login.formatMessage" var="loginFormatMessage"/>
    <fmt:message bundle="${locale}" key="locale.user.password.formatMessage" var="passwordFormatMessage"/>


    <title>${pageTitle} | Tattoo Parlor</title>
    <link rel="stylesheet" href="../css/style.css">

</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<div class="container">
    <div class="input-container">
        <form name="changePasswordForm" method="POST" action="app">
            <input type="hidden" name="command" value="change-password">
            <input type="hidden" name="login" value="${sessionScope.user.login}">
            <span>${oldPassword}</span>
            <input type="password"
                   name="oldPassword"
                   maxlength="32"
                   pattern="[^<>]{8,}"
                   title="${passwordFormatMessage}"
                   required>
            <span>${newPassword}</span>
            <input type="password"
                   name="newPassword"
                   maxlength="32"
                   pattern="[^<>]{8,}"
                   title="${passwordFormatMessage}"
                   required>
            <input class="button button-blue" type="submit" value=${confirm}>
            <c:choose>
                <c:when test="${not empty sessionScope.wrongData}">
                    ${failMessage}
                </c:when>
            </c:choose>
            <br/>
        </form>
    </div>
</div>
</body>
</html>