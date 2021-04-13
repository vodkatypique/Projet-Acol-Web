<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

<c:choose>
                <c:when test='${utilisateur == null}'>
                    
<div class="dropdown">
  <button
    class="btn btn-primary dropdown-toggle"
    type="button"
    data-mdb-toggle="dropdown"
    aria-expanded="false"
    onclick="changeShow()"
  >
    Connexion/Inscription
  </button>
    
    <script> 
        function changeShow() {
    var a = document.getElementsByClassName('dropdown-menu');
    
    a = a[0];
    
    a.classList.toggle('show');
    
    
}
    </script>
  <div class="dropdown-menu" css>
    <form class="px-4 py-3" method="post" accept-charset="UTF-8">
        
            <div class="form-outline mb-4">
              <input type="text" name="login" class="form-control" />
              <label class="form-label" for="login">login</label>
            </div>
            
            <!-- Password input -->
            <div class="form-outline mb-4">
              <input type="password" name="password" class="form-control" />
              <label class="form-label" for="password">Password</label>
            </div>
                    
                        <%String cP = (String) request.getAttribute("currentPageAction");
                        boolean bookRead = cP.equals("read");%>
                        <input type="hidden" name="currentPageAction" value="<%=cP%>" />
                        <c:if test="<%=bookRead%>">
                            <input type="hidden" name="idBook" value="<%=request.getAttribute("idBook")%>" />
                            <input type="hidden" name="idPara" value="<%=request.getAttribute("idPara")%>" />
                        </c:if>
                        

      <button type="submit" name="login" formaction="checkuser" class="btn btn-primary btn-block">Login</button>
      <button type="submit" name="register" formaction="register" class="btn btn-block btn-outline-primary">Register</button>
    </form>
  </div>
</div>
                        
                        
                    
                    
                    
                </c:when>
                <c:otherwise>
                    <p>
                        ${utilisateur} est connecté
                        <br>
                        <a href="logout" class="btn btn-secondary">déconnexion</a>
                    </p>
                    
                </c:otherwise>
</c:choose>
                    
                    
                <style>
.dropdown-menu {
  position: absolute;
}
</style>
              