<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.adminRoom.button.orders" var="pageTitle"/>
    <fmt:message bundle="${locale}" key="locale.table.orderId" var="orderId"/>
    <fmt:message bundle="${locale}" key="locale.table.userId" var="userId"/>
    <fmt:message bundle="${locale}" key="locale.table.tattooId" var="tattooId"/>
    <fmt:message bundle="${locale}" key="locale.table.totalPrice" var="totalPrice"/>
    <fmt:message bundle="${locale}" key="locale.table.orderStatus" var="orderStatus"/>
    <fmt:message bundle="${locale}" key="locale.message.emptyOrderList" var="emptyOrderList"/>
    <fmt:message bundle="${locale}" key="locale.table.openAction" var="open"/>
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
            <c:when test="${not empty sessionScope.orders}">
                <jsp:include page="/WEB-INF/jsp/pageContainer.jsp"/>
                <table style="font-style: normal; color:white">
                    <tr>
                        <th>${orderId}</th>
                        <th>${userId}</th>
                        <th>${tattooId}</th>
                        <th>${totalPrice}</th>
                        <th>${orderStatus}</th>
                        <th></th>
                    </tr>
                    <c:forEach var="order" items="${sessionScope.orders}" varStatus="status">
                        <tr>
                            <td>${order.orderId}</td>
                            <td>${order.userId}</td>
                            <td>${order.tattooId}</td>
                            <td>${order.totalPrice}</td>
                            <c:choose>
                                <c:when test="${order.status == 'Accepted'}">
                                    <td style="font-style: normal; color:green">${order.status}</td>
                                </c:when>
                                <c:when test="${order.status == 'Declined'}">
                                    <td style="font-style: normal; color:red">${order.status}</td>
                                </c:when>
                                <c:otherwise>
                                    <td style="font-style: normal; color:white">${order.status}</td>
                                </c:otherwise>
                            </c:choose>
                            <td>
                                <c:choose>
                                    <c:when test="${sessionScope.user.role == 'USER'}">
                                        <form action="app">
                                            <input type="hidden" name="command" value="tattoo-page">
                                            <input type="hidden" name="tattooId" value="${order.tattooId}">
                                            <input type="submit" value="${open}">
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form action="app" method="post">
                                            <input type="hidden" name="command" value="order-decision-page">
                                            <input type="hidden" name="tattooId" value="${order.tattooId}">
                                            <input type="hidden" name="userId" value="${order.userId}">
                                            <input type="submit" value="${open}">
                                        </form>
                                    </c:otherwise>
                                </c:choose>
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