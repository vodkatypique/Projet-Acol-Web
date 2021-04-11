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
        

        
        <table>
            <c:forEach items="<%=aau%>" var="addedUser"> <!-- On affiche les users dj ajoutés -->
                <tr>
                    <td>
                         ${addedUser} <!-- c'est son login -->
                    </td>
                    <td>
                        <%--<%boolean isNotThemselves = !(<c:out value = '${addedUser}'/>.equals(loginConnectedUser));%> --%>
                        <c:if test="${!(utilisateur.equals(addedUser))}"> <!<!-- utilisateur est un attribut de session -->
                            <form method="post" action="controleur?action=uninviteUser" accept-charset="utf-8">
                                <p>
                                    <input type="hidden" name="idBook" value="${idBook}">
                                    <input type="hidden" name="loginUser" value="${addedUser}">
                                    <input type="submit" value="supprimer">
                                </p>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
        
            
        <form method="post" action="controleur?action=uninviteEveryUser" accept-charset="utf-8">
            <p>
            <input type="hidden" name="idBook" value="${idBook}">
            <input type="submit" value="supprimer l'accès à tous les utilisateurs">
            </p>
        </form>
        ----------------------------------------------------------
        <%@include file="addInvitCommon.jsp" %>
            
        <form method="post" action="controleur?action=endInvitedAuthors" accept-charset="utf-8">
            <p>
            <input type="hidden" name="idBook" value="${idBook}">
            <input type="submit" value="Valider et passer à la suite">
            </p>
        </form>
        ----------------------------------------------------------    
        <form method="post" action="controleur?action=endInvitedAuthorsOpen" accept-charset="utf-8">
            <p>
            <input type="hidden" name="idBook" value="${idBook}">
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
    </body>
</html>

