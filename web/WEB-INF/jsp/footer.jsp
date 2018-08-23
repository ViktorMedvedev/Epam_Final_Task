<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ex" uri="/WEB-INF/tld/info.tld" %>

<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../../css/style.css">

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources.locale.locale" var="locale"/>
    <fmt:message bundle="${locale}" key="locale.tag.info.email" var="email"/>

</head>
<body>
<div class="footer">
    <div class="info-tag-container">
        <div class = "info-line">
            <ex:Info>
            ${email}
        </ex:Info>
        </div>
    </div>
</div>
</body>
</html>
