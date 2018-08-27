<%@ page isErrorPage="true" isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Error 400</title>
</head>
<body>
<h2>Error 400</h2>
Wrong request!<br/>
<a href="${pageContext.request.contextPath}/app?command=home">Return home page.</a>
</body>
</html>