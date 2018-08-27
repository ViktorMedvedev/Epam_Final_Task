<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="../../css/style.css">

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.lang.text.english" var="en"/>
    <fmt:message bundle="${locale}" key="locale.lang.text.russian" var="ru"/>
    <fmt:message bundle="${locale}" key="locale.header.userRoom" var="userRoom"/>
    <fmt:message bundle="${locale}" key="locale.action.search" var="search"/>
    <fmt:message bundle="${locale}" key="locale.user.button.signin" var="signIn"/>

    <fmt:message bundle="${locale}" key="locale.select.style.All" var="All"/>
    <fmt:message bundle="${locale}" key="locale.select.style.Dotwork" var="Dotwork"/>
    <fmt:message bundle="${locale}" key="locale.select.style.Trash-Polka" var="TrashPolka"/>
    <fmt:message bundle="${locale}" key="locale.select.style.Watercolor" var="Watercolor"/>
    <fmt:message bundle="${locale}" key="locale.select.style.Blackwork" var="Blackwork"/>
    <fmt:message bundle="${locale}" key="locale.select.style.OldSchool" var="OldSchool"/>
    <fmt:message bundle="${locale}" key="locale.select.style.Oriental" var="Oriental"/>
    <fmt:message bundle="${locale}" key="locale.select.style.Realism" var="Realism"/>
    <fmt:message bundle="${locale}" key="locale.select.style.Biomechanics" var="Biomechanics"/>
    <fmt:message bundle="${locale}" key="locale.select.style.Geometry" var="Geometry"/>
    <fmt:message bundle="${locale}" key="locale.select.style.Polynesia" var="Polynesia"/>
    <fmt:message bundle="${locale}" key="locale.select.style.NewSchool" var="NewSchool"/>
    <fmt:message bundle="${locale}" key="locale.select.style.Ornamental" var="Ornamental"/>
    <fmt:message bundle="${locale}" key="locale.select.style.Ethnics" var="Ethnics"/>
    <fmt:message bundle="${locale}" key="locale.select.style.Japan" var="Japan"/>
    <fmt:message bundle="${locale}" key="locale.select.style.Handpoke" var="Handpoke"/>
    <fmt:message bundle="${locale}" key="locale.select.style.Minimalism" var="Minimalism"/>

    <fmt:message bundle="${locale}" key="locale.select.size.All" var="All"/>
    <fmt:message bundle="${locale}" key="locale.select.size.size01" var="size01"/>
    <fmt:message bundle="${locale}" key="locale.select.size.size02" var="size02"/>
    <fmt:message bundle="${locale}" key="locale.select.size.size03" var="size03"/>
    <fmt:message bundle="${locale}" key="locale.select.size.size04" var="size04"/>
    <fmt:message bundle="${locale}" key="locale.select.size.size05" var="size05"/>
    <fmt:message bundle="${locale}" key="locale.select.size.size06" var="size06"/>
    <fmt:message bundle="${locale}" key="locale.select.size.size07" var="size07"/>
    <fmt:message bundle="${locale}" key="locale.select.size.size08" var="size08"/>
    <fmt:message bundle="${locale}" key="locale.select.size.size09" var="size09"/>
    <fmt:message bundle="${locale}" key="locale.select.size.size10" var="size10"/>
    <fmt:message bundle="${locale}" key="locale.select.size.size11" var="size11"/>
    <fmt:message bundle="${locale}" key="locale.select.size.size12" var="size12"/>
    <fmt:message bundle="${locale}" key="locale.select.size.size13" var="size13"/>
    <fmt:message bundle="${locale}" key="locale.select.size.size14" var="size14"/>
    <fmt:message bundle="${locale}" key="locale.select.size.size15" var="size15"/>
</head>
<body>
<div class="header">

            <form action="app">
                <input type="hidden" name="command" value="home">
                <input type="hidden" name="page" value="1">
                <input type="hidden" name="style" value="${All}">
                <input type="hidden" name="size" value="${All}">
                <div class="logo-container">
                    <input type="image" src="../../res/img/logo.png">
                </div>
            </form>


    <div class="search-container">
                <form class="search" action="app">
                    <input type="hidden" name="command" value="home">
                    <select name="style">
                        <option>${All}</option>
                        <option>${Dotwork}</option>
                        <option>${TrashPolka}</option>
                        <option>${Watercolor}</option>
                        <option>${Blackwork}</option>
                        <option>${OldSchool}</option>
                        <option>${Oriental}</option>
                        <option>${Realism}</option>
                        <option>${Biomechanics}</option>
                        <option>${Geometry}</option>
                        <option>${Polynesia}</option>
                        <option>${NewSchool}</option>
                        <option>${Ornamental}</option>
                        <option>${Ethnics}</option>
                        <option>${Japan}</option>
                        <option>${Handpoke}</option>
                        <option>${Minimalism}</option>
                    </select>
                    <select name="size">
                        <option>${All}</option>
                        <option>${size01}</option>
                        <option>${size02}</option>
                        <option>${size03}</option>
                        <option>${size04}</option>
                        <option>${size05}</option>
                        <option>${size06}</option>
                        <option>${size07}</option>
                        <option>${size08}</option>
                        <option>${size09}</option>
                        <option>${size10}</option>
                        <option>${size11}</option>
                        <option>${size12}</option>
                        <option>${size13}</option>
                        <option>${size14}</option>
                        <option>${size15}</option>
                    </select>
                    <input type="submit" value=${search}>
                </form>
    </div>
    <div class="lang-button-container">
        <form action="app">
            <input type="hidden" name="command" value="locale"/>
            <input class="lang-button" type="submit" name="lang" value=${en}>
        </form>
        <form action="app">
            <input type="hidden" name="command" value="locale"/>
            <input class="lang-button" type="submit" name="lang" value=${ru}>
        </form>
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <form action="userRoom">
                    <input class="lang-button" type="submit" value=${userRoom}>
                </form>
            </c:when>
            <c:otherwise>
                <form action="login">
                    <input class="lang-button" type="submit" value=${signIn}>
                </form>
            </c:otherwise>
        </c:choose>
    </div>

</div>
</body>
</html>