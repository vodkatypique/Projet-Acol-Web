 <c:forEach items="${paragraphes}" var="para">
    <div class='choice'><a href='controleur?action=read&idBook=${idBook}&idPara=${para}'>${para}</a></div>
</c:forEach>

<c:choose>
    <c:when test='${utilisateur != null}'>
        <div class='choice'><a href="controleur?action=addHistory&idBook=${idBook}&idUser=${utilisateur}&history=${paragraphes}">Sauvegarder l'historique</a></div>
        <div class='choice'><a href="controleur?action=getHistory&idBook=${idBook}&idUser=${utilisateur}">Telecharger un historique pour ce livre</a></div>
    </c:when>
    <c:otherwise>
    </c:otherwise>
</c:choose>