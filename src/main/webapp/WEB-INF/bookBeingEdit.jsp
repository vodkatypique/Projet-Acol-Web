<%@page import="modele.Paragraph"%>
<%@page import="modele.Book"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
	<meta charset="UTF-8"/>
	<title>Lecture du livre ${book.title}</title>
    </head>
    <body>
        <% request.setAttribute("currentPageAction", "edit"); %>
        <%@include file="co_deco.jsp" %>
        
        <a href="controleur">Retour au menu d'accueil</a>
        
        <h2> ${para.title} </h2>
        <a href="controleur?action=editParagraph&idBook=${book.id}&numParagraph=${para.id}">Modifier</a>
        <!-- TO DO afficher Modifier uniquement si le user est l'auteur -->  
        <div class='paragraphText'>${para.text}</div>
        
        <jsp:include page="/controleur?action=getChoices&idBook=${book.id}&idPara=${para.id}" />
        <c:forEach items="${choices}" var="choice"> <!-- ce sont des paragraphes -->
            <div class='choice'><a href='controleur?action=getParagraph&view=edit&idBook=${book.id}&idPara=${choice.id}'>${choice.title}</a></div>
        </c:forEach> 
    </body>
</html>
