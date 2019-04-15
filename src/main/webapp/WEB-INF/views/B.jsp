<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="value" scope="page" class="java.lang.String" /> // sample is java file name



<%
        var idElement = document.getElementById("idName");
        var selectedValue = idElement.options[idElement.selectedIndex].value;


        String name = request.getParameter("text1");
        request.setAttribute();
        int iRowAffected = 0;

        //-------now pass parameter "name" to your sample java file

        value.function_name("name");
%>