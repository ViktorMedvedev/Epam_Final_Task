<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.card.info.style" var="style"/>
    <fmt:message bundle="${locale}" key="locale.card.info.size" var="size"/>
    <fmt:message bundle="${locale}" key="locale.action.acceptOrder" var="accept"/>
    <fmt:message bundle="${locale}" key="locale.action.declineOrder" var="decline"/>
    <fmt:message bundle="${locale}" key="locale.adminRoom.button.orders" var="orders"/>
    <fmt:message bundle="${locale}" key="locale.table.userLogin" var="login"/>
    <fmt:message bundle="${locale}" key="locale.table.userEmail" var="email"/>
    <fmt:message bundle="${locale}" key="locale.card.info.price" var="total"/>
    <fmt:message bundle="${locale}" key="locale.action.acceptOrder" var="accept"/>
    <fmt:message bundle="${locale}" key="locale.action.declineOrder" var="decline"/>
    <fmt:message bundle="${locale}" key="locale.message.errorAcceptOrder" var="error"/>
    <fmt:message bundle="${locale}" key="locale.adminRoom.button.offered" var="offered"/>

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
    <meta charset="UTF-8">
    <title>Заказать</title>
    <link rel="stylesheet" href="../css/style.css">
    <script src="../js/jquery-3.3.1.js"></script>
    <script src="../js/main.js"></script>
</head>
<body>
<header>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
</header>
<c:choose>
    <c:when test="${sessionScope.user.role == 'USER' || empty sessionScope.user}">
        <jsp:forward page="/login"/>
    </c:when>
</c:choose>
<div class="order-card-container">
    <div class="order-card">
        <div class="order-card_img-container">
            <img src="image?imageId=${sessionScope.offer.offerId}&table=offers"/>
        </div>
        <div class="order-card_info-container">
            <form action="app" method="post">
                <p class="all-info offer-info">
                <table class="offer-offer-table" style="font-style: normal; color:white">
                    <tbody>
                    <tr>
                        <td>${style}:</td>
                        <td>
                            <select name="style">
                                <option></option>
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
                        </td>
                    </tr>
                    <tr>
                        <td>${size}:</td>
                        <td>
                            <select name="size">
                                <option></option>
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
                        </td>
                    </tr>
                    <tr>
                        <td>${total}:</td>
                        <td><input placeholder="${total}" name="price" type="number"></td>
                    </tr>
                    </tbody>
                </table>
                </p>

                <c:choose>
                    <c:when test="${not empty requestScope.wrongData}">
                        ${error}
                    </c:when>
                </c:choose>
                <input type="hidden" name="command" value="accept-offer">
                <input type="submit" value="${accept}"/>
            </form>
            <form action="app" method="post">
                <input type="hidden" name="command" value="offer-list">
                <input type="submit" value=${offered}/>
            </form>
            <form action="app" method="post">
                <input type="hidden" name="command" value="decline-offer">
                <input class="gray" type="submit" value="${decline}"/>
            </form>
        </div>
    </div>
</div>
<footer>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</footer>
</body>
</html>
