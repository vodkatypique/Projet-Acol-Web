package controleur;

import dao.DAOException;
import dao.BookDAO;
import dao.ChoiceDAO;
import dao.ParagraphDAO;
import dao.UserAccessDAO;
import dao.UserBookHistoryDAO;
import dao.UserDAO;
import java.io.*;
import java.net.http.HttpClient;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import modele.Book;
import modele.Paragraph;
import modele.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modele.Choice;

/**
 * Le contrôleur de l'application.
 */
@WebServlet(name = "Controleur", urlPatterns = {"/controleur"})
public class Controleur extends HttpServlet {

    @Resource(name = "jdbc/Book")
    private DataSource dsBook;

    @Resource(name = "jdbc/Paragraph")
    private DataSource dsParagraph;

    @Resource(name = "jdbc/UserTable")
    private DataSource dsUser;

    @Resource(name = "jdbc/UserAccess")
    private DataSource dsUserAccess;

    @Resource(name = "jdbc/Choice")
    private DataSource dsChoice;

    @Resource(name = "jdbc/UserBookHistory")
    private DataSource dsUserBookHistory;

    /* pages d’erreurs */
    private void invalidParameters(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/controleurErreur.jsp").forward(request, response);
    }

    private void erreurBD(HttpServletRequest request,
                HttpServletResponse response, DAOException e)
            throws ServletException, IOException {
        e.printStackTrace(); // permet d’avoir le détail de l’erreur dans catalina.out
        request.setAttribute("erreurMessage", e.getMessage());
        request.getRequestDispatcher("/WEB-INF/bdErreur.jsp").forward(request, response);
    }

    /**
     * Actions possibles en GET : afficher (correspond à l’absence du param), getOuvrage.
     */
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        BookDAO bookDAO = new BookDAO(dsBook);
        ParagraphDAO paragraphDAO = new ParagraphDAO(dsParagraph);
        UserDAO userDAO = new UserDAO(dsUser);
        UserAccessDAO userAccessDAO = new UserAccessDAO(dsUserAccess);
        ChoiceDAO choiceDAO = new ChoiceDAO(dsChoice);
        UserBookHistoryDAO userBookHistoryDAO = new UserBookHistoryDAO(dsUserBookHistory);
        
        try {
            // actions depuis la page ppale = liste des livres disponibles
            if (action == null || action.equals("accueil")) {
                actionDisplay(request, response, bookDAO);
            } else if (action.equals("getBook")){
                actionGetBook(request, response, bookDAO, paragraphDAO);
            } else if (action.equals("getParagraph")){
                actionGetParagraph(request, response, paragraphDAO, bookDAO);
            } else if (action.equals("access")){
                actionGetAccess(request, response, userDAO, userAccessDAO);
            } else if (action.equals("authors")){
                actionGetAuthors(request, response, paragraphDAO);
            } else if (action.equals("edition")){
                actionEdition(request, response, bookDAO);
            } else if (action.equals("read")){
                actionRead(request, response, bookDAO, paragraphDAO);
            } else if (action.equals("getChoices")){
                actionChoices(request, response, choiceDAO);
            } else if (action.equals("writeBook")){
                actionWriteBook(request, response);
            } else if (action.equals("editParagraph")){  // Rentre dans le menu d'édition d'un paragraphe
                actionGetEditParagraph(request, response, bookDAO, paragraphDAO);
            } else if (action.equals("getHistory")){
                actionGetHistory(request, response, userDAO, userBookHistoryDAO);
            } else if (action.equals("saveHistory")){
                
                actionSaveHistory(request, response, userDAO, userBookHistoryDAO);
            } else if (action.equals("displayParaEdit")){
                actionDisplayParaEdit(request, response, paragraphDAO, bookDAO);
            } else if (action.equals("addChoiceToPara")){
                actionAddChoiceToPara(request, response, paragraphDAO);
            }else if(action.equals("deleteParagraph")){
                actionDeleteParagraph(request, response, paragraphDAO, choiceDAO);
                actionAddChoiceToPara(request, response, paragraphDAO);
            } else if(action.equals("isChoiceValid")) {
                actionIsChoiceValid(request, response, choiceDAO);
            } else if(action.equals("changeInvitations")) {
                actionChangeInvitations(request, response);
            } else if(action.equals("publishOrUnpublish")) {
                actionPublishOrUnpublish(request, response, bookDAO, paragraphDAO);
            }
            else {
                invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }

    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        BookDAO bookDAO = new BookDAO(dsBook);
        ParagraphDAO paragraphDAO = new ParagraphDAO(dsParagraph);
        ChoiceDAO choiceDAO = new ChoiceDAO((dsChoice));
        UserDAO userDAO = new UserDAO(dsUser);
        UserAccessDAO userAccessDAO = new UserAccessDAO(dsUserAccess);
        UserBookHistoryDAO userBookHistoryDAO = new UserBookHistoryDAO(dsUserBookHistory);

        if (action.equals("createNewBook")) {
            actionCreateNewBook(request, response, bookDAO, userDAO, userAccessDAO);
        }else if(action.equals("createParagraph")) {
            actionCreateParagraph(request, response, paragraphDAO, choiceDAO, bookDAO);
        } else if (action.equals("addUserInvit")){
                actionAddUserInvit(request, response, userDAO, userAccessDAO);
            }
        else if (action.equals("getInvitedUsers")) {
                actionGetInvitedUsers(request, response, userDAO, userAccessDAO);
        }
        else if (action.equals("endInvitedAuthors")) {
                actionEndInvitedAuthors(request, response, bookDAO);
        }
        else if (action.equals("endInvitedAuthorsOpen")) {
            actionEndInvitedAuthorsOpen(request, response, bookDAO, userDAO, userAccessDAO);
        } else if(action.equals("postEditParagraph")) {
            actionPostEditParagraph(request, response, paragraphDAO, choiceDAO, bookDAO);
        }
        else if(action.equals("uninviteUser")) {
            actionUninviteUser(request, response, userDAO, userAccessDAO);
        } 
        else if(action.equals("uninviteEveryUser")) {
            actionUninviteEveryUser(request, response, userDAO, userAccessDAO);
        }
        else if(action.equals("choiceAdded")) {
            actionChoiceAdded(request, response, paragraphDAO, bookDAO, choiceDAO);
        } else if (action.equals("getChoices")){
                actionChoices(request, response, choiceDAO);
            } 
        else {
            invalidParameters(request, response);
        }

    }

    /**
     * Redirige vers l'index
     *
     */
    private void actionIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }

    /**
     *
     * Affiche la page d’accueil avec la liste de tous les ouvrages.
     */

    private void actionDisplay(HttpServletRequest request,
            HttpServletResponse response,
            BookDAO bookDAO) throws ServletException, IOException {
        /* On interroge la base de données pour obtenir la liste des ouvrages */
        List<Book> books = bookDAO.getBooksList();
        /* On ajoute cette liste à la requête en tant qu’attribut afin de la transférer à la vue
         * Rem. : ne pas confondre attribut (= objet ajouté à la requête par le programme
         * avant un forward, comme ici)
         * et paramètre (= chaîne représentant des données de formulaire envoyées par le client) */
        request.setAttribute("books", books);
        /* Enfin on transfère la requête avec cet attribut supplémentaire vers la vue qui convient */
        request.getRequestDispatcher("/WEB-INF/listBooksToRead.jsp").forward(request, response);
    }

    private void actionEdition(HttpServletRequest request,
            HttpServletResponse response,
            BookDAO bookDAO) throws ServletException, IOException {
        /* On interroge la base de données pour obtenir la liste des ouvrages */
        List<Book> books = bookDAO.getBooksList();
        /* On ajoute cette liste à la requête en tant qu’attribut afin de la transférer à la vue
         * Rem. : ne pas confondre attribut (= objet ajouté à la requête par le programme
         * avant un forward, comme ici)
         * et paramètre (= chaîne représentant des données de formulaire envoyées par le client) */
        request.setAttribute("books", books);
        /* Enfin on transfère la requête avec cet attribut supplémentaire vers la vue qui convient */
        request.getRequestDispatcher("/WEB-INF/listBooksToEdit.jsp").forward(request, response);
    }

    /**
     *
     * Affiche la page d’accueil avec la liste de tous les ouvrages.
     */
    private void actionGetBook(HttpServletRequest request, HttpServletResponse response,
                               BookDAO bookDAO, ParagraphDAO paragraphDAO) throws DAOException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String v = request.getParameter("view");
        if (v.equals("listBooksToRead") || v.equals("listBooksToEdit")) {
            Book book = bookDAO.getBook(id);
            request.setAttribute("book", book);
            getServletContext().getRequestDispatcher("/WEB-INF/" + v + ".jsp").forward(request, response);
        } else if(v.equals("edit")) {
            Paragraph firstParagraph = paragraphDAO.getParagraph(id, 1);
            Book book = bookDAO.getBook(id);
            request.setAttribute("para", firstParagraph);
            request.setAttribute("book", book);
            getServletContext().getRequestDispatcher("/WEB-INF/bookBeingEdit.jsp").forward(request, response);
        }else {
            invalidParameters(request, response);
        }
    }

    private void actionGetParagraph(HttpServletRequest request, HttpServletResponse response,
                               ParagraphDAO paragraphDAO, BookDAO bookDAO) throws DAOException, ServletException, IOException {
        int idB = Integer.parseInt(request.getParameter("idBook"));
        int idP = Integer.parseInt(request.getParameter("idPara"));
        String v = request.getParameter("view");
        if (v.equals("listBooksToRead") || v.equals("listBooksToEdit")) {
            Paragraph para = paragraphDAO.getParagraph(idB, idP);
            request.setAttribute("paragraph", para);
            //getServletContext().getRequestDispatcher("/WEB-INF/" + v + ".jsp").forward(request, response);
        } else  if (v.equals("edit")) {
            Paragraph para = paragraphDAO.getParagraph(idB, idP);
            Book book = bookDAO.getBook(idB);
            request.setAttribute("para", para);
            request.setAttribute("book", book);
            getServletContext().getRequestDispatcher("/WEB-INF/bookBeingEdit.jsp").forward(request, response);
        }
        else invalidParameters(request, response);
    }

     private void actionGetAccess(HttpServletRequest request,
            HttpServletResponse response,
            UserDAO userDAO, UserAccessDAO userAccessDAO) throws ServletException, IOException {
         boolean is = false;
         int iB = Integer.parseInt(request.getParameter("idBook"));
         String login = (String) request.getSession().getAttribute("utilisateur");
         
         if(login != null) {
             int iU = userDAO.getIdFromLogin(login);
             System.out.println(iU);
             System.out.println(iB);
             is = userAccessDAO.accessBook(iB, iU);
         }

         request.setAttribute("isAccess", is); // faux pr un utilisateur non co
     }

    private void actionGetAuthors(HttpServletRequest request,
            HttpServletResponse response, ParagraphDAO paragraphDAO) throws ServletException, IOException {
         int iB = Integer.parseInt(request.getParameter("idBook"));
         List<String> authors = paragraphDAO.findAuthors(iB);
         request.setAttribute("authors", authors);
     }
    
    private HttpServletResponse setALtoCookie(ArrayList liste, Cookie cookie, HttpServletResponse response){
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        
        cookie.setValue(gson.toJson(liste));
        response.addCookie(cookie);
        return response;
    }
    
    private void actionRead(HttpServletRequest request, 
        HttpServletResponse response, BookDAO bookDAO, ParagraphDAO paragraphDAO) throws ServletException, IOException {
    int idB = Integer.parseInt(request.getParameter("idBook"));
    Book book = bookDAO.getBook(idB);
    int idP = Integer.parseInt(request.getParameter("idPara"));
    //List<Paragraph> paragraphBeingRead = new ArrayList<Paragraph>();
    Paragraph para = paragraphDAO.getParagraph(idB, idP);
    
    //paragraphBeingRead.add(para);
    // il faut concaténer les paragraphes tant qu'il n'y a que que un choix.
    //int choiceNumber = 1;
    //boolean concat = false;
    //while(choiceNumber == 1) {
    //    concat = true;
    //}
    request.setAttribute("bookBeingRead", book);
    request.setAttribute("paragraphBeingRead", para);
    
    HttpSession session = request.getSession();
    
    if (true){
    //if (null == session.getAttribute("utilisateur")){
        request.setAttribute("idBook", idB);
        Cookie[] cookies = request.getCookies();
        boolean cook = false;
        if(cookies != null){ //check si on a le bon cookie
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Integer.toString(idB))) {
                    cook = true;
                    final GsonBuilder builder = new GsonBuilder();
                    final Gson gson = builder.create();
                    ArrayList<String> listCookie = gson.fromJson(cookie.getValue(), ArrayList.class);
                   
                    
                    if(listCookie.contains(Integer.toString(idP))){
                        System.out.println("COKK");
                        for (Cookie cookie2 : cookies) {
                            if (cookie2.getName().equals("temp"+Integer.toString(idB))) {
                                System.out.println("TEMP");
                                System.out.println(listCookie.size());
                                System.out.println(listCookie.size());
                                ArrayList<String> listCookieTemp = gson.fromJson(cookie2.getValue(), ArrayList.class);
                                
                                listCookieTemp.addAll(
                                        0,
                                        listCookie.subList(
                                                listCookie.indexOf(Integer.toString(idP))+1,
                                                listCookie.size()
                                        )
                                );
                                
                                
                                
                                listCookie = new ArrayList<String>(listCookie.subList(
                                        0,
                                        listCookie.indexOf(Integer.toString(idP))+1));
                                
                            
                            setALtoCookie(listCookieTemp, cookie2, response);
                            //cookie2.setValue(gson.toJson(listCookieTemp));
                            //response.addCookie(cookie2);
                            
                            setALtoCookie(listCookie, cookie, response);
                            //cookie.setValue(gson.toJson(listCookie)); 
                            //response.addCookie(cookie);
                            
                            ArrayList val = gson.fromJson(cookie.getValue(), ArrayList.class);
                            val.addAll(gson.fromJson(cookie2.getValue(), ArrayList.class));
                            System.out.println(val);                  
                            
                            request.setAttribute("paragraphes", val);
                            
                            
                            String history = gson.toJson(val);
                           
                            history = history.replaceAll("\"", "\\\\\'");
                            request.setAttribute("history", history);
                            System.out.println("cookie2");
                            }
                        }
                        
                    } else {
                        System.out.println("ICI");
                        listCookie.add(Integer.toString(idP));
                        for (Cookie cookie2 : cookies) {
                            if (cookie2.getName().equals("temp"+Integer.toString(idB))) {
                                ArrayList<String> listCookieTemp = gson.fromJson(cookie2.getValue(), ArrayList.class);
                                if (listCookieTemp.size()>0){
                                    System.out.println(listCookieTemp.get(0));
                                    if (listCookieTemp.get(0).equals(Integer.toString(idP))){
                                        listCookieTemp.remove(0);
                                        
                                    } else {
                                        listCookieTemp = new ArrayList<String>();
                                    }
                                    setALtoCookie(listCookieTemp, cookie2, response);
                                    //cookie2.setValue(gson.toJson(listCookieTemp));
                                    //response.addCookie(cookie2);
                                }                     
                            }   
                    }
                  setALtoCookie(listCookie, cookie, response);
                  //cookie.setValue(gson.toJson(listCookie));
                  //response.addCookie(cookie);
                  request.setAttribute("paragraphes", gson.fromJson(cookie.getValue(), ArrayList.class));
                  System.out.println("ON EST LA");
                  
                  for (Cookie cookie2 : cookies) {
                            if (cookie2.getName().equals("temp"+Integer.toString(idB))) {
                                ArrayList val = gson.fromJson(cookie.getValue(), ArrayList.class);
                            val.addAll(gson.fromJson(cookie2.getValue(), ArrayList.class));
                            System.out.println(val);                  
                            
                            request.setAttribute("paragraphes", val);
                            
                            
                            String history = gson.toJson(val);
                           
                            history = history.replaceAll("\"", "\\\\\'");
                            request.setAttribute("history", history);

                            }
                  }
         
                 }
                    
            }
        }
        if (!cook){
            System.out.println("what");
            List<String> listCookie = new ArrayList<String>();
            List<String> listCookieTemp = new ArrayList<String>();
            listCookie.add(Integer.toString(idP));
            final GsonBuilder builder = new GsonBuilder();
            final Gson gson = builder.create();
            Cookie cookie = new Cookie(Integer.toString(idB), gson.toJson(listCookie));
            //System.out.println(gson.toJson(listCookie));
            response.addCookie(cookie);
            Cookie cookieTemp;
            cookieTemp = new Cookie("temp"+Integer.toString(idB), gson.toJson(listCookieTemp));
            request.setAttribute("paragraphes", gson.fromJson(cookie.getValue(), ArrayList.class));
            response.addCookie(cookieTemp);
        }
    }
    
    request.getRequestDispatcher("/WEB-INF/bookBeingRead.jsp").forward(request, response);
    }
    
    request.getRequestDispatcher("/WEB-INF/bookBeingRead.jsp").forward(request, response);
    }

    private void actionChoices(HttpServletRequest request,
        HttpServletResponse response, ChoiceDAO choiceDAO) {
        int idB = Integer.parseInt(request.getParameter("idBook"));
        int idP = Integer.parseInt(request.getParameter("idPara"));
        List<Paragraph> res = choiceDAO.getListChoices(idB, idP);
        request.setAttribute("choices", res);
    }

    private void actionWriteBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.getRequestDispatcher("/WEB-INF/writeBook.jsp").forward(request, response);
        //request.getRequestDispatcher("/WEB-INF/invitedAuthors.jsp").forward(request, response);
    }

    private void actionGetHistory(HttpServletRequest request,
        HttpServletResponse response, UserDAO userDAO, UserBookHistoryDAO userBookHistoryDAO) throws ServletException, IOException {
        String login = request.getParameter("utilisateur");
        int idU = userDAO.getIdFromLogin(login);
        request.setAttribute("idUser", idU);
        int idB = Integer.parseInt(request.getParameter("idBook"));
        String res = userBookHistoryDAO.getHistory(idB, idU);
        request.setAttribute("history", res);
        System.out.println(res);
        
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
            Cookie cookie = new Cookie(Integer.toString(idB), res.replaceAll("\'", "\""));
            //System.out.println(gson.toJson(listCookie));
            response.addCookie(cookie);
            Cookie cookieTemp;
            cookieTemp = new Cookie("temp"+idB, "[]");
            request.setAttribute("paragraphes", gson.fromJson(cookie.getValue(), ArrayList.class));
            response.addCookie(cookieTemp);
        
        response.addCookie(cookie);
        System.out.println(request.getRequestURI());
        
        response.sendRedirect(request.getContextPath());
    }

    private void actionSaveHistory(HttpServletRequest request,
        HttpServletResponse response, UserDAO userDAO, UserBookHistoryDAO userBookHistoryDAO) throws ServletException, IOException{
        
        System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
        String login = request.getParameter("utilisateur");
        int idU = userDAO.getIdFromLogin(login);
        request.setAttribute("idUser", idU);
        int idB = Integer.parseInt(request.getParameter("idBook"));
        userBookHistoryDAO.suppressHistory(idB, idU);
        
        
        String histo = request.getParameter("history");
        
        
        
        userBookHistoryDAO.addHistory(idB, idU, histo);
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>History created</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> L'historique a bien été sauvegardé! </h1>");
            out.println("<a href=\"controleur?action=accueil\">Retour à l'accueil</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private void actionCreateNewBook(HttpServletRequest request, HttpServletResponse response, BookDAO bookDAO, UserDAO userDAO, UserAccessDAO userAccessDAO) throws ServletException, IOException{
        String title = request.getParameter("title");
        String loginConnectedUser = (String) request.getSession().getAttribute("utilisateur");
        int idConnectedUser = userDAO.getIdFromLogin(loginConnectedUser);
        int idBook = bookDAO.addBook(title, loginConnectedUser);
        request.setAttribute("idBook", idBook);
        userAccessDAO.addNewAccess(idBook, idConnectedUser);
        //request.getRequestDispatcher("/WEB-INF/writeBook.jsp").forward(request, response);
        request.getRequestDispatcher("/WEB-INF/invitedAuthors.jsp").forward(request, response);
    }


    private void actionCreateParagraph(HttpServletRequest request, HttpServletResponse response, ParagraphDAO paragraphDAO, ChoiceDAO choiceDAO, BookDAO bookDAO) throws ServletException, IOException{
           // TODO not yet implemented
           int idBook = Integer.parseInt(request.getParameter("idBook"));
           int numParagraph = paragraphDAO.getCurrentMaxNumParagraph(idBook) + 1;
           String paragraphTitle = request.getParameter("paragraphTitle");
           String paragraphContent = request.getParameter("paragraphContent");
           String author = (String) request.getSession().getAttribute("utilisateur");
           boolean isEnd = Boolean.parseBoolean(request.getParameter("isEnd"));
           boolean isValidate = true;
           boolean isAccess = true;
           paragraphDAO.addParagraph(idBook,
                                     numParagraph,
                                     paragraphTitle,
                                     paragraphContent,
                                     author,
                                     isEnd,
                                     isValidate,
                                     isAccess);
           String[] choices = request.getParameterValues("choice");
           if (choices != null){
                for(int i = 0; i < choices.length; i++) {
                    paragraphDAO.addParagraph(idBook,
                                              numParagraph + i + 1,
                                              choices[i],
                                              "La suite de l'histoire n'a pas encore été écrite",
                                              author,
                                              false,
                                              false,
                                              true);
                     choiceDAO.addChoice(idBook, numParagraph, numParagraph + i +1, 0); // TO DO choix disponible avec condition
                }
           }
           try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Paragraph created</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Le paragraphe " + paragraphTitle + " a bien été créé! </h1>");
            out.println("<a href=\"controleur?action=edition\">Retour à l'édition</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
 private void actionDeleteParagraph(HttpServletRequest request,
        HttpServletResponse response, ParagraphDAO paragraphDAO, ChoiceDAO choiceDAO) throws IOException{
        int idBook = Integer.parseInt(request.getParameter("idB"));
        int idPara = Integer.parseInt(request.getParameter("idP"));
        //On s'assure qu'il n'y a pas de choix après le paragraphe à supprimer
        List<Paragraph> choices = choiceDAO.getListChoices(idBook, idPara);
        if(choices.isEmpty()){
            // On supprime les choix qui renvoie vers le paragraphe à supprimer
            List<Paragraph> predecessorChoices = choiceDAO.getListPredecessorChoices(idBook, idPara);
            for (Paragraph choice : predecessorChoices){
                choiceDAO.suppressChoice(idBook, choice.getId(), idPara);
            }
            paragraphDAO.deleteParagraph(idBook, idPara);
            try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Paragraph deleted</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Le paragraphe a bien été supprimé! </h1>");
            out.println("<a href=\"controleur?action=edition\">Retour à l'édition</a>");
            out.println("</body>");
            out.println("</html>");
            }
        }
        
        
 }

private void actionAddUserInvit(HttpServletRequest request,
        HttpServletResponse response, UserDAO userDAO, UserAccessDAO userAccessDAO) throws ServletException, IOException {
    String log = request.getParameter("userToAdd");
    int idUser = userDAO.getIdFromLogin(log);
    String loginConnectedUser = (String) request.getSession().getAttribute("utilisateur");
    int idConnectedUser = userDAO.getIdFromLogin(loginConnectedUser);
    if(idUser != -1 && idUser != idConnectedUser) { // L'utilisateur existe et ce n'est pas celui connecté
        int idBook = Integer.parseInt(request.getParameter("idBook"));
        boolean already = userAccessDAO.accessBook(idBook, idUser);
        if (already) {
            request.setAttribute("errorInAddedUser3", log);
        } else {
            userAccessDAO.addNewAccess(idBook, idUser);
        }
    } else if (idUser == idConnectedUser){
        request.setAttribute("errorInAddedUser2", log);
    } else { // l'utilisateur donné est inexistant
        request.setAttribute("errorInAddedUser", log);
    }
    request.setAttribute("idBook", request.getParameter("idBook"));
    request.getRequestDispatcher("/WEB-INF/invitedAuthors.jsp").forward(request, response);
}

private void actionGetInvitedUsers(HttpServletRequest request,
        HttpServletResponse response, UserDAO userDAO, UserAccessDAO userAccessDAO) throws ServletException, IOException {

    int idBook = Integer.parseInt(request.getParameter("idBook"));
    List<Integer> listOfIds = userAccessDAO.getAllUsersAllowed(idBook);
    List<String> list = new ArrayList<String>();
    for(int id : listOfIds) {
        String login = userDAO.getLoginFromId(id);
        list.add(login);
    }
    request.setAttribute("alreadyAddedUsers", list);
}

    private void actionEndInvitedAuthors(HttpServletRequest request, HttpServletResponse response, BookDAO bookDAO) throws ServletException, IOException{
        Book book = bookDAO.getBook(Integer.parseInt(request.getParameter("idBook")));
        request.setAttribute("book", book);
        request.getRequestDispatcher("/WEB-INF/writeBook.jsp").forward(request, response);
}

    private void actionEndInvitedAuthorsOpen(HttpServletRequest request, HttpServletResponse response, BookDAO bookDAO, UserDAO userDAO, UserAccessDAO userAccessDAO) throws ServletException, IOException{
        //rendre accessible à tous
        List<Integer> listOfIds = userDAO.getListIdUser();
        int idBook = Integer.parseInt(request.getParameter("idBook"));
        for(int id : listOfIds) {
            if(!(userAccessDAO.accessBook(idBook, id))) {
                userAccessDAO.addNewAccess(idBook, id);
            }
        }
        actionEndInvitedAuthors(request, response, bookDAO);
}
    
    private void actionUninviteUser(HttpServletRequest request, HttpServletResponse response, UserDAO userDAO, UserAccessDAO userAccessDAO) throws ServletException, IOException{
        int idBook = Integer.parseInt(request.getParameter("idBook"));
        int idUser = userDAO.getIdFromLogin(request.getParameter("loginUser"));
        userAccessDAO.removeAccess(idBook, idUser);
        request.setAttribute("idBook", idBook);
        request.getRequestDispatcher("/WEB-INF/invitedAuthors.jsp").forward(request, response);
    }
    
    private void actionUninviteEveryUser(HttpServletRequest request, HttpServletResponse response, UserDAO userDAO, UserAccessDAO userAccessDAO) throws ServletException, IOException{
        int idBook = Integer.parseInt(request.getParameter("idBook"));
        String loginConnectedUser = (String) request.getSession().getAttribute("utilisateur");
        int idConnectedUser = userDAO.getIdFromLogin(loginConnectedUser);
        userAccessDAO.removeEveryAccess(idBook);
        userAccessDAO.addNewAccess(idBook, idConnectedUser);
        request.setAttribute("idBook", idBook);
        request.getRequestDispatcher("/WEB-INF/invitedAuthors.jsp").forward(request, response);
    }
    
    private void actionChangeInvitations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int idBook = Integer.parseInt(request.getParameter("idBook"));
        request.setAttribute("idBook", idBook);
        request.getRequestDispatcher("/WEB-INF/invitedAuthors.jsp").forward(request, response);
    }

    private void actionGetEditParagraph(HttpServletRequest request, HttpServletResponse response, BookDAO bookDAO, ParagraphDAO paragraphDAO)
               throws ServletException, IOException{
        int idBook = Integer.parseInt(request.getParameter("idBook"));
        int numParagraph = Integer.parseInt(request.getParameter("numParagraph"));
        Paragraph paragraph = paragraphDAO.getParagraph(idBook, numParagraph);
        request.setAttribute("paragraph", paragraph);
        Book book = bookDAO.getBook(idBook);
        request.setAttribute("book", book);
        paragraphDAO.lockParagraph(idBook, numParagraph);
        request.getRequestDispatcher("/WEB-INF/writeBook.jsp").forward(request, response);
    }

    private void actionDisplayParaEdit(HttpServletRequest request, HttpServletResponse response, ParagraphDAO paragraphDAO, BookDAO bookDAO) throws ServletException, IOException {
           int idBook = Integer.parseInt(request.getParameter("idBook"));
           int numParagraph = Integer.parseInt(request.getParameter("numParagraph"));
           request.setAttribute("book", bookDAO.getBook(idBook));
           request.setAttribute("para", paragraphDAO.getParagraph(idBook, numParagraph));
           request.getRequestDispatcher("/WEB-INF/bookBeingEdit.jsp").forward(request, response);
    }
    
       private void actionPostEditParagraph(HttpServletRequest request, HttpServletResponse response, ParagraphDAO paragraphDAO, ChoiceDAO choiceDAO, BookDAO bookDAO) throws ServletException, IOException{
           int idBook = Integer.parseInt(request.getParameter("idBook"));
           int numParagraph = Integer.parseInt(request.getParameter("numParagraph"));
           String paragraphTitle = request.getParameter("paragraphTitle");
           String paragraphContent = request.getParameter("paragraphContent");
           String author = (String) request.getSession().getAttribute("utilisateur");
            // TO DO  Ajouter les booléens au formulaire
           boolean isEnd = Boolean.parseBoolean(request.getParameter("isEnd"));
           boolean isValidate = true;
           boolean isAccess = true;
           paragraphDAO.modifyParagraph(idBook,
                                     numParagraph,
                                     paragraphTitle,
                                     paragraphContent,
                                     author,
                                     isEnd,
                                     isValidate,
                                     isAccess);
           String[] choices = request.getParameterValues("choice");
           if (choices != null){
                for(int i = 0; i < choices.length; i++) {
                    paragraphDAO.addParagraph(idBook,
                                              numParagraph + i + 1,
                                              choices[i],
                                              "La suite de l'histoire n'a pas encore été écrite",
                                              author,
                                              false,
                                              false,
                                              true);
                     choiceDAO.addChoice(idBook, numParagraph, numParagraph + i +1, 0); // TO DO choix disponible avec condition
                }
           }
           try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Book created</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Le paragraphe \"" + paragraphTitle + "\" a bien été modifié! </h1>");
                out.println("<a href=\"controleur?action=displayParaEdit&idBook=" + idBook + "&numParagraph=" + numParagraph + "\">Retour à l'édition</a>");
                out.println("</body>");
                out.println("</html>");
                }
       }
       
       private void actionAddChoiceToPara(HttpServletRequest request, HttpServletResponse response, ParagraphDAO paragraphDAO) throws ServletException, IOException {
           int idBook = Integer.parseInt(request.getParameter("idBook"));
           int numParagraph = Integer.parseInt(request.getParameter("numParagraph"));
           request.setAttribute("idBook", idBook);
           request.setAttribute("numParagraph", numParagraph);
           List<Paragraph> list = paragraphDAO.getListParagraphs(idBook);
           request.setAttribute("listPara", list);
           if (Boolean.parseBoolean(request.getParameter("isNew"))) {
               request.getRequestDispatcher("/WEB-INF/addNewChoice.jsp").forward(request, response);
           }
           else {
               request.getRequestDispatcher("/WEB-INF/addChoiceAlreadyExists.jsp").forward(request, response);
           }
    }
       
    private void actionChoiceAdded(HttpServletRequest request, HttpServletResponse response, ParagraphDAO paragraphDAO, BookDAO bookDAO, ChoiceDAO choiceDAO) throws ServletException, IOException {
           int idBook = Integer.parseInt(request.getParameter("idBook"));
           int numParagraph = Integer.parseInt(request.getParameter("numParagraph"));
           int numNextParagraph = 0;
           int numParagraphConditional = 0;
           String author = (String) request.getSession().getAttribute("utilisateur");
           boolean isNew = Boolean.parseBoolean(request.getParameter("isNew"));
           boolean isError = false;
           if (isNew) {
               String choiceText = request.getParameter("choiceText");
               isError = paragraphDAO.isParagraphWithThisTitle(choiceText);
               if (isError) {
                   request.setAttribute("previousError", choiceText);
                   request.setAttribute("idBook", idBook);
                   request.setAttribute("numParagraph", numParagraph);
                   List<Paragraph> list = paragraphDAO.getListParagraphs(idBook);
                   request.setAttribute("listPara", list);
                   request.getRequestDispatcher("/WEB-INF/addNewChoice.jsp").forward(request, response);
               } else {
                    numNextParagraph = paragraphDAO.getCurrentMaxNumParagraph(idBook) + 1;
                    paragraphDAO.addParagraph(idBook,
                                              numNextParagraph,
                                              choiceText,
                                              "La suite de l'histoire n'a pas encore été écrite",
                                              author,
                                              false,
                                              false,
                                              false);
               }
           } else { // on relie à un paragraphe déjà existant
               numNextParagraph = Integer.parseInt(request.getParameter("numNextParagraph"));
           }
           //request.setAttribute("valeurMystere", request.getParameter("isConditional"));
           //request.getRequestDispatcher("/WEB-INF/test.jsp").forward(request, response);
           if(!isError) {
                if(request.getParameter("isConditional") != null) {
                    numParagraphConditional = Integer.parseInt(request.getParameter("conditionalToWhich"));
                }
                choiceDAO.addChoice(idBook, numParagraph, numNextParagraph, numParagraphConditional);
                paragraphDAO.removeIsEnd(idBook, numParagraph); // le paragraphe a un choix donc ce n'est plus une conclusion
                request.setAttribute("book", bookDAO.getBook(idBook));
                request.setAttribute("para", paragraphDAO.getParagraph(idBook, numParagraph));
                request.getRequestDispatcher("/WEB-INF/bookBeingEdit.jsp").forward(request, response);
           }
          
    }
    
    private void actionIsChoiceValid(HttpServletRequest request, HttpServletResponse response, ChoiceDAO choiceDAO) throws ServletException, IOException {
       int idBook = Integer.parseInt(request.getParameter("idBook"));
       int numParagraph = Integer.parseInt(request.getParameter("numParagraph"));
       int numNextParagraph = Integer.parseInt(request.getParameter("numNextParagraph"));
       boolean res = false;
       boolean isDistinct = (numParagraph != numNextParagraph);
       if (isDistinct) {
           res = !(choiceDAO.isAlreadyHere(idBook, numParagraph, numNextParagraph));
       }
       request.setAttribute("isChoiceValid", res);
    }
    
    private void actionPublishOrUnpublish(HttpServletRequest request, HttpServletResponse response, BookDAO bookDAO, ParagraphDAO paragraphDAO) throws ServletException, IOException {
       int idBook = Integer.parseInt(request.getParameter("idBook"));
       int idPara = Integer.parseInt(request.getParameter("idPara"));
       boolean isPublished = Boolean.parseBoolean(request.getParameter("idPublished"));
       boolean isError = !(bookDAO.inversePublication(idBook, !isPublished));
       int pubCode = (isError)? -1 : (isPublished)? 0 : 1; // 1 = publiée avec succès, 0 = dépubliée avec succès, -1 = échec
       request.setAttribute("pubCode", pubCode);
       request.setAttribute("book", bookDAO.getBook(idBook));
       request.setAttribute("para", paragraphDAO.getParagraph(idBook, idPara));
       request.getRequestDispatcher("/WEB-INF/bookBeingEdit.jsp").forward(request, response);
    }
}
