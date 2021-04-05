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
        <% request.setAttribute("idBook", book.getId()); %>
        <% request.setAttribute("idPara", para.getId()); %>
        <% request.setAttribute("currentPageAction", "read"); %>
        <%@include file="co_deco.jsp" %>
        <%@include file="historique.jsp" %>
        
        <a href="controleur">Retour au menu d'accueil</a>
        
        <h2> <%= para.getTitle() %> </h2>
        <div class='paragraphText'><%= para.getText() %> </div>
        <jsp:include page="/controleur?action=getChoices&idBook=<%= book.getId()%>&idPara=<%= para.getId()%>" />
        <c:forEach items="${choices}" var="choice"> <!-- ce sont des paragraphes -->
            <div class='choice'><a href='controleur?action=read&idBook=<%= book.getId()%>&idPara=${choice.id}'>${choice.title}</a></div>
        </c:forEach>
    </body>
</html>
