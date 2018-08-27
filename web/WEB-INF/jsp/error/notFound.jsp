<%@ page isErrorPage="true" isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="locale.locale" var="locale"/>
    <title>Error 404</title>
</head>
<body>
<h2>404 error</h2><br/>
Page not found.<br/>
<a href="${pageContext.request.contextPath}/app?command=home">Return home page.</a>
</body>
</html>
