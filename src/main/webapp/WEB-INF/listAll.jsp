<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
	<meta charset="UTF-8"/>
	<title>Références bibliographiques</title>
        <script src="formularise.js"></script>
    </head>
    <body>
        <h2> Liste des références </h2>

        <p>
            <a href="ajouter.html">Ajouter une référence bibliographique</a>
        </p>

        <form method="post" action="controleur" accept-charset="UTF-8">
        <table>
            <tr>
                <th>Auteur</th>
                <th>Titre</th>
                <th><!-- modifier --></th>
                <th><!-- supprimer --></th>
            </tr>
            <c:forEach items="${ouvrages}" var="ouvrage">
                <tr>
                    <td>${ouvrage.auteur}</td><td>${ouvrage.titre}</td>
                    <td><a onclick="formularise(this, event, ${ouvrage.id}, 'modifier');"
                            href="controleur?action=getOuvrage&view=modifier&id=${ouvrage.id}">
			modifier
		    </a></td>
                    <td><a onclick="formularise(this, event, ${ouvrage.id}, 'supprimer');"
                            href="controleur?action=getOuvrage&view=supprimer&id=${ouvrage.id}">
			supprimer
		    </a></td>
                </tr>
            </c:forEach>
        </table>
        </form>
    </body>
</html>
