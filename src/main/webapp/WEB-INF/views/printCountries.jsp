<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<title>Insert title here</title>
<h1>Seznam všech zemí v databázi:</h1>
<c:forEach var="country" items="${countries}">
    <p><c:out value="${country}"></c:out></p>
</c:forEach>

</body>
</html>