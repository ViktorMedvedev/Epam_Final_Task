<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../../css/style.css">

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.lang.text.english" var="en"/>
    <fmt:message bundle="${locale}" key="locale.lang.text.russian" var="ru"/>

</head>
<body>
<div class="lang-button-container">
    <form name="changeLangForm" method="POST" action="app">
        <input type="hidden" name="command" value="locale"/>
        <input class="lang-button" type="submit" name="lang" value=${ru}>
        <input class="lang-button" type="submit" name="lang" value=${en}>
    </form>

</div>
</body>
</html>
