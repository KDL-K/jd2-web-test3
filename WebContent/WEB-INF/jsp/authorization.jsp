<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Authorization</title>

   <fmt:setLocale value="${sessionScope.local}"/>
   <fmt:setBundle basename="resources.local" var="loc"/>
   <fmt:message bundle="${loc}" key="local.lang_button_ru" var="button_ru"/>
   <fmt:message bundle="${loc}" key="local.lang_button_en" var="button_en"/>
   
   <fmt:message bundle="${loc}" key="local.lang_login" var="l_login"/>
   <fmt:message bundle="${loc}" key="local.lang_password" var="l_password"/>
   <fmt:message bundle="${loc}" key="local.lang_sign_in" var="l_sign_in"/>
   <fmt:message bundle="${loc}" key="local.button_registration" var="l_registration"/>
   

</head>
<body>
  <form action="Controller" method="post">
      <input type="hidden" name="command" value="change_language"/>
      <input type="hidden" name="lang" value="ru"/>
      <input type="submit" value="${button_ru}"/>
   </form> 
   <form action="Controller" method="post">
      <input type="hidden" name="command" value="change_language"/>
      <input type="hidden" name="lang" value="en"/>
      <input type="submit" value="${button_en}"/>
   </form> 

 <p style="color:red">
    <c:if test="${not empty param.message}">
       <c:out value="${param.message}"></c:out>
    </c:if>
 </p>
 <p style="color:red">
    <c:if test="${not empty messageAttr}">
       <c:out value="${messageAttr}"></c:out>
    </c:if>
 </p>
 
  <form action="Controller" method="post">
      <input type="hidden" name="command" value="authorization"/>
     <c:out value="${l_login}"/>
      <input type="text" name="login" value=""/><br/>
     <c:out value="${l_password}"/>
      <input type="password" name="password" value=""/><br/>
      <input type="submit" value="${l_sign_in}"/>
  </form> 
  <br/>
  <form action="Controller" method="post">
      <input type="hidden" name="command" value="go_to_registration_page"/>
      <input type="submit" value="${l_registration}" style="color:grey"/>
  </form>  

</body>
</html>