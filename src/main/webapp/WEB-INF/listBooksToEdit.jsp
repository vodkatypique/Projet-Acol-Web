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
        
        <!--On renvoie l'utilisateur au formulaire de connexion s'il n'est pas connecté :-->
         <%HttpSession sess = request.getSession(false);
         if (sess == null || sess.getAttribute("utilisateur") == null) {
            response.sendRedirect("login.html");
         }%>
        <h2> Liste des histoires que vous pouvez éditer : </h2>
        <table>
            <tr>
                <!--Image représentant l'histoire ?-->
                <th>Titre</th>
                <th>Liste des auteurs</th>
            </tr>
            <c:forEach items="${books}" var="book">
                <jsp:include page="/controleur?action=access&idBook=${book.id}" />
                <% boolean cond = (boolean) request.getAttribute("isAccess");%>
                <c:if test="<%=cond%>">
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
