<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>

   <fmt:setLocale value="${sessionScope.local}"/>
   <fmt:setBundle basename="resources/local" var="loc"/>
   
   <fmt:message bundle="${loc}" key="local.lang_button_ru" var="button_ru"/>
   <fmt:message bundle="${loc}" key="local.lang_button_en" var="button_en"/>
   
   <fmt:message bundle="${loc}" key="local.button_add_another_question" var="l_add_q"/>
   <fmt:message bundle="${loc}" key="local.button_save_test" var="l_save_t"/>  
   
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
   </form><br/>
   
 <c:if test="${not empty param.message}">
    <c:out value="${param.message}"></c:out>
 </c:if><br/>

    <form action="Controller" method="post">
       <input type="hidden" name="command" value="go_to_question_add_page"/>
       <input type="submit" value="${l_add_q}"/>
    </form>
    
    <br/>
    
    <form action="Controller" method="post">
       <input type="hidden" name="command" value="save_test"/>
       <input type="submit" value="${l_save_t}"/>
    </form>

</body>
</html>