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
   <fmt:message bundle="${loc}" key="local.lang_test_duration" var="l_duration"/>
   <fmt:message bundle="${loc}" key="local.button_save_test" var="b_save"/>  
   
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

 <h1>Add your question and answers</h1>
    <form action="Controller" method="post">
          <input type="hidden" name="command" value="test_edition"/>
          <c:out value="${l_title}"/><br/>
          <input type="text" name="test_title" size="80" value="${test.name}" required/><br/>
          <c:out value="${l_duration}"/><br/>
          <input type="number" name="test_duration" value="${test.testDuration}" required/><br/>
      
       <c:forEach items="${test.questionList}" var="q"> 
          <c:out value="${l_qtitle}"/><br/>
          <textarea name="q ${q.id}" rows=15 cols=80 required>${q.name}
          </textarea><br/>
          <c:out value="${l_answers}"/><br/>
         <c:forEach items="${q.answerList}" var="a">
          
          <c:if test="${a.isRightAnswer eq true}">
             <input type="checkbox" name="r" value="${a.id}" checked/>
          </c:if>
          <c:if test="${a.isRightAnswer eq false}">
             <input type="checkbox" name="r" value="${a.id}"/>
          </c:if>
          <input type="text" name="a ${a.id}" size=50 value="${a.name}" /><br/>
          
         </c:forEach> 
       </c:forEach> 
       
       <input type="submit" value="${b_save}"/>
    </form>

</body>
</html>