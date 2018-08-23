<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>
    <fmt:message bundle="${locale}" key="locale.userRoom.label.delete" var="pageTitle"/>
    <fmt:message bundle="${locale}" key="locale.message.attentionDeleteTattoo" var="warning"/>
    <fmt:message bundle="${locale}" key="locale.action.delete" var="delete"/>
    <fmt:message bundle="${locale}" key="locale.page.toUserList" var="userList"/>
    <fmt:message bundle="${locale}" key="locale.page.toHomePage" var="homePage"/>
    <fmt:message bundle="${locale}" key="locale.select.style.All" var="All"/>

    <title>${pageTitle} | Tattoo Parlor</title>
    <link rel="stylesheet" href="../css/style.css">
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
<div class="container">
    <div class="input-container">
        <form method="POST" action="app">
            <input type="hidden" name="command" value="delete-tattoo">
            ${warning}
            <input class="button button-gray" type="submit" value=${delete}>
            <br/>
        </form>
        <form action="app">
            <input type="hidden" name="command" value="home">
            <input type="hidden" name="style" value="${All}">
            <input type="hidden" name="size" value="${All}">
            <input class="button button-blue" type="submit" value=${homePage}>
        </form>
    </div>
</div>
<footer>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</footer>
</body>
</html>
