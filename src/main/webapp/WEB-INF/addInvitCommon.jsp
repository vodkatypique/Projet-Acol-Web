<form method="post" action="controleur?action=addUserInvit" accept-charset="utf-8">
    <p>
    <input type="hidden" name="idBook" value="${idBook}">
    <input type="hidden" name="idPara" value="${idPara}">
    <input type="text" name="userToAdd" placeholder="Login de l'utilisateur � ajouter">
    <input type="submit" value="Ajouter">
    </p>
</form>

<% String er = (String) request.getAttribute("errorInAddedUser");
boolean cond = (er != null) ;%>
<c:if test="<%=cond%>"> <!-- le dernier nom d'utilisateur rentr� n'existe pas -->
    <p class="red">Erreur : l'utilisateur <%=er%> n'existe pas !</p>
</c:if>

<% String er2 = (String) request.getAttribute("errorInAddedUser2");
boolean cond2 = (er2 != null) ;%>
<c:if test="<%=cond2%>"> <!-- le dernier nom d'utilisateur rentr� est celui de l'utilisateur connect� -->
    <p class="blue">Vous ne pouvez pas vous inviter vous-m�me, h�las ! (vous avez d�j� les droits...) </p>
</c:if>

<% String er3 = (String) request.getAttribute("errorInAddedUser3");
boolean cond3 = (er3 != null) ;%>
<c:if test="<%=cond3%>"> <!-- le dernier nom d'utilisateur rentr� est d�j� parmi ceux inscrits -->
    <p class="red">Vous avez d�j� ajout� <%=er3%> pr�c�demment !</p>
</c:if>

<% String val = (String) request.getAttribute("validated");
boolean cond4 = (val != null) ;%>
<c:if test="<%=cond4%>"> <!-- utilisateur bien ajout� -->
    <p class="green">L'utilisateur ${validated} a bien �t� ajout� !</p>
</c:if>