<%@page import="modele.User"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styleInviteAuthors.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

        <title>Invitations à l'écriture de l'histoire</title>
    </head>
    <body>
        <h1> A quels utilisateurs voulez-vous donner le droit d'éditer votre histoire ? </h1>
        <div class="col-md-6 offset-md-3">
            <jsp:include page="/controleur?action=getInvitedUsers&idBook=${idBook}" />
            <% List<String> aau = (List<String>) request.getAttribute("alreadyAddedUsers");%>



            <ul class="list-group">
                <c:forEach items="<%=aau%>" var="addedUser"> <!-- On affiche les users dj ajoutés -->

                    <li class="list-group-item">

                          ${addedUser}


                           <c:if test="${!(utilisateur.equals(addedUser))}"> <!<!-- utilisateur est un attribut de session -->
                                <form method="post" action="controleur?action=uninviteUser" accept-charset="utf-8">

                                        <input type="hidden" name="idBook" value="${idBook}">
                                        <input type="hidden" name="loginUser" value="${addedUser}">
                                        <button class="btn btn-warning" type="submit" value="supprimer">supprimer</button>

                                </form>
                            </c:if>

                    </li>


                </c:forEach>
              </ul>


            <form method="post" action="controleur?action=uninviteEveryUser" accept-charset="utf-8">

                <input type="hidden" name="idBook" value="${idBook}">
                <div class="d-flex justify-content-center py-1">
                <button class="btn btn-danger" type="submit" value="supprimer l'accès à tous les utilisateurs">supprimer l'accès à tous les utilisateurs</button>
                </div>
            </form>
                <hr>


            <%@include file="addInvitCommon.jsp" %>

            <hr><!-- comment -->

            <form method="post" action="controleur?action=endInvitedAuthors" accept-charset="utf-8">

                <input type="hidden" name="idBook" value="${idBook}">
                <div class="d-flex justify-content-center py-1">
                <button class="btn btn-success" type="submit" value="Valider et passer à la suite">Valider et passer à la suite</button>
                </div>
            </form>
                <hr><!-- comment -->

            <form method="post" action="controleur?action=endInvitedAuthorsOpen" accept-charset="utf-8">
                <input type="hidden" name="idBook" value="${idBook}">
                <div class="d-flex justify-content-center py-1">
                <button class="btn btn-secondary" type="submit" value="Rendre l'histoire ouverte">Rendre l'histoire ouverte</button>  
                </div>
            </form>
        </div>
    </body>
</html>

