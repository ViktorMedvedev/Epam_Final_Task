<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.adminRoom.label.users" var="pageTitle"/>
    <fmt:message bundle="${locale}" key="locale.table.userId" var="userId"/>
    <fmt:message bundle="${locale}" key="locale.table.userEmail" var="userEmail"/>
    <fmt:message bundle="${locale}" key="locale.table.userLogin" var="userLogin"/>
    <fmt:message bundle="${locale}" key="locale.table.userRole" var="userRole"/>
    <fmt:message bundle="${locale}" key="locale.table.userDiscount" var="userDiscount"/>
    <fmt:message bundle="${locale}" key="locale.table.deleteAction" var="delete"/>
    <fmt:message bundle="${locale}" key="locale.table.updateRole" var="updateRole"/>
    <fmt:message bundle="${locale}" key="locale.message.noAccess" var="noAccess"/>

    <title>${pageTitle}</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<header>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
</header>
<c:choose>
    <c:when test="${empty sessionScope.user||sessionScope.user.role == 'USER'}">
        <jsp:forward page="/login"/>
    </c:when>
</c:choose>
<div class="info-table-container">
    <div class="info-table">
        <p>${pageTitle}:</p>
        <jsp:include page="/WEB-INF/jsp/pageContainer.jsp"/>
        <table style="font-style: normal; color:white">
            <tr>
                <th>${userId}</th>
                <th>${userEmail}</th>
                <th>${userLogin}</th>
                <th>${userRole}</th>
                <th>${userDiscount}</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach var="userFromTable" items="${sessionScope.users}" varStatus="status">
                <tr>
                    <td>${userFromTable.id}</td>
                    <td>${userFromTable.email}</td>
                    <td>${userFromTable.login}</td>
                    <td>${userFromTable.role}</td>
                    <td>${userFromTable.discountPct}</td>
                    <c:choose>
                        <c:when test="${userFromTable.role !='ADMIN' && sessionScope.user.role=='ADMIN'}">
                            <td>
                                <form method="post" action="app">
                                    <input type="hidden" name="command" value="delete-account-page">
                                    <input type="hidden" name="userId" value="${userFromTable.id}">
                                    <input type="submit" value="${delete}">
                                </form>
                            </td>
                            <td>
                                <form method="post" action="app">
                                    <input type="hidden" name="command" value="change-user-role">
                                    <input type="hidden" name="userId" value="${userFromTable.id}">
                                    <input type="submit" value=${updateRole}>
                                </form>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td></td>
                            <td></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<footer>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</footer>
</body>
</html>