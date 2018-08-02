<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.page.title.info" var="pageTitle"/>
    <fmt:message bundle="${locale}" key="locale.user.button.signin" var="signIn"/>
    <fmt:message bundle="${locale}" key="locale.user.button.signin" var="signIn"/>
    <fmt:message bundle="${locale}" key="locale.page.toHomePage" var="homePage"/>
    <fmt:message bundle="${locale}" key="locale.message.successSignIn" var="successSignIn"/>
    <fmt:message bundle="${locale}" key="locale.message.successSignUp" var="successSignUp"/>
    <fmt:message bundle="${locale}" key="locale.message.successChangeLocale" var="successChangeLocale"/>
    <fmt:message bundle="${locale}" key="locale.message.successChangePassword" var="successChangePassword"/>
    <fmt:message bundle="${locale}" key="locale.message.successOrder" var="successOrder"/>
    <fmt:message bundle="${locale}" key="locale.message.failOrder" var="failOrder"/>


    <title>${pageTitle} | Tattoo Parlor</title>
    <link rel="stylesheet" href="../css/style.css">

</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<div class="container">
    <div class="input-container">
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <form action="app">
                    <input type="hidden" name="command" value="home">
                    <c:choose>
                        <c:when test="${not empty sessionScope.changedPassword}">
                            ${successChangePassword}
                        </c:when>
                        <c:when test="${not empty sessionScope.changedLocale}">
                            ${successChangeLocale}
                        </c:when>
                        <c:when test="${not empty sessionScope.signedIn}">
                            ${successSignIn} ${sessionScope.user.login}!
                        </c:when>
                        <c:when test="${sessionScope.order == 'success'}">
                            ${successOrder}
                        </c:when>
                        <c:when test="${sessionScope.order == 'fail'}">
                            ${failOrder}
                        </c:when>
                    </c:choose>
                    <br/>
                    <input class="button button-blue" type="submit" value=${homePage}>
                        ${sessionScope.changedPassword = null}
                        ${sessionScope.changedLocale = null}
                        ${sessionScope.signedIn = null}
                        ${sessionScope.order = null}
                        ${sessionScope.wrongData = null}
                </form>
            </c:when>
            <c:when test="${empty sessionScope.user}">
                <form action="login">
                    <c:choose>
                        <c:when test="${not empty sessionScope.changedLocale}">
                            ${successChangeLocale}
                        </c:when>
                        <c:when test="${not empty sessionScope.registered}">
                            ${successSignUp}
                        </c:when>
                    </c:choose>
                    <br/>
                    <input class="button button-blue" type="submit" value=${signIn}>
                        ${sessionScope.changedLocale = null}
                        ${sessionScope.registered = null}
                        ${sessionScope.wrongData = null}
                </form>
            </c:when>
        </c:choose>
    </div>
</div>
</body>
</html>