<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="modele.Paragraph"%>
<%@page import="modele.Book"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
	<meta charset="UTF-8"/>
        <link rel="stylesheet" type="text/css" href="styleInviteAuthors.css" />
        <% Book book = (Book) request.getAttribute("bookBeingRead");%>
        <% List<Paragraph> para = (ArrayList) request.getAttribute("paragraphsBeingRead");%> 
        <% request.setAttribute("para", request.getAttribute("para"));%> 
	<title>Lecture du livre <%= book%></title>
    </head>
    <body>
        
        <% request.setAttribute("currentPageAction", "read"); %>
        
        <%@include file="co_deco.jsp" %>
        <a href="controleur">Retour au menu d'accueil</a><!-- comment -->
        <hr>
        <%@include file="historique.jsp" %>
        
        
        <jsp:include page="/controleur?action=authors&idBook=<%= book.getId()%>" />
        <p></p>
        <div class="blue">Cette histoire a été écrite par 
            <c:forEach items="${authors}" var="author">
                ${author} ; 
            </c:forEach>
        </div>      
        
        <h2> <%= para.get(0).getTitle() %> </h2>
        <div class='paragraphText'><%=para.get(0).getText()%></div>
        
        <c:choose>
            <c:when test='${paragraphsBeingRead.size() > 1}'>
                <c:forEach var="par" items="${paragraphsBeingRead.subList(1, paragraphsBeingRead.size())}">
                    <div class='paragraphText'>${par.title}</div>
                    <div class='paragraphText'>${par.text}</div>
                </c:forEach>
            </c:when>
        </c:choose>
                    
        <jsp:include page="/controleur?action=getChoicesRead&idBook=<%= book.getId()%>&idPara=${idLastPara}&paragraphes=${paragraphes}" />
        
        <hr>
        <div class="list-group list-group-flush">

        <c:choose>
            <c:when test='${choices != null}'>
                <c:forEach items="${choices}" var="choice"> <!-- ce sont des paragraphes -->
                        
                    <a class="list-group-item list-group-item-action" href='controleur?action=read&idBook=<%= book.getId()%>&idPara=${choice.id}'>${choice.title}</a></div>
                </c:forEach>
            </c:when>
        </c:choose>
    </body>
</html>
