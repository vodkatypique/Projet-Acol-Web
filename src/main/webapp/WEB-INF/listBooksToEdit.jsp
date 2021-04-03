<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
	<meta charset="UTF-8"/>
	<title>Editer une histoire</title>
    </head>
    <body>
        <%@include file="co_deco.jsp" %>
        
        <h2> Liste des histoires que vous pouvez éditer : </h2>
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
                <jsp:include page="/controleur?action=access&idBook=${book.id}&idUser=1" />
                <%boolean cond = (boolean) request.getAttribute("isAccess");%>
                <%= cond %>
                <c:if test = "cond === true"> <!-- ne fonctionne pas pour une raison obscure (j'ai tout essayé...) -->
                    <tr>
                        <td></td>
                        <td>
                            <a href="controleur?action=getBook&view=edit&id=${book.id}">
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
