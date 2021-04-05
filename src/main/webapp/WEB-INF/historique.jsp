 <c:forEach items="${paragraphes}" var="para">
    <div class='choice'><a href='controleur?action=read&idBook=${idBook}&idPara=${para}'>${para}</a></div>
</c:forEach>