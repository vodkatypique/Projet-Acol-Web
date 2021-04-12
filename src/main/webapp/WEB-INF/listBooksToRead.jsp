<%@page import="modele.Paragraph"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

<html>
    <head>
	<meta charset="UTF-8"/>
	<title>Menu des histoires</title>
    </head>
    <body>
        <% request.setAttribute("currentPageAction", "accueil"); %>
        <%@include file="co_deco.jsp" %>
        <a href="controleur?action=edition">vers édition</a>
        <!--<a href='WEB-INF/listBooksToEdit.jsp'>accéder à l'édition</a>-->
        
        <h2> Liste des histoires disponibles : </h2>
        <table class="table">
            <tr>
                <!--Image représentant l'histoire ?-->
                <th scope="col">Titre</th>
                <th scope="col">Liste des auteurs</th>
            </tr>
            <c:forEach items="${books}" var="book">
                <c:if test = "${book.isPublished}">
                    <tr>
                        <td>
                            <jsp:include page="/controleur?action=getParagraph&view=listBooksToRead&idBook=${book.id}&idPara=1" />
                            <a href='controleur?action=read&idBook=${book.id}&idPara=${paragraph.id}'>
                                ${book.title}
                            </a>
                        </td>
                        <td>
                            <jsp:include page="/controleur?action=authors&idBook=${book.id}" />
                            <c:forEach items="${authors}" var="author">
                                ${author} ; 
                            </c:forEach>
                        </td>
                    </tr>
               </c:if>
            </c:forEach>
        </table>
        
    </body>
</html>
