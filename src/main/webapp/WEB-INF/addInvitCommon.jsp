<form method="post" action="controleur?action=addUserInvit" accept-charset="utf-8">
    <p>
    <input type="hidden" name="idBook" value="${idBook}">
    <input type="hidden" name="idPara" value="${idPara}">
    <input type="text" name="userToAdd" placeholder="Login de l'utilisateur à ajouter">
    <input type="submit" value="Ajouter">
    </p>
</form>

<% String er = (String) request.getAttribute("errorInAddedUser");
boolean cond = (er != null) ;%>
<c:if test="<%=cond%>"> <!-- le dernier nom d'utilisateur rentré n'existe pas -->
    <p class="red">Erreur : l'utilisateur <%=er%> n'existe pas !</p>
</c:if>

<% String er2 = (String) request.getAttribute("errorInAddedUser2");
boolean cond2 = (er2 != null) ;%>
<c:if test="<%=cond2%>"> <!-- le dernier nom d'utilisateur rentré est celui de l'utilisateur connecté -->
    <p class="blue">Vous ne pouvez pas vous inviter vous-même, hélas ! (vous avez déjà les droits...) </p>
</c:if>

<% String er3 = (String) request.getAttribute("errorInAddedUser3");
boolean cond3 = (er3 != null) ;%>
<c:if test="<%=cond3%>"> <!-- le dernier nom d'utilisateur rentré est déjà parmi ceux inscrits -->
    <p class="red">Vous avez déjà ajouté <%=er3%> précédemment !</p>
</c:if>

<% String val = (String) request.getAttribute("validated");
boolean cond4 = (val != null) ;%>
<c:if test="<%=cond4%>"> <!-- utilisateur bien ajouté -->
    <p class="green">L'utilisateur ${validated} a bien été ajouté !</p>
</c:if>