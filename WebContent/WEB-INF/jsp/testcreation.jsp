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
   
   <fmt:message bundle="${loc}" key="local.lang_test_title" var="l_title"/>
   <fmt:message bundle="${loc}" key="local.lang_test_duration" var="l_duration"/>
   <fmt:message bundle="${loc}" key="local.button_create_test" var="button_createtest"/>
   
   <fmt:message bundle="${loc}" key="local.button_main_page" var="b_main"/>
   
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
 <c:if test="${not empty param.message}">
    <c:out value="${param.message}"></c:out>
 </c:if>
 
 <form action="Controller" method="post">
      <input type="hidden" name="command" value="go_to_main_page"/>
      <input type="submit" value="${b_main}"/>
  </form><br/>

 <h1>Add your New test</h1>
    <form action="Controller" method="post">
       <input type="hidden" name="command" value="save_test_name_and_duration"/>
       <c:out value="${l_title}"/><br/>
       <input type="text" name="test_title" size="80" placeholder="TestTitle" required/><br/>
       <c:out value="${l_duration}"/><br/>
       <input type="number" name="test_duration" placeholder="Duration" required/>
       <input type="submit" value="${button_createtest}"/>
    </form>

</body>
</html>