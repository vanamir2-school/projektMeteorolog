<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<a href="/">
    <img src="<c:url value='https://image.flaticon.com/icons/png/512/53/53567.png'/>" width="42" height="42" border="0" />
</a>
<title>Cities</title>
<h1>List of all cities saved in database:</h1>
<c:forEach var="city" items="${cities}">
    <p><c:out value="${city}"></c:out></p>
</c:forEach>

</body>
</html>