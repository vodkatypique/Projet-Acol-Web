<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
                <c:when test='${utilisateur == null}'>
                    <form method="post" accept-charset="UTF-8">
                    <ul>
                      <li> Login : <input type="text" name="login"/></li>
                      <li> Mot de passe : <input type="password" name="password"/></li>
                    </ul>
                        <button type="submit" name="login" formaction="checkuser">Login</button>
                        <button type="submit" name="register" formaction="register">Register</button>
                        
                    
                </c:when>
                <c:otherwise>
                    <p>
                        ${utilisateur} est connecté
                        <a href="logout">déconnexion</a>
                    </p>
                    
                </c:otherwise>
</c:choose>
              