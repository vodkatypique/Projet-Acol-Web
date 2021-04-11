<%@page import="modele.User"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styleInviteAuthors.css" />
        <title>Inviter un nouvel utilisateur à participer</title>
    </head>
    <body>
        <h1> A quel nouvel utilisateur voulez-vous donner le droit d'éditer votre histoire ? </h1>
        
        <%@include file="addInvitCommon.jsp" %>
            
        <form method="post" action="controleur?action=endInvitedAuthors" accept-charset="utf-8">
            <p>
            <input type="hidden" name="idBook" value="${idBook}">
            <input type="hidden" name="idPara" value="${idPara}">
            <input type="submit" value="Retour">
            </p>
        </form>
        ----------------------------------------------------------    
        <form method="post" action="controleur?action=endInvitedAuthorsOpen" accept-charset="utf-8">
            <p>
            <input type="hidden" name="idBook" value="${idBook}">
            <input type="hidden" name="idPara" value="${idPara}">
            <table>
                <tr>
                    <td>
                        <input type="submit" value="Rendre l'histoire ouverte"> 
                    </td>
                    <td>
                        <div class="orange">Attention cette action est irréversible !</div>
                    </td>
                </tr>
            </table>
             L'édition de l'histoire sera alors accessible à tous les utilisateurs du site.
            </p>
        </form>
        ----------------------------------------------------------    
        <h2>Voici la liste des utilisateurs disposant déjà des droits d'édition :</h2>
        
        <jsp:include page="/controleur?action=getInvitedUsers&idBook=${idBook}" />
        <% List<String> aau = (List<String>) request.getAttribute("alreadyAddedUsers");%>

        <c:forEach items="<%=aau%>" var="addedUser"> <!-- On affiche les users dj ajoutés -->
            <p>${addedUser}</p> <!-- c'est son login -->
        </c:forEach>
    </body>
</html>

