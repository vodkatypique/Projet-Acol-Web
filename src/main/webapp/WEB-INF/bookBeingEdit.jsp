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
        
        <jsp:include page="/controleur?action=getTypeOpen&idBook=${book.id}" />
        
        <p></p>
        <div class="blue"><p>paragraphe écrit par ${para.author} ;</p>
        Cette histoire est ${typeOpen}.
        </div>
        
        <h2> ${para.title} </h2>
        
         <p><div class='paragraphText'>${para.text}</div></p>
         
        
        <jsp:include page="/controleur?action=getAllChoices&idBook=${book.id}&idPara=${para.id}" />
        <c:forEach items="${choices}" var="choice"> <!-- ce sont des paragraphes -->
            <c:choose>
                <c:when test="${choice.isValidate}">
                    <div class='choice'><a href='controleur?action=getParagraph&view=edit&idBook=${book.id}&idPara=${choice.id}'>${choice.title}</a></div>
                </c:when>
                <c:otherwise>
                    <div class='choice'>
                        <p>${choice.title} <button <c:if test="${!choice.isAccessible}"> disabled </c:if> 
                                                  onclick="location.href='controleur?action=editParagraph&idBook=${book.id}&numParagraph=${choice.id}';">
                                                  Ecrire ce choix  </button><p>
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
        
        <c:if test="${book.superAuthor.equals(utilisateur)}">
            <c:if test='${typeOpen.equals("sur invitation")}'>
                <p><a href='controleur?action=changeInvitations&idBook=${book.id}&idPara=${para.id}'>Gérer les invitations</a></p>
            </c:if>
            <% String textToDisplay = "Publier l'histoire";%>
            <c:if test="${book.isPublished == true}">
                <% textToDisplay = "Dépublier l'histoire";%>
            </c:if>
            <button type='button' onclick="location.href = 'controleur?action=publishOrUnpublish&idBook=${book.id}&idPara=${para.id}&isPublished=${book.isPublished}'"><%=textToDisplay%></button>
            <table>
                <tr>
                    <td><button type='button' onclick="location.href = 'controleur?action=deleteBook&idBook=${book.id}'">Supprimer ce livre</button></td>
                    <td><div class='orange'>Attention, cette action est irréversible !</div></td>
                </tr>
            </table>
        </c:if>
        
        <c:choose>
           <c:when test = "${pubCode == -1}">
                <div class='red'>
                    Erreur de publication : l'histoire doit contenir au moins un paragraphe qui "est une fin de l'histoire" !
                </div>
           </c:when>

            <c:when test = "${pubCode == 0}">
                <div class='green'>
                    L'histoire a bien été dépubliée !
                </div>
           </c:when>  
            
            <c:when test = "${pubCode == 1}">
                <div class='green'>
                    L'histoire a bien été publiée !
                </div>
           </c:when>  
        </c:choose>
            
    </body>
</html>
