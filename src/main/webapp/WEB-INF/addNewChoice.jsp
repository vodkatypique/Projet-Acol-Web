<%@page import="modele.Paragraph"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styleInviteAuthors.css" />
        <script src="ableDisableHandler.js"></script>
        <title>Ajouter un choix</title>
    </head>
    <body>
        <h1>Ajouter un choix</h1>
        <form method="post" action="controleur?action=choiceAdded" accept-charset="utf-8">
                <c:choose>
                    <c:when test="${previousError != null}">
                        <input type="hidden" name="confirmation" value="true" >
                        <input type="text" name="choiceText" value="${previousError}" >
                    </c:when>
                    <c:otherwise>
                       <input type="text" name="choiceText" placeHolder="Entrer ici le contenu du nouveau choix" >
                    </c:otherwise>
                </c:choose>
     
                <%@include file="addChoiceCommonPart.jsp" %>
                <input type="hidden" name="isNew" value="true" >
                <c:if test="${previousError != null}">
                    <div class='orange'>/!\ Un paragraphe du même nom ("${previousError}") existe déjà... Êtes-vous sûr de vouloir créer ce choix ?</div>
                </c:if>
                <input type="submit" value="Valider" > <button type='button' onclick="location.href='controleur?action=displayParaEdit&idBook=${idBook}&numParagraph=${numParagraph}<c:if test='${previousPara != null}'>&previousPara=${previousPara}</c:if>';">Retour</button>
        </form>
    </body>
</html>
