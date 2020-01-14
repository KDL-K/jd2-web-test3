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
   
   <fmt:message bundle="${loc}" key="local.lang_question_title" var="l_qtitle"/>
   <fmt:message bundle="${loc}" key="local.lang_answers" var="l_answers"/>
   <fmt:message bundle="${loc}" key="local.button_next" var="l_next"/>  
   
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

 <h1>Add your question and answers</h1>
    <form action="Controller" method="post">
       <input type="hidden" name="command" value="go_to_mark_right_answers_page"/>
       <c:out value="${l_qtitle}"/><br/>
       <textarea name="question_title" rows="15" cols="80" required></textarea><br/>
       <c:out value="${l_answers}"/><br/>
       <input type="text" name="answers" size="50" required/><br/>
       <input type="text" name="answers" size="50" required/><br/>
       <input type="text" name="answers" size="50"/><br/>
       <input type="text" name="answers" size="50"/><br/>
       <input type="text" name="answers" size="50"/><br/>
       <input type="submit" value="${l_next}"/>
    </form>

</body>
</html>