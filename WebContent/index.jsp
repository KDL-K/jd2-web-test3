<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Welcome</title>
</head>
<body>
    <c:set var="goto_request" scope="session" value="/WEB-INF/jsp/authorization.jsp"/>
    <c:redirect url="Controller?command=go_to_some_page"/>

</body>
</html>