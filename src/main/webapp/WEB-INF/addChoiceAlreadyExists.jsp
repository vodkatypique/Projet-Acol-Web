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
        <h3>A quel paragraphe voulez-vous lier ce choix ?</h3>
        
        <%boolean isAnyValid = false;%>

        <form method="post" action="controleur?action=choiceAdded" accept-charset="utf-8">
            <c:forEach items="${listPara}" var="para">
                <jsp:include page="/controleur?action=isChoiceValid&idBook=${idBook}&numParagraph=${numParagraph}&numNextParagraph=${para.id}" />
                <c:if test="${isChoiceValid}">
                    <p><label><input type="radio" name="numNextParagraph" value="${para.id}">${para.title}</label></p>
                    <% isAnyValid = true;%>
                </c:if>
            </c:forEach>
            <input type="hidden" name="isNew" value="false" >

            <c:choose>
                <c:when test="<%=isAnyValid%>">
                    <%@include file="addChoiceCommonPart.jsp" %>
                    <input type="submit" value="Valider" >
                </c:when>
                <c:otherwise>
                   <div class='red'>Erreur : tous les paragraphes possibles sont déjà parmi les choix !</div>
                </c:otherwise>
            </c:choose>
        </form>

        <button type='button' onclick="location.href='controleur?action=displayParaEdit&idBook=${idBook}&numParagraph=${numParagraph}<c:if test='${previousPara != null}'>&previousPara=${previousPara}</c:if>';">Retour</button>           
        
    </body>
</html>
