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
        <p></p>
        <p></p>
        <p><a href="controleur?action=addChoiceToPara&idBook=${book.id}&numParagraph=${para.id}&isNew=false">Ajouter un choix lié à un paragraphe déjà existant</a></p>
        <p><a href="controleur?action=addChoiceToPara&idBook=${book.id}&numParagraph=${para.id}&isNew=true">Ajouter un nouveau choix</a></p>
    </body>
</html>
