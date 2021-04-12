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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

    </head>
    <body>
        <h1>Menu d'édition </h1>
            <div class="col-md-6 offset-md-3">
            <%
            if(request.getAttribute("book") == null){
            %>
            
            <form method="post" action="controleur?action=createNewBook" accept-charset="utf-8">
                <div class="form-group">
                    <div class="d-flex justify-content-center">
                    <label for="title">Titre de l'histoire</label>
                    </div>
                    <input type="text" class="form-control" name="title" id="title" placeholder="Titre" required>
                    
                </div>
                    <div class="d-flex justify-content-center py-3">
                     <button type="submit" class="btn btn-primary">Créer</button>
                    </div>
              
               
            </form>
                
            
            <c:if test="${errorTitle != null}">
                <div class="alert alert-danger" role="alert">
                    Erreur : le nom de livre ${errorTitle} est déjà pris !
                </div>
            </c:if>
            <% } else { 
            %>  
            <h2>Livre : ${book.title}</h2>
            
            <p class="font-weight-light">Edition de paragraphe</p>
            
            <form method="post" 
                  <% if(request.getAttribute("paragraph")!=null) { %>
                               action="controleur?action=postEditParagraph"
                  <%} else {%> action="controleur?action=createParagraph" <%}%>
                  accept-charset="utf-8">
                <input type="hidden" name="idBook" value="${book.id}" >
                
                <% if(request.getAttribute("paragraph")!=null) { %>
                  <input type="hidden" name="numParagraph" value="${paragraph.id}" >
                <% } %>

                
                <div class="form-group">
                <input class="form-control" type="text" name="paragraphTitle" 

                <% if(request.getAttribute("previousPara")!=null) { %>
                  <input type="hidden" name="previousPara" value="${previousPara}" >
                <% } %>
                
                <input class="form-control" type="text" name="paragraphTitle" 

                       <% if(request.getAttribute("paragraph")!=null) { %>
                            value="${paragraph.title}"
                       <%} else { %>
                       value="Titre du paragraphe" <%}%> required>
                </div>
                
                <div class="form-group">
                <textarea class="form-control" name="paragraphContent" style="resize: none; width: 600px; height: 300px;" 
                          required>
                    <% if(request.getAttribute("paragraph")!=null) { %>${paragraph.text}<%}%>
                </textarea>
                
                </div>
                          
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
                           <button class="btn btn-info" onclick="addChoice(this)" form="">Ajouter</button>
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
                <button class="btn btn-success" type="submit" value="Valider le paragraphe">Valider le paragraphe</button>
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
                    <button class="btn btn-warning" onclick="location.href = 'controleur?action=deleteParagraph&idB=${book.id}&idP=${paragraph.id}&title=${paragraph.title}'"> supprimer ce paragraphe </button>
                    <p class='red'>${errorDelete}</p>
                 </c:if>
                  <%} %>
           
            
            
            <%}
        %>
            
            </div>  
    </body>
</html>

