<c:forEach items="${paragraphes}" var="para">
    <div class='choice'><a href='controleur?action=read&idBook=${idBook}&idPara=${para}'>${para}</a></div>
</c:forEach>

<c:choose>
    <c:when test='${utilisateur != null}'>
        <div class='choice'><a href="controleur?action=saveHistory&utilisateur=${utilisateur}&idBook=${idBook}&history=${history}">Sauvegarder l'historique</a></div>
        <div class='choice'><a href="controleur?action=getHistory&utilisateur=${utilisateur}&idBook=${idBook}">Telecharger un historique pour ce livre</a></div>
    </c:when>
    <c:otherwise>
    </c:otherwise>
</c:choose>