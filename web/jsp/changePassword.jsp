<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.page.title.auth" var="pageTitle"/>
    <fmt:message bundle="${locale}" key="locale.page.toUserRoom" var="userRoom"/>
    <fmt:message bundle="${locale}" key="locale.password.oldPassword" var="oldPassword"/>
    <fmt:message bundle="${locale}" key="locale.password.newPassword" var="newPassword"/>
    <fmt:message bundle="${locale}" key="locale.action.confirm" var="confirm"/>
    <fmt:message bundle="${locale}" key="locale.message.failChangePassword" var="failMessage"/>
    <fmt:message bundle="${locale}" key="locale.user.label.confirmPassword" var="confirmPassword"/>
    <fmt:message bundle="${locale}" key="locale.message.passwordsDoNotMatch" var="passwordsDoNotMatch"/>
    <fmt:message bundle="${locale}" key="locale.user.password.formatMessage" var="passwordFormatMessage"/>
    <title>${pageTitle} | Tattoo Parlor</title>
    <link rel="stylesheet" href="../css/style.css">
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
<div class="container">
    <div class="input-container">
        <form name="changePasswordForm" method="POST" action="app">
            <input type="hidden" name="command" value="change-password">
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
            <span>${oldPassword}</span>
            <input type="password"
                   name="oldPassword"
                   maxlength="32"
                   pattern="[^<>]{8,}"
                   title="${passwordFormatMessage}"
                   required>
            <span>${newPassword}</span>
            <input type="password"
                   id="pass1"
                   name="newPassword"
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
            <input class="button button-blue" type="submit" value=${confirm}>
            <c:choose>
                <c:when test="${requestScope.wrongData == 'passwordDoesNotMatch'}">
                    ${passwordsDoNotMatch}
                </c:when>
                <c:when test="${requestScope.wrongData == 'wrongPassword'}">
                    ${failMessage}
                </c:when>
            </c:choose>
            <br/>
        </form>
        <form action="userRoom">
            <input class="button button-gray" type="submit" value=${userRoom}>
        </form>
    </div>
</div>
<footer>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</footer>
</body>
</html>