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
        <h2>A quel paragraphe voulez-vous lier ce choix ?</h2>
        
        <form method="post" action="controleur?action=choiceAdded" accept-charset="utf-8">
            <input type="hidden" name="idBook" value="${idBook}" >
            <input type="hidden" name="numParagraph" value="${numParagraph}" >
            <input type="hidden" name="isNew" value="false" >
            <c:forEach items="${listPara}" var="para">
                <jsp:include page="/controleur?action=isChoiceValid&idBook=${idBook}&numParagraph=${numParagraph}&numNextParagraph=${para.id}" />
                <c:if test="${isChoiceValid}">
                    <p><label><input type="radio" name="numNextParagraph" value="${para.id}">${para.title}</label></p>
                </c:if>
            </c:forEach>
            <%@include file="addChoiceCommonPart.jsp" %>
            <input type="submit" value="Valider" >            
        </form>
    </body>
</html>
