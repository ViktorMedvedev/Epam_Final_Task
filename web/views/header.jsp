<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="../css/style.css">

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.lang.text.english" var="en"/>
    <fmt:message bundle="${locale}" key="locale.lang.text.russian" var="ru"/>
    <fmt:message bundle="${locale}" key="locale.header.signIn" var="signIn"/>
</head>
<body>
<div class="header">
    <div class="logo-container">
        <a href="./main.jsp"><img src="../res/img/logo.png"></a>
    </div>
    <div class="search-container">
        <form class="search">
            <input type="text"/>
            <input type="submit" value="Поиск...">
        </form>
    </div>
    <div class="lang-button-container">
        <form name="changeLangForm" method="POST" action="app">
            <input type="hidden" name="command" value="locale"/>
            <input class="lang-button" type="submit" value=${en}/>
            <input class="lang-button" type="submit" value=${ru}/>
        </form>
        <form>
            <input class="lang-button" type="submit" value=${signIn}/>
        </form>
    </div>
</div>
</body>
</html>