<c:choose>
                <c:when test='${request.session}.getAttribute("utilisateur") == ""'>
                    <form method="post" action="checkuser" accept-charset="UTF-8">
                    <ul>
                      <li> Login : <input type="text" name="login"/></li>
                      <li> Mot de passe : <input type="password" name="password"/></li>
                    </ul>
                    <input type="submit" name="Login" />
                </c:when>
                <c:otherwise>
                    <p> <a href="logout">déconnexion</a></p>
                </c:otherwise>
</c:choose>
 