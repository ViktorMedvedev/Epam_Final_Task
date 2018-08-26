<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../../css/style.css">

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.pagination.next" var="next"/>
    <fmt:message bundle="${locale}" key="locale.pagination.previous" var="previous"/>

</head>
<body>
<div class="page-number-container">
    <c:if test="${empty requestScope.page}">
        ${requestScope.page = 1}
    </c:if>
    <c:if test="${requestScope.page > 1}">
        <form action="app">
            <input type="hidden" name="command" value="home">
            <input type="hidden" name="page" value="${requestScope.page - 1}">
            <input class="page-number-blue" type="submit" value="${previous}">
        </form>
    </c:if>
    <c:if test="${requestScope.page < 1}">
        ${requestScope.page = 1}
    </c:if>
    <c:if test="${requestScope.page > sessionScope.noOfPages}">
        ${requestScope.page = sessionScope.noOfPages}
    </c:if>
    <c:forEach begin="1" end="${sessionScope.noOfPages}" var="i">
        <c:choose>
            <c:when test="${requestScope.page eq i}">
                <form>
                    <button class="page-number-gray" disabled>${i}</button>
                </form>
            </c:when>
            <c:otherwise>
                <form action="app">
                    <input type="hidden" name="command" value="home">
                    <input type="hidden" name="page" value="${i}">
                    <input class="page-number-blue" type="submit" value=${i}>
                </form>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <c:if test="${requestScope.page < sessionScope.noOfPages}">
        <form action="app">
            <input type="hidden" name="command" value="home">
            <input type="hidden" name="page" value="${requestScope.page + 1}">
            <input class="page-number-blue" type="submit" value="${next}">
        </form>
    </c:if>
</div>
</body>
</html>