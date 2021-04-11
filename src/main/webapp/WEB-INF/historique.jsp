<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>


<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="#">Historique</a>
  <div class="collapse navbar-collapse" id="navbarNav">
    <ul class="navbar-nav">
      <c:forEach items="${paragraphes}" var="para">
        <li class="nav-item choice">
            <a class="nav-link" href='controleur?action=read&idBook=${idBook}&idPara=${para}'>${para}</a>
        </li>
      </c:forEach>
    </ul>
  </div>
</nav>
        <style>
            .collapse {
    display: flex!important;
}
        </style>


<c:choose>
    <c:when test='${utilisateur != null}'>
        <button onclick="location.href ='controleur?action=saveHistory&utilisateur=${utilisateur}&idBook=${idBook}&history=${history}'">Sauvegarder l'historique </button><br>
        <button onclick="location.href ='controleur?action=getHistory&utilisateur=${utilisateur}&idBook=${idBook}'">Telecharger un historique pour ce livre </button><br>
    </c:when>
    <c:otherwise>
    </c:otherwise>
</c:choose>