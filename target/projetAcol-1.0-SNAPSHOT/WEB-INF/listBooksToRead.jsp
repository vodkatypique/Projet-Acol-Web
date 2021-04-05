<%@page import="modele.Paragraph"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
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
        <table>
            <tr>
                <!--Image représentant l'histoire ?-->
                <th>Titre</th>
                <th>Liste des auteurs</th>
            </tr>
            <c:forEach items="${books}" var="book">
                <c:if test = "${book.isPublished}">
                    <tr>
                        <td></td>
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