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
   <fmt:message bundle="${loc}" key="local.button_question_save" var="l_save_q"/>  
   
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

 <h1>Select right answers by using checkbox</h1>
 
    <p>${question.name}</p>
    
    <form action="Controller" method="post">
       <input type="hidden" name="command" value="question_save"/>
       <c:forEach items="${sessionScope.question.answerList}" var="i" >
          <input type="checkbox" name="right_answer" value="${i.id}"/>${i.name}<br/>
       </c:forEach>
       <input type="submit" value="${l_save_q}"/>
    </form>

</body>
</html>