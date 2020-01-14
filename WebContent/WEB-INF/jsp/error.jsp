<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
   <fmt:setLocale value="${sessionScope.local}"/>
   <fmt:setBundle basename="resources/local" var="loc"/>
   
   <fmt:message bundle="${loc}" key="local.button_main_page" var="b_main"/>
</head>
<body>
  <c:if test="${not empty param.error_message}">
    <c:out value="${param.error_message}"></c:out>
  </c:if><br/>
  
  <form action="Controller" method="post">
      <input type="hidden" name="command" value="go_to_authorization_page"/>
      <input type="submit" value="${b_main}"/>
  </form><br/>

</body>
</html>