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
                <input type="hidden" name="idBook" value="${idBook}" >
                <input type="hidden" name="numParagraph" value="${numParagraph}" >
                <input type="hidden" name="isNew" value="true" >
                <input type="text" name="choiceText" placeHolder="Entrer ici le contenu du nouveau choix" >
                <%@include file="addChoiceCommonPart.jsp" %>
                <input type="submit" value="Valider" >
        </form>
        <c:if test="${previousError != null}">
            <div class='red'>Un paragraphe du même nom ("${previousError}") existe déjà !</div>
        </c:if>
    </body>
</html>
