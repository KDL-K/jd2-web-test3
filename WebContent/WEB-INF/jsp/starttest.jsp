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
   <fmt:message bundle="${loc}" key="local.lang_question_title" var="l_qtitle"/>
   <fmt:message bundle="${loc}" key="local.lang_answers" var="l_answers"/>
   <fmt:message bundle="${loc}" key="local.button_finish_test" var="b_finish"/>  
   
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

 <h1><c:out value="${test.name}"/></h1>
    <form action="Controller" method="post">
          <input type="hidden" name="command" value="finish_test"/>
          <input type="hidden" name="user_id" value="${user.id}"/>
          <input type="hidden" name="test_id" value="${test.id}"/>
          <c:set var="right_answers_in_question_count" scope="page" value="${0}"/>
      
       <c:forEach items="${test.questionList}" var="q"> 
          <c:out value="${q.name}"/><br/><br/>
          <c:out value="${l_answers}"/><br/>
         
          <c:forEach items="${q.answerList}" var="answ">
           <c:if test="${answ.isRightAnswer eq true}">
            <c:set var="right_answers_in_question_count" scope="page" value="${right_answers_in_question_count+1}"/>
           </c:if>
          </c:forEach> 
         
          <c:if test="${right_answers_in_question_count eq 1}" >
           <c:forEach items="${q.answerList}" var="a">
            <input type="radio" name="q ${q.id}" value="${a.id}"/>
            <c:out value="${a.name}" /><br/>
           </c:forEach><br/> 
          </c:if>
          
          <c:if test="${right_answers_in_question_count gt 1}" >
           <c:forEach items="${q.answerList}" var="a">
            <input type="checkbox" name="q ${q.id}" value="${a.id}"/>
            <c:out value="${a.name}" /><br/>
           </c:forEach><br/> 
          </c:if>
          
          <c:set var="right_answers_in_question_count" scope="page" value="${0}"/>
       </c:forEach> 
       
       <input type="submit" value="${b_finish}"/>
    </form>

</body>
</html>