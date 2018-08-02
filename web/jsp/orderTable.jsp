<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.adminRoom.button.orders" var="pageTitle"/>
    <fmt:message bundle="${locale}" key="locale.table.orderId" var="orderId"/>
    <fmt:message bundle="${locale}" key="locale.table.userId" var="userId"/>
    <fmt:message bundle="${locale}" key="locale.table.tattooId" var="tattooId"/>
    <fmt:message bundle="${locale}" key="locale.table.totalPrice" var="totalPrice"/>
    <fmt:message bundle="${locale}" key="locale.table.orderStatus" var="orderStatus"/>
    <title>${pageTitle}</title>
    <link rel="stylesheet" href="../css/style.css">
    <script src="../WEB-INF/js/jquery-3.3.1.js"></script>
    <script src="../WEB-INF/js/main.js"></script>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<div class="info-table-container">
    <div class="info-table">
        <p>${pageTitle}:</p>
        <table>
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
                    <td>${order.status}</td>
                    <td>
                        <form>
                            <input type="submit" value="Action">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>