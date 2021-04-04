<%@page import="modele.Paragraph"%>
<%@page import="modele.Book"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
	<meta charset="UTF-8"/>
        <% Book book = (Book) request.getAttribute("bookBeingRead");%>
        <% Paragraph para = (Paragraph) request.getAttribute("paragraphBeingRead");%> 
	<title>Lecture du livre <%= book%></title>
    </head>
    <body>
        <!-- Il va falloir donner l'id du livre qu'on est en train de lire => TO DO-->
        <% request.setAttribute("currentPageAction", "read"); %>
        <%@include file="co_deco.jsp" %>
        
        <a href="controleur">Retour au menu d'accueil</a>
        
        <h2> <%= para.getTitle() %> </h2>
        <div class='paragraphText'><%= para.getText() %> </div>
        <jsp:include page="/controleur?action=getChoices&idBook=<%= book.getId()%>&idPara=<%= para.getId()%>" />
        <c:forEach items="${choices}" var="choice"> <!-- ce sont des paragraphes -->
            <div class='choice'><a href='controleur?action=read&&book=<%= book.getId()%>&para=${choice.id}'>${choice.title}</a></div>
        </c:forEach>
    </body>
</html>
