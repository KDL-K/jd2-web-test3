<%@ page language="java" import="by.htp.ts.bean.User" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Welcome</title>

   <fmt:setLocale value="${sessionScope.local}"/>
   <fmt:setBundle basename="resources.local" var="loc"/>
   
   <fmt:message bundle="${loc}" key="local.lang_button_ru" var="button_ru"/>
   <fmt:message bundle="${loc}" key="local.lang_button_en" var="button_en"/>
   <fmt:message bundle="${loc}" key="local.button_create_test" var="b_createtest"/>
   <fmt:message bundle="${loc}" key="local.button_created_tests" var="b_createdtests"/>
   <fmt:message bundle="${loc}" key="local.button_test_list" var="b_testlist"/>
   <fmt:message bundle="${loc}" key="local.button_progress" var="b_progress"/>
   <fmt:message bundle="${loc}" key="local.button_log_out" var="b_logout"/>
   

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

<br/>

<c:out value="Welcome ${user.name}!"/>

   <c:if test="${user.role eq 'admin'}">
      <form action="Controller" method="post">
         <input type="hidden" name="command" value="go_to_test_creation_page"/>
         <input type="submit" value="${b_createtest}"/>
      </form><br/>
   </c:if>
   
   <c:if test="${user.role eq 'admin'}">
      <form action="Controller" method="post">
         <input type="hidden" name="command" value="created_tests"/>
         <input type="submit" value="${b_createdtests}"/>
      </form><br/>
   </c:if>
   
   <form action="Controller" method="post">
      <input type="hidden" name="command" value="test_list"/>
      <input type="submit" value="${b_testlist}"/>
   </form><br/>
   
   <form action="Controller" method="post">
      <input type="hidden" name="command" value="progress"/>
      <input type="submit" value="${b_progress}"/>
   </form><br/>
   
   <form action="Controller" method="post">
      <input type="hidden" name="command" value="logout"/>
      <input type="submit" value="${b_logout}"/>
   </form>
</body>
</html>