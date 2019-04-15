<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<title>Insert title here</title>

<h1>Test page:</h1>
<c:forEach var="a" items="${list}">
    <p><c:out value="${a}"></c:out></p>
</c:forEach>

<%
    Integer hitsCount = (Integer)application.getAttribute("hitCounter");
    if( hitsCount ==null || hitsCount == 0 ) {
        /* First visit */
        out.println("Welcome to my website!");
        hitsCount = 1;
    } else {
        /* return visit */
        out.println("Welcome back to my website!");
        hitsCount += 1;
    }
    application.setAttribute("hitCounter", hitsCount);
%>

<center>
    <p>Total number of visits: <%= hitsCount%></p>
</center>

<form action="MyCity" method="POST">
    <select name="City" >
        <option>Kanpur</option>
        <option>Ghazipur</option>
        <option>Allahabad</option>
        <option>Maunathbhanjan </option>
    </select>
    <input type="submit"/>
</form>

<select name="item">
    <c:forEach items="${list}" var="id">
        <option value="${id}">${id}</option>
    </c:forEach>
</select>


<form:form method="POST"
           action="/addEmployee" modelAttribute="employee">
    <table>
        <tr>
            <td><form:label path="name">Name</form:label></td>
            <td><form:input path="name"/></td>
        </tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form:form>

${name}
${name}


</body>
</html>