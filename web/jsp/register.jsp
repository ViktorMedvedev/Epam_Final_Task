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
    <fmt:message bundle="${locale}" key="locale.user.label.confirmPassword" var="confirmPassword"/>
    <fmt:message bundle="${locale}" key="locale.user.button.signup" var="button"/>
    <fmt:message bundle="${locale}" key="locale.user.text.haveAccountAlready" var="toLogin"/>
    <fmt:message bundle="${locale}" key="locale.user.button.signin" var="signIn"/>
    <fmt:message bundle="${locale}" key="locale.user.register.fail" var="registerFailMessage"/>
    <fmt:message bundle="${locale}" key="locale.user.login.formatMessage" var="loginFormatMessage"/>
    <fmt:message bundle="${locale}" key="locale.user.password.formatMessage" var="passwordFormatMessage"/>
    <fmt:message bundle="${locale}" key="locale.message.passwordsDoNotMatch" var="passwordsDoNotMatch"/>    <fmt:message bundle="${locale}" key="locale.message.passwordsDoNotMatch" var="passwordsDoNotMatch"/>
    <title>${pageTitle}</title>
</head>
<body>
<header>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
</header>
<c:choose>
    <c:when test="${not empty sessionScope.user}">
        <jsp:forward page="/home"/>
    </c:when>
</c:choose>
<div class="container">
    <div class="input-container">
        <form name="registerForm" method="POST" action="app">
            <input type="hidden" name="command" value="register"/>
            <script>
                function checkPass()
                {
                    var pass1 = document.getElementById('pass1');
                    var pass2 = document.getElementById('pass2');
                    var goodColor = "#66cc66";
                    var badColor = "#ff6666";
                    if(pass1.value === pass2.value){
                        pass2.style.backgroundColor = goodColor;
                        message.style.color = goodColor;
                    }else{
                        pass2.style.backgroundColor = badColor;
                        message.style.color = badColor;
                    }
                }
            </script>
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
                   id="pass1"
                   maxlength="32"
                   pattern="[^<>]{8,}"
                   title="${passwordFormatMessage}"
                   required>
            <span>${confirmPassword}</span>
            <input type="password" name="confirmPassword"
                   id="pass2"
                   maxlength="32"
                   pattern="[^<>]{8,}"
                   title="${passwordFormatMessage}"
                   onkeyup="checkPass()"
                   required>
            <input class="button button-blue" type="submit" value=${button}>
            <c:choose>
                <c:when test="${not empty requestScope.dataExists}">
                    ${registerFailMessage}
                </c:when>
                <c:when test="${not empty requestScope.wrongData}">
                    ${passwordsDoNotMatch}
                </c:when>
            </c:choose>
        </form>
        <form action="login">
            <input class="button button-gray" type="submit" value=${signIn}>
        </form>
    </div>
</div>
<footer>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</footer>
</body>
</html>