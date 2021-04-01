<%-- 
    Document   : modifier
    Created on : 1 avr. 2021, 11:44:40
    Author     : nicolas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8" />
        <title>Modification d’une référence</title>
    </head>
    <body>

        <h2> Modifier une  référence </h2>

        <form action="controleur" method="post" accept-charset="UTF-8">
            <label>Auteur :</label><input type="text" name="auteur" value="${ouvrage.auteur}"/><br/>
            <label>Titre :</label><input type="text" name="titre" value="${ouvrage.titre}"/> <br/>
            <!-- Annuler est un simple lien car il ne soumet pas le formulaire -->
            <a href="controleur">Annuler</a>
            <input type="submit" value="Valider" />
            <!-- Pour indiquer au contrôleur quelle action faire, on utilise un champ caché -->
            <input type="hidden" name="id" value="${ouvrage.id}" />
            <input type="hidden" name="action" value="modifier" />

        </form>
    </body>
</html>

