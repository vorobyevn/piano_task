<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<link href="css/style.css" rel="stylesheet" type="text/css">
    <title>Поиск</title>
</head>
<body>
    <h1>Поиск на Stackoverflow</h1>
    <form action="${pageContext.request.contextPath}/search" method="post">
    	<input type="hidden" name="page" id="page" value="${page}">
        <label for="searchQuery">Поиск: </label>
        <input type="text" name="searchQuery" id="searchQuery" value="${searchQuery}" required>
        <input type="submit" name="search" value="Найти">
    </form>

	<c:if test="${message != null}">
		<p>${message}</p>
	</c:if>

    <c:if test="${questionList.items != null && questionList.items.size() > 0}">
    
	    <table class="table-fill">
	 		<caption>Результаты поиска</caption>
	   		<tr>
	    		<th>Дата</th>
			    <th>Пользователь</th>
			    <th>Вопрос</th>
		   	</tr>
		   	<c:forEach var="question" items="${questionList.items}">
			   	<tr <c:if test="${question.answerCount > 0}"> class="row-answered" </c:if> >
		   			<td class="column-date">${question.creationDateFormat}</td>
		   			<td>
		   				<a href="${question.owner.link}">
		   					<div>
		   					<img src="${question.owner.profileImage}" alt="avatar">
		   						<p>${question.owner.displayName}</p>
		   						<%-- <p>${question.owner.link}</p> --%>
		   					</div>
		   				</a>
		   			</td>
		   			<td>
		   				<a href=${question.link} target="_blank">${question.title}</a>
		   			</td>
		   		</tr>
	       </c:forEach>
	   		
	    </table>
	 	   
    </c:if>
    <c:if test="${prevUrl != null}">
    	<a href=${prevUrl} class="next-page">Предыдущая</a>
    </c:if>
        <c:if test="${nextUrl != null}">
    	<a href=${nextUrl} class="next-page">Следующая</a>
    </c:if>
    
    <c:if test="${questionList.items != null && questionList.items.size() == 0}">
    	<p>Не найдено</p>
    </c:if>
</body>
</html>
