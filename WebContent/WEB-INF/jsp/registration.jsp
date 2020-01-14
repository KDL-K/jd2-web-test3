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
    <fmt:setBundle basename="resources.local" var="loc"/>
    
    <fmt:message bundle="${loc}" key="local.lang_button_ru" var="button_ru"/>
    <fmt:message bundle="${loc}" key="local.lang_button_en" var="button_en"/>
    
    <fmt:message bundle="${loc}" key="local.lang_name" var="l_name"/>
    <fmt:message bundle="${loc}" key="local.lang_surname" var="l_surname"/>
    <fmt:message bundle="${loc}" key="local.lang_email" var="l_email"/>
    <fmt:message bundle="${loc}" key="local.lang_sex" var="l_sex"/>
    <fmt:message bundle="${loc}" key="local.lang_age" var="l_age"/>
    <fmt:message bundle="${loc}" key="local.lang_role" var="l_role"/>
    <fmt:message bundle="${loc}" key="local.lang_login" var="l_login"/>
    <fmt:message bundle="${loc}" key="local.lang_password" var="l_password"/>
    <fmt:message bundle="${loc}" key="local.button_registration" var="button_registration"/>
    

</head>
<body>

    <form action="Controller" method="post">
      <input type="hidden" name="command" value="change_language"/>
      <input type="hidden" name="lang" value="ru"/>
      <input type="submit" value="${button_ru}" />
    </form>
    
    <form action="Controller" method="post">
      <input type="hidden" name="command" value="change_language"/>
      <input type="hidden" name="lang" value="en"/>
      <input type="submit" value="${button_en}"/>
    </form>

    <c:if test="${not empty param.message }">
        <p style="color:red"><c:out value="${param.message }"></c:out></p>
    </c:if>
    <c:if test="${not empty messageAttr }">
        <p style="color:red"><c:out value="${messageAttr}"></c:out></p>
    </c:if>
    
    <form action="Controller" method="post">
      <input type="hidden" name="command" value="registration"/>
      <c:out value="${l_name}"/> 
      <input type="text" name="name" value="" required/><br/>
      <c:out value="${l_surname}"/> 
      <input type="text" name="surname" value="" required/><br/>
      <c:out value="${l_email}"/>
      <input type="email" name="email" value="" required/><br/>
      <c:out value="${l_sex}"/> 
           <input type=radio name="sex" value="female"/>Female
           <input type=radio name="sex" value="male" checked/>Male<br/>
      <c:out value="${l_age}"/> 
      <input type="number" name="age" min=1 value="" required/><br/>
      <c:out value="${l_role}"/> 
            <select name="role">
                <option>user</option>
                <option>admin</option>
            </select>  <br/>
      <c:out value="${l_login}"/>
      <input type="text" name="login" value="" required/><br/>
      <c:out value="${l_password}"/>
      <input type="password" name="password" value="" required/><br/>
      
      <input type="submit" value="${button_registration}"/>
    </form>

</body>
</html>