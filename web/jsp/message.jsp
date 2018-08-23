<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.page.title.info" var="pageTitle"/>
    <fmt:message bundle="${locale}" key="locale.user.button.signin" var="signIn"/>
    <fmt:message bundle="${locale}" key="locale.user.button.signin" var="signIn"/>
    <fmt:message bundle="${locale}" key="locale.page.toHomePage" var="homePage"/>
    <fmt:message bundle="${locale}" key="locale.page.toUserRoom" var="userRoom"/>
    <fmt:message bundle="${locale}" key="locale.page.toUserList" var="userList"/>
    <fmt:message bundle="${locale}" key="locale.page.toOrderList" var="orderList"/>
    <fmt:message bundle="${locale}" key="locale.page.toOfferList" var="offerList"/>
    <fmt:message bundle="${locale}" key="locale.message.successSignIn" var="successSignIn"/>
    <fmt:message bundle="${locale}" key="locale.message.successSignUp" var="successSignUp"/>
    <fmt:message bundle="${locale}" key="locale.message.successChangeLocale" var="successChangeLocale"/>
    <fmt:message bundle="${locale}" key="locale.message.successChangePassword" var="successChangePassword"/>
    <fmt:message bundle="${locale}" key="locale.message.successOffer" var="successOffer"/>
    <fmt:message bundle="${locale}" key="locale.message.successOrder" var="successOrder"/>
    <fmt:message bundle="${locale}" key="locale.message.failOrder" var="failOrder"/>
    <fmt:message bundle="${locale}" key="locale.message.userDeleted" var="userDeleted"/>
    <fmt:message bundle="${locale}" key="locale.message.searchFail" var="searchFail"/>
    <fmt:message bundle="${locale}" key="locale.message.deleteOrderFail" var="deleteOrderFail"/>
    <fmt:message bundle="${locale}" key="locale.message.acceptOffer" var="acceptOffer"/>
    <fmt:message bundle="${locale}" key="locale.message.declineOffer" var="declineOffer"/>
    <fmt:message bundle="${locale}" key="locale.message.tattooDeleted" var="tattooDeleted"/>
    <fmt:message bundle="${locale}" key="locale.select.size.All" var="All"/>
    <fmt:message bundle="${locale}" key="locale.action.uploadPhoto" var="upload"/>


    <title>${pageTitle} | Tattoo Parlor</title>
    <link rel="stylesheet" href="../css/style.css">

</head>
<body>
<header>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
</header>
<div class="container">
    <div class="input-container">
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <c:choose>
                    <c:when test="${requestScope.message == 'changedPassword'}">
                        ${successChangePassword}
                    </c:when>
                    <c:when test="${requestScope.message == 'changedLocale'}">
                        ${successChangeLocale}
                    </c:when>
                    <c:when test="${requestScope.message == 'searchFail'}">
                        ${searchFail}
                        <form action="uploadPhotoPage" method="post">
                            <input class="button button-blue" type="submit" value=${upload}>
                                ${sessionScope.message = null}
                                ${sessionScope.wrongData = null}
                        </form>
                    </c:when>
                    <c:when test="${requestScope.message == 'offerAccepted'}">
                        ${acceptOffer}
                        <form action="app" method="post">
                            <input type="hidden" name="command" value="offer-list">
                            <br/>
                            <input class="button button-blue" type="submit" value=${orderList}>
                                ${sessionScope.message = null}
                                ${sessionScope.wrongData = null}
                        </form>
                    </c:when>
                    <c:when test="${requestScope.message == 'offerDeclined'}">
                        ${declineOffer}
                        <form action="app" method="post">
                            <input type="hidden" name="command" value="offer-list">
                            <br/>
                            <input class="button button-blue" type="submit" value=${offerList}>
                                ${sessionScope.message = null}
                                ${sessionScope.wrongData = null}
                        </form>
                    </c:when>
                    <c:when test="${requestScope.message == 'signedIn'}">
                        ${successSignIn} ${sessionScope.user.login}!
                    </c:when>
                    <c:when test="${requestScope.message == 'orderSuccess'}">
                        ${successOrder}
                        <form action="app">
                            <input type="hidden" name="command" value="order-list">
                            <br/>
                            <input class="button button-blue" type="submit" value="${orderList}">
                                ${sessionScope.message = null}
                                ${sessionScope.wrongData = null}
                        </form>
                    </c:when>
                    <c:when test="${requestScope.message == 'offerSuccess'}">
                        ${successOffer}
                    </c:when>
                    <c:when test="${requestScope.message == 'deleteOrderFail'}">
                        ${deleteOrderFail}
                    </c:when>
                    <c:when test="${requestScope.message == 'orderFail'}">
                        ${failOrder}
                        <form action="app">
                            <input type="hidden" name="command" value="order-list">
                            <br/>
                            <input class="button button-blue" type="submit" value=${orderList}>
                                ${sessionScope.message = null}
                                ${sessionScope.wrongData = null}
                        </form>
                    </c:when>
                    <c:when test="${requestScope.message == 'userDeleted'}">
                        ${userDeleted}
                        <form action="app" method="post">
                            <input type="hidden" name="command" value="user-list">
                            <br/>
                            <input class="button button-blue" type="submit" value=${userList}>
                                ${requestScope.message = null}
                                ${requestScope.wrongData = null}
                        </form>
                    </c:when>
                    <c:when test="${requestScope.message == 'tattooDeleted'}">
                        ${tattooDeleted}
                    </c:when>
                </c:choose>
                <form action="app">
                    <input type="hidden" name="command" value="home">
                    <input type="hidden" name="style" value="${All}">
                    <input type="hidden" name="size" value="${All}">
                    <br/>
                    <input class="button button-blue" type="submit" value=${homePage}>
                        ${requestScope.message = null}
                        ${requestScope.wrongData = null}

                </form>
                <form action="userRoom">
                    <input class="button button-blue" type="submit" value=${userRoom}>
                        ${requestScope.message = null}
                        ${requestScope.wrongData = null}
                </form>
            </c:when>
            <c:when test="${empty sessionScope.user}">
                <form action="login">
                    <c:choose>
                        <c:when test="${requestScope.message == 'changedLocale'}">
                            ${successChangeLocale}
                        </c:when>
                        <c:when test="${requestScope.message == 'signedUp'}">
                            ${successSignUp}
                        </c:when>
                        <c:when test="${empty requestScope.message}">
                            <jsp:forward page="/login"/>
                        </c:when>
                    </c:choose>
                    <br/>
                    <input class="button button-blue" type="submit" value=${signIn}>
                        ${requestScope.message = null}
                        ${requestScope.wrongData = null}
                </form>
            </c:when>
        </c:choose>
    </div>
</div>
<footer>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</footer>
</body>
</html>