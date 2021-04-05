<%-- 
    Document   : supprimer
    Created on : 1 avr. 2021, 12:19:50
    Author     : nicolas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8" />
        <title>Suppression d’une référence</title>
    </head>
    <body>

        <h2> Supprimer la référence </h2>
        <p>Auteur : ${ouvrage.auteur}</p>
        <p>Titre : ${ouvrage.titre}</p>
        <form action="controleur" method="post" accept-charset="UTF-8">
            <input type="hidden" name="id" value="${ouvrage.id}" />
            <input type="hidden" name="action" value="supprimer" />
            <input type="submit" value="Supprimer" />
        </form>
    </body>
</html>