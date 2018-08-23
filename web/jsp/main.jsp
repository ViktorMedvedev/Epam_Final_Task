<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../css/style.css">
    <script src="../js/jquery-3.3.1.js"></script>
    <script src="../js/main.js"></script>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>
    <fmt:message bundle="${locale}" key="locale.card.info.style" var="style"/>

    <fmt:message bundle="${locale}" key="locale.card.info.size" var="size"/>
    <fmt:message bundle="${locale}" key="locale.card.info.price" var="price"/>
    <fmt:message bundle="${locale}" key="locale.card.info.rating" var="rating"/>
    <fmt:message bundle="${locale}" key="locale.card.button.open" var="open"/>
    <fmt:message bundle="${locale}" key="locale.page.title.main" var="pageTitle"/>
    <fmt:message bundle="${locale}" key="locale.message.searchFail" var="searchFail"/>
    <title>${pageTitle} | tattoo Parlor</title>

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
<jsp:include page="/WEB-INF/jsp/pageContainer.jsp"/>
<div class="tattoo-card-container">
    <c:forEach var="tattoo" items="${sessionScope.tattoos}" varStatus="status">
        <div class='tatoo-card'>
            <!--<div class="delete-button">X</div>-->
            <div class='img-container'>
                <img src="image?imageId=${tattoo.id}&table=tattoo"/>
            </div>
            <div class='info-container'>
                <div class='info'>
                    <span class='info-line'>${style}: ${tattoo.style}</span>
                    <span class='info-line'>${size}: ${tattoo.size}</span>
                    <span class='info-line'>${price}: $${tattoo.price}</span>
                    <span class='info-line'>${rating}: ${tattoo.rating}</span>
                </div>
                <div>
                    <form action="app" class="tattoo-card-button">
                        <input type="hidden" name="command" value="tattoo-page">
                        <input type="hidden" name="tattooId" value="${tattoo.id}">
                        <input type="submit" class="order-button" value="${open}">
                    </form>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<jsp:include page="/WEB-INF/jsp/pageContainer.jsp"/>
<footer>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</footer>
</body>
</html>