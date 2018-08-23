<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.page.title.uploadPhoto" var="pageTitle"/>


    <fmt:message bundle="${locale}" key="locale.uploadFile.choose" var="choose"/>
    <fmt:message bundle="${locale}" key="locale.uploadFile.send" var="send"/>
    <fmt:message bundle="${locale}" key="locale.action.uploadPhoto" var="upload"/>
    <fmt:message bundle="${locale}" key="locale.message.failOffer" var="failOffer"/>
    <fmt:message bundle="${locale}" key="locale.card.info.price" var="total"/>
    <fmt:message bundle="${locale}" key="locale.message.errorAcceptOrder" var="error"/>

    <fmt:message bundle="${locale}" key="locale.card.info.style" var="style"/>
    <fmt:message bundle="${locale}" key="locale.card.info.size" var="size"/>

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


    <title>${pageTitle} | Tattoo Parlor</title>
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
<div class="container">
    <c:choose>
        <c:when test="${sessionScope.user.role == 'USER'}">
            <div class="input-container">
                <form action="uploadFile" method="post" enctype="multipart/form-data">
                    <input type="file"
                           class="inputfile"
                           name="file"
                           id="file"
                           accept="image/*"
                           style="opacity: 0"
                           value=${choose}/>
                    <label for="file">${choose}</label>
                    <script>
                        document.getElementById("file")
                            .addEventListener("change", function(e) {
                                document.querySelector("[for=" + e.target.id + "]")
                                    .innerHTML = e.target.files[0].name;
                            })
                    </script>
                    <input type="hidden" name="user" value="user">
                    <input class="button button-blue" type="submit" value=${send}>
                    <c:choose>
                        <c:when test="${not empty requestScope.wrongData}">
                            ${failOffer}
                        </c:when>
                    </c:choose>
                </form>
            </div>
        </c:when>
        <c:otherwise>
            <div class="input-container">
                <form action="uploadFile" method="post" enctype="multipart/form-data">
                    <input type="file"
                           class="inputfile"
                           name="file"
                           id="file"
                           accept="image/*"
                           style="opacity: 0"
                           value=${choose}/>
                    <label for="file">${choose}</label>
                    <script>
                        document.getElementById("file")
                            .addEventListener("change", function(e) {
                                document.querySelector("[for=" + e.target.id + "]")
                                    .innerHTML = e.target.files[0].name;
                            })
                    </script>
                    <p class="all-info offer-info">
                    <table class="upload-offer-table" style="font-style: normal; color:white">
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
                    <input type="hidden" name="user" value="admin">
                    <input class="button button-blue" type="submit" value=${upload}>
                    <c:choose>
                        <c:when test="${not empty requestScope.wrongData}">
                            ${error}
                        </c:when>
                    </c:choose>
                </form>
            </div>
        </c:otherwise>
    </c:choose>
</div>
<footer>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</footer>
</body>
</html>