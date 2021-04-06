<%@page import="modele.Paragraph"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styleInviteAuthors.css" />
        <title>Ajouter un choix</title>
    </head>
    <body>
        <h1>Ajouter un choix</h1>
        <h2>A quel paragraphe voulez-vous lier ce choix ?</h2>
        <jsp:include page="/controleur?action=getListPara&idBook=${idBook}" />
        <%List<Paragraph> listPara = (List<Paragraph>) request.getAttribute("listPara");%>
        
        <form method="post" action="controleur?action=choiceAdded" accept-charset="utf-8">
            <input type="hidden" name="idBook" value="${idBook}" >
            <input type="hidden" name="numParagraph" value="${numParagraph}" >
            <input type="hidden" name="isNew" value="false" >
            <c:forEach items="${listPara}" var="para">
                <c:if test="${para.id != numParagraph}"> <!-- TODO : rajouter dans le test qu'il faut que para pas dans listPara -->
                    <p><label><input type="radio" name="numNextParagraph" value="${para.id}">${para.title}</label></p>
                </c:if>
            </c:forEach>
            <input type="submit" value="Valider" >
            <!-- TODO : cas particulier du choix conditionnel -->
        </form>
    </body>
</html>
