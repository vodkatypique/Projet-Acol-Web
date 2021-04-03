<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
	<meta charset="UTF-8"/>
	<title>Editer une histoire</title>
    </head>
    <body>
        <p> <a href="register.html">Se connecter</a></p>
        <p> <a href="logout.html">déconnexion</a></p>

        <h2> Liste des histoires que vous pouvez éditer : </h2>
        <form method="post" action="controleur" accept-charset="UTF-8">
        <table>
            <tr>
                <!--Image représentant l'histoire ?-->
                <th>Titre</th>
                <th>Liste des auteurs</th>
            </tr>
            <c:forEach items="${books}" var="book">
                <!--Une histoire peut être ouverte ou sur invitation. 
                Si elle est ouverte, tous les utilisateurs en-registrés du site 
                peuvent participer. Sinon, l’auteur du paragraphe initial invite l
                es utilisateursqu’il veut et seuls ces utilisateurs ont accès à l’histoire en mode écritur-->
                <jsp:include page="controleur.jsp">
                    <jsp:param name="action" value="access" />
                    <jsp:param name="idBook" value="${book.id}" />
                    <jsp:param name="idUser" value="${user.id}" />
                </jsp:include> 
                <c:if test = '${request}.getAttribute("isAccess") === true'>
                    <tr>
                        <td></td>
                        <td>
                            <a href="controleur?action=getBook&view=edit&id=${book.id}">
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
