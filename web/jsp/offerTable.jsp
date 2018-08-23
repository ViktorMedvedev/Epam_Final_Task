<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.adminRoom.label.offered" var="pageTitle"/>
    <fmt:message bundle="${locale}" key="locale.table.offerId" var="offerId"/>
    <fmt:message bundle="${locale}" key="locale.table.userId" var="userId"/>
    <fmt:message bundle="${locale}" key="locale.message.emptyOrderList" var="emptyOrderList"/>
    <fmt:message bundle="${locale}" key="locale.table.openAction" var="open"/>
    <fmt:message bundle="${locale}" key="locale.page.toUserRoom" var="userRoom"/>
    <title>${pageTitle}</title>
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

<div class="info-table-container">
    <div class="info-table">
        <p>${pageTitle}:</p>
        <c:choose>
            <c:when test="${not empty sessionScope.offers}">
                <jsp:include page="/WEB-INF/jsp/pageContainer.jsp"/>
                <table style="font-style: normal; color:white">
                    <tr>
                        <th>${offerId}</th>
                        <th>${userId}</th>
                        <th></th>
                    </tr>
                    <c:forEach var="offer" items="${sessionScope.offers}" varStatus="status">
                        <tr>
                            <td>${offer.offerId}</td>
                            <td>${offer.userId}</td>
                            <td>
                                <form action="app" method="post">
                                    <input type="hidden" name="command" value="offer-page">
                                    <input type="hidden" name="offerId" value="${offer.offerId}">
                                    <input type="submit" value="${open}">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                ${emptyOrderList}
            </c:otherwise>
        </c:choose>
    </div>
</div>
<footer>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</footer>
</body>
</html>