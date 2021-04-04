<%-- 
    Document   : writeBook
    Created on : 4 avr. 2021, 16:09:50
    Author     : nicolas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ecriture d'un livre</title>
        <script src="paragraphManager.js"></script>
    </head>
    <body>
        <h1>Création d'un nouveau livre </h1>

        <%
            if(request.getAttribute("book") == null){
            %>
            <form method="post" action="controleur?action=createNewBook" accept-charset="utf-8">
                Titre de l'histoire :  <input type="text" name="title" required/>
            <input type="submit" value="Créer" >
            </form>
            <% } else { 
            %>  
            <h2>Livre : ${book.title}</h2>
            Premier paragraphe
            <p>
            <form method="post" action="controleur?action=createParagraph" accept-charset="utf-8">
                <input type="hidden" name="idBook" value="${book.id}" >
                <p>
                <input type="text" name="paragraphTitle" value="Titre du paragraphe" required>
                </p>
                <textarea name="paragraphContent" style="resize: none; width: 600px; height: 300px;" required></textarea>
                <table>
                    <tr>
                        <th>Choix</th>
                        <th></th>
                    </tr>
                    <tr>
                        <th>
                            <input type="text" name="choice" required>  <!-- TO DO retirer les choix (avec js) quand la case isEnd est cochée -->
                        </th>
                        <th>
                        </th>
                    </tr>
                    <tr>
                        <th>
                           <button onclick="paragraphManager(this)" form="">Ajouter</button>
                       </th>
                       <th></th>
                    </tr>
                </table>
                <p>
                <input type="submit" value="Valider le paragraphe">
                </p>
            </form>
            </p>
            
            
            <%}
        %>
            
        
    </body>
</html>

