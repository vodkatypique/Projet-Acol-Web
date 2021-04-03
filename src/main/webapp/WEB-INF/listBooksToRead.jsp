<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
	<meta charset="UTF-8"/>
	<title>Menu des histoires</title>
    </head>
    <body>
        <form method="post" action="controleur" accept-charset="UTF-8">
            <c:choose>
                <c:when test='${request.session}.getAttribute("utilisateur") == null'> <p> <a href="register.html">Se connecter</a></p></c:when>
                <c:otherwise><p> <a href="logout.html">déconnexion</a></p></c:otherwise>
            </c:choose>
        </form>       

        <h2> Liste des histoires disponibles : </h2>
        <form method="post" action="controleur" accept-charset="UTF-8">
        <table>
            <tr>
                <!--Image représentant l'histoire ?-->
                <th>Titre</th>
                <th>Liste des auteurs</th>
            </tr>
            <c:forEach items="${books}" var="book">
                <c:if test = "${book.isPublished} === true">
                    <tr>
                        <td></td>
                        <td>
                            <a href="controleur?action=getBook&view=read&id=${book.id}">
                            ${book.title}
                            </a>
                        </td>
                        <td>
                            Liste des auteurs, TO DO
                        </td>
                    </tr>
               </c:if>
            </c:forEach>
        </table>
        </form>
    </body>
</html>
