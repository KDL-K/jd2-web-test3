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
   
   <fmt:message bundle="${loc}" key="local.button_edit_test" var="b_edit"/>
   <fmt:message bundle="${loc}" key="local.button_delete_test" var="b_delete"/>
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
   
   <form action="Controller" method="post">
      <input type="hidden" name="command" value="go_to_main_page"/>
      <input type="submit" value="${b_main}"/>
  </form><br/>
  
 <c:if test="${not empty param.message}">
    <c:out value="${param.message}"></c:out>
 </c:if>
 

 <h1>List of tests created by you</h1>
 
   <c:if test="${not empty created_tests}">
     <c:forEach items="${sessionScope.created_tests}" var="i">
       <c:out value="${i.name}"/>
       <form action="Controller" method="post">
          <input type="hidden" name="command" value="edit_test"/>
          <input type="hidden" name="test_id" value="${i.id}"/>
          <input type="submit" value="${b_edit}"/>
       </form>
       <form action="Controller" method="post">
          <input type="hidden" name="command" value="delete_test"/>
          <input type="hidden" name="test_id" value="${i.id}"/>
          <input type="submit" value="${b_delete}"/>
       </form>
      </c:forEach>
  </c:if>
</body>
</html>