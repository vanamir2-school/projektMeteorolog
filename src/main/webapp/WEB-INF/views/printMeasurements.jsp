<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <style><%@include file="/WEB-INF/css/style.css"%></style>
</head>
<body>
<a href="${pageContext.request.contextPath}/">
    <img src="<c:url value='https://image.flaticon.com/icons/png/512/53/53567.png'/>" width="42" height="42" border="0" />
</a>
<title>Measurements</title>

<h1>List of all measurements saved in database:</h1>

<table>
    <tr>
        <th>city</th>
        <th>time</th>
        <th>temperature [°C]</th>
        <th>humidity [%]</th>
        <th>pressure [hPa]</th>
        <th>wind speed [m/s]</th>
    </tr>
    <c:forEach var="measurement" items="${measurements}">
        <tr>
            <td>${measurement.cityName()}</td>
            <td>${measurement.timeOfMeasurementReadable()}</td>
            <td>${measurement.temperature}</td>
            <td>${measurement.humidity}</td>
            <td>${measurement.pressure}</td>
            <td>${measurement.wind}</td>
        </tr>
    </c:forEach>
</table>



</body>
</html>