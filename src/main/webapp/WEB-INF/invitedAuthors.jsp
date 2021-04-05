<%@page import="modele.User"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styleInviteAuthors.css" />
        <title>Invitations à l'écriture de l'histoire</title>
    </head>
    <body>
        <h1> A quels utilisateurs voulez-vous donner le droit d'éditer votre histoire ? </h1>
        
        <jsp:include page="/controleur?action=getInvitedUsers&idBook=${idBook}" />
        <% List<String> aau = (List<String>) request.getAttribute("alreadyAddedUsers");%>
        <c:forEach items="<%=aau%>" var="addedUser"> <!<!-- On affiche les users dj ajoutés -->
            <p>${addedUser}</p> <!-- c'est son login -->
        </c:forEach>    
    
        <form method="post" action="controleur?action=addUserInvit" accept-charset="utf-8">
            <p>
            <input type="hidden" name="idBook" value="${idBook}">
            <input type="text" name="userToAdd" placeholder="Login de l'utilisateur à ajouter">
            <input type="submit" value="Ajouter">
            </p>
        </form>
        
        <% String er = (String) request.getAttribute("errorInAddedUser");
        boolean cond = (er != null) ;%>
        <c:if test="<%=cond%>"> <!-- le dernier nom d'utilisateur rentré n'existe pas -->
            <p class="red">Erreur : l'utilisateur <%=er%> n'existe pas !</p>
        </c:if>
            
        <% String er2 = (String) request.getAttribute("errorInAddedUser2");
        boolean cond2 = (er2 != null) ;%>
        <c:if test="<%=cond2%>"> <!-- le dernier nom d'utilisateur rentré est celui de l'utilisateur connecté -->
            <p class="blue">Ne vous inquiétez pas, vous aurez bien-sûr accès à votre propre histoire ;-)</p>
        </c:if>
            
        <% String er3 = (String) request.getAttribute("errorInAddedUser3");
        boolean cond3 = (er3 != null) ;%>
        <c:if test="<%=cond3%>"> <!-- le dernier nom d'utilisateur rentré est déjà parmi ceux inscrits -->
            <p class="red">Vous avez déjà ajouté <%=er3%> précédemment !</p>
        </c:if>
            
        <form method="post" action="controleur?action=endInvitedAuthors" accept-charset="utf-8">
            <p>
            <input type="hidden" name="idBook" value="${idBook}">
            <input type="submit" value="Valider et passer à la suite">
            </p>
        </form>
            
        <form method="post" action="controleur?action=endInvitedAuthorsOpen" accept-charset="utf-8">
            <p>
            <input type="hidden" name="idBook" value="${idBook}">
            <input type="submit" value="Rendre l'édition de l'histoire accessible à tous les utilisateurs du site et passer à la suite">
            </p>
        </form>
    </body>
</html>

