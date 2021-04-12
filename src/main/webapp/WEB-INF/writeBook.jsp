<%-- 
    Document   : writeBook
    Created on : 4 avr. 2021, 16:09:50
    Author     : nicolas
--%>

<%@page import="java.util.List"%>
<%@page import="dao.ChoiceDAO"%>
<%@page import="modele.Paragraph"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<select name="conditionalToWhich" disabled="true" id="selector" style="visibility:hidden;"> 
                <c:forEach items="${listPara}" var="para">
                    <c:if test="${para.id != numParagraph}"> <!-- pas conditionnel à lui-même, aucun sens -->
                        <option value="${para.id}">${para.title}</option>
                    </c:if>
                </c:forEach>
</select>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styleInviteAuthors.css" />
        <title>Ecriture d'un livre</title>
        <script src="paragraphManager.js"></script>
    </head>
    <body>
        <h1>Menu d'édition </h1>

            <%
            if(request.getAttribute("book") == null){
            %>
            <form method="post" action="controleur?action=createNewBook" accept-charset="utf-8">
                Titre de l'histoire :  <input type="text" name="title" required/>
            <input type="submit" value="Créer" >
            </form>
            <c:if test="${errorTitle != null}">
                <p class="red">Erreur : le nom de livre ${errorTitle} est déjà pris !</p>
            </c:if>
            <% } else { 
            %>  
            <h2>Livre : ${book.title}</h2>
            Edition de paragraphe
            <p>
            <form method="post" 
                  <% if(request.getAttribute("paragraph")!=null) { %>
                               action="controleur?action=postEditParagraph"
                  <%} else {%> action="controleur?action=createParagraph" <%}%>
                  accept-charset="utf-8">
                <input type="hidden" name="idBook" value="${book.id}" >
                <% if(request.getAttribute("paragraph")!=null) { %>
                  <input type="hidden" name="numParagraph" value="${paragraph.id}" >
                <% } %>
                <% if(request.getAttribute("previousPara")!=null) { %>
                  <input type="hidden" name="previousPara" value="${previousPara}" >
                <% } %>
                <p>
                <input type="text" name="paragraphTitle" 
                       <% if(request.getAttribute("paragraph")!=null) { %>
                            value="${paragraph.title}"
                       <%} else { %>
                       value="Titre du paragraphe" <%}%> required>
                </p>
                <textarea name="paragraphContent" style="resize: none; width: 600px; height: 300px;" 
                          required><% if(request.getAttribute("paragraph")!=null) { %>${paragraph.text}<%}%></textarea>
                          <% if(request.getAttribute("paragraph")==null || !((Paragraph) request.getAttribute("paragraph")).getIsValidate()) { %>
                <table>
                    <tr>
                        <th></th>
                        <th>Choix</th>
                    </tr>
                    <tr>
                        <th>
                            <label>Paragraphe déjà existant <input type="checkbox" name="isAlreadyExist"
                                                                   value = "true" onclick="changeInputChoice(this)"/></label>
                                <input type="hidden" name="isAlreadyExist" value="false"/>
                        </th>
                        <th>
                            <input type="text" name="choice" required> 
                        </th>
                            <th><input type="hidden" name="condition" value="-1"></th>
                    </tr>
                    <tr>
                        <th>
                           <button onclick="addChoice(this)" form="">Ajouter</button>
                       </th>
                    </tr>
                </table>
                <% } %>
                <input type="checkbox" id="isEnd" name="isEnd" value="isEnd" onclick="blockChoice(this)" 
                       <c:if test="${paragraph.isEnd}"> checked </c:if> >
                  <label for="isEnd">est une fin de l'histoire</label>
                  <c:if test="${errorIncond != null}">
                        <p class="red">Erreur : Ce paragraphe ne peut pas ne pas être une fin de l'histoire car il est rédigé et ne propose aucun choix inconditionnel</p>
                  </c:if>
                <p>
                    <input type="hidden" name="isNewParagraph" value="${!paragraph.isValidate}"/>
                <input type="submit" value="Valider le paragraphe">
                </p>
            </form>
                  
                <% if(request.getAttribute("paragraph") != null) { %>
                <c:if test="${!paragraph.isValidate}">
                       <button onclick="location.href = 'controleur?action=cancelEditParagraph&idB=${book.id}&idP=${paragraph.id}'"> Annuler l'écriture de ce paragraphe </button>
                </c:if>
 
                 <jsp:include page="/controleur?action=getAllChoices&idBook=${book.id}&idPara=${paragraph.id}" />
                 <%     /* On peut supprimer uniquement si :
                        - il n'y a aucun choix après ce paragraphe
                        - Tous les choix suivant ne sont pas validé ou actuellement édité par quelqu'un
                            */
                     List<Paragraph> choices = (List<Paragraph>) request.getAttribute("choices");
                    boolean hasNoValidateChoice = true;
                    for(Paragraph choice : choices){
                        if(!choice.getIsAccessible() || choice.getIsValidate()){
                            hasNoValidateChoice = false;
                            break;
                        }
                    }%>
                 <c:if test="<%=hasNoValidateChoice%>">
                    <button onclick="location.href = 'controleur?action=deleteParagraph&idB=${book.id}&idP=${paragraph.id}&title=${paragraph.title}'"> supprimer ce paragraphe </button>
                    <p class='red'>${errorDelete}</p>
                 </c:if>
                  <%} %>
            </p>
            
            
            <%}
        %>
            
        
    </body>
</html>

