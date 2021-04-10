<%@page import="modele.Paragraph"%>
<%@page import="modele.Book"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
	<meta charset="UTF-8"/>
        <link rel="stylesheet" type="text/css" href="styleInviteAuthors.css" />
	<title>Lecture du livre ${book.title}</title>
    </head>
    <body>
        <%@include file="co_deco.jsp" %>
        
        <a href="controleur">Retour au menu d'accueil</a>
        
        <h2> ${para.title} </h2>
        <p>paragraphe écrit par ${para.author}.</p>

         <p><div class='paragraphText'>${para.text}</div></p>
         
        
        <jsp:include page="/controleur?action=getChoices&idBook=${book.id}&idPara=${para.id}" />
        <c:forEach items="${choices}" var="choice"> <!-- ce sont des paragraphes -->
            <c:choose>
                <c:when test="${choice.isValidate}">
                    <div class='choice'><a href='controleur?action=getParagraph&view=edit&idBook=${book.id}&idPara=${choice.id}'>${choice.title}</a></div>
                </c:when>
                <c:otherwise>
                    <div class='choice'>
                        <p>${choice.title} <button <c:if test="${choice.isAccessible}">disabled</c:if> 
                                                  onclick="location.href='controleur?action=editParagraph&idBook=${book.id}&numParagraph=${choice.id}';">
                                                  Ecrire ce choix</button><p>
                    </div>                    
                </c:otherwise>
            </c:choose>
        </c:forEach> 
        <p></p>
        <p>---------------</p> <!-- faire du CSS plus propre -->
        <p><c:if test="${para.author.equals(utilisateur)}">
                <a href="controleur?action=editParagraph&idBook=${book.id}&numParagraph=${para.id}">Modifier le contenu du paragraphe</a>
        </c:if></p>
        <p><a href="controleur?action=addChoiceToPara&idBook=${book.id}&numParagraph=${para.id}&isNew=false">Ajouter un choix lié à un paragraphe déjà existant</a></p>
        <p><a href="controleur?action=addChoiceToPara&idBook=${book.id}&numParagraph=${para.id}&isNew=true">Ajouter un nouveau choix</a></p
        <p>---------------</p> <!-- faire du CSS plus propre -->
        
        <!-- test TO DO : si l'utilisateur est l'auteur du paragraphe initial : 
        Changer les autorisations
        Publier l'histoire (si pas publiée ; grisé si pas de isEnd ou alors renvoie une erreur si pas de isEnd) / dépublier
        -->
        <c:if test="${book.superAuthor == utilisateur.id}">
            <a href='controleur?action=changeInvitations&idBook=${book.id}'></a>
            <% String textToDisplay = "Publier l'histoire";%>
            <c:if test="${book.isPublished == true}">
                <% textToDisplay = "Dépublier l'histoire";%>
            </c:if>
            <button type='button' onclick="location.href = 'controleur?action=publishOrUnpublish&idBook=${book.id}&idPara=${para.id}$isPublished=${book.isPublished}"><%=textToDisplay%></button>
        </c:if></p>
        
        <c:choose>
           <c:when test = "${pubCode == -1}">
                <div class='red'>
                    Erreur de publication : l'histoire doit contenir au moins un paragraphe qui "est une fin de l'histoire" !
                </div>
           </c:when>

            <c:when test = "${pubCode == 0}"> <!-- Pas otherwise car isPubError peut aussi être null -->
                <div class='green'>
                    L'histoire a bien été dépubliée !
                </div>
           </c:when>  
            
            <c:when test = "${pubCode == 1}"> <!-- Pas otherwise car isPubError peut aussi être null -->
                <div class='green'>
                    L'histoire a bien été publiée !
                </div>
           </c:when>  
        </c:choose>
        
    </body>
</html>
