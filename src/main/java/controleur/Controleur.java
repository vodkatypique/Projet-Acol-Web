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
import dao.UserEditingParagraphDAO;
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

    @Resource(name = "jdbc/UserEditingParagraph")
    private DataSource dsUserEditingParagraph;

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
        UserEditingParagraphDAO userEditingParagraphDAO =  new UserEditingParagraphDAO(dsUserEditingParagraph);

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
                actionRead(request, response, bookDAO, choiceDAO, paragraphDAO);
            } else if (action.equals("getAllChoices")){
                actionChoices(request, response, choiceDAO);
            } else if (action.equals("getChoicesRead")){
            actionChoicesRead(request, response, choiceDAO, paragraphDAO);
            } else if (action.equals("writeBook")){
                actionWriteBook(request, response);
            } else if (action.equals("editParagraph")){  // Rentre dans le menu d'édition d'un paragraphe
                actionGetEditParagraph(request, response, bookDAO, paragraphDAO, userEditingParagraphDAO, userDAO);
            } else if (action.equals("getHistory")){
                actionGetHistory(request, response, userDAO, userBookHistoryDAO);
            } else if (action.equals("saveHistory")){

                actionSaveHistory(request, response, userDAO, userBookHistoryDAO);
            } else if (action.equals("displayParaEdit")){
                actionDisplayParaEdit(request, response, paragraphDAO, bookDAO);
            } else if (action.equals("addChoiceToPara")){
                actionAddChoiceToPara(request, response, paragraphDAO);
            }else if(action.equals("deleteParagraph")){
                actionDeleteParagraph(request, response, paragraphDAO, choiceDAO, userEditingParagraphDAO, bookDAO);
            } else if(action.equals("isChoiceValid")) {
                actionIsChoiceValid(request, response, choiceDAO);
            } else if(action.equals("changeInvitations")) {
                actionChangeInvitations(request, response);
            } else if(action.equals("publishOrUnpublish")) {
                actionPublishOrUnpublish(request, response, bookDAO, paragraphDAO);
            } else if(action.equals("getTypeOpen")) {
                actionGetTypeOpen(request, response, bookDAO);
            } else if (action.equals("getInvitedUsers")) {
                actionGetInvitedUsers(request, response, userDAO, userAccessDAO);
            } else if(action.equals("cancelEditParagraph")){
                  actionCancelEditParagraph(request, response, userEditingParagraphDAO, paragraphDAO, userDAO);
            } else if(action.equals("deleteBook")){
                  actionDeleteBook(request, response, bookDAO);
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
        UserEditingParagraphDAO userEditingParagraphDAO = new UserEditingParagraphDAO((dsUserEditingParagraph));

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
                actionEndInvitedAuthors(request, response, bookDAO, paragraphDAO);
        }
        else if (action.equals("endInvitedAuthorsOpen")) {
            actionEndInvitedAuthorsOpen(request, response, bookDAO, userDAO, userAccessDAO, paragraphDAO);
        } else if(action.equals("postEditParagraph")) {
            actionPostEditParagraph(request, response, paragraphDAO, choiceDAO, bookDAO, userEditingParagraphDAO);
        }
        else if(action.equals("uninviteUser")) {
            actionUninviteUser(request, response, userDAO, userAccessDAO);
        }
        else if(action.equals("uninviteEveryUser")) {
            actionUninviteEveryUser(request, response, userDAO, userAccessDAO);
        }
        else if(action.equals("choiceAdded")) {
            actionChoiceAdded(request, response, paragraphDAO, bookDAO, choiceDAO);
        } else if (action.equals("getAllChoices")){
            actionChoices(request, response, choiceDAO);
        } else if (action.equals("getChoicesRead")){
            actionChoicesRead(request, response, choiceDAO, paragraphDAO);
        } else if(action.equals("getTypeOpen")) {
            actionGetTypeOpen(request, response, bookDAO);
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
        HttpServletResponse response, BookDAO bookDAO, ChoiceDAO choiceDAO, ParagraphDAO paragraphDAO) throws ServletException, IOException {
    int idB = Integer.parseInt(request.getParameter("idBook"));
    request.setAttribute("idBook", idB);
    Book book = bookDAO.getBook(idB);
    int idP = Integer.parseInt(request.getParameter("idPara"));
    request.setAttribute("idPara", idP);
    List<Paragraph> paragraphsBeingRead = new ArrayList<Paragraph>();
    Paragraph para = paragraphDAO.getParagraph(idB, idP);

    paragraphsBeingRead.add(para);
    // il faut concaténer les paragraphes tant qu'il n'y a que que un choix.
    List<Paragraph> listChoices = choiceDAO.getListChoices(idB, idP);
    int choiceNumber = listChoices.size();
    Paragraph currentP = para;

    while(choiceNumber == 1) {
        currentP = listChoices.get(0);
        paragraphsBeingRead.add(currentP);
        listChoices = choiceDAO.getListChoices(idB, currentP.getId());
        choiceNumber = listChoices.size();
    }


    HttpSession session = request.getSession();

    request.setAttribute("idLastPara", currentP.getId());
    request.setAttribute("bookBeingRead", book);
    request.setAttribute("paragraphsBeingRead", paragraphsBeingRead);

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

    List<Paragraph> choicesConditionalRead(HttpServletRequest request,
        HttpServletResponse response, ChoiceDAO choiceDAO, ParagraphDAO paragraphDAO,
        List<Choice> choices) {


        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        String paragraphes = request.getParameter("paragraphes");
        ArrayList<Double> listHisto = gson.fromJson(paragraphes, ArrayList.class);
        int idB = Integer.parseInt(request.getParameter("idBook"));

        List<Paragraph> result = new ArrayList<Paragraph>();
        for(Choice c : choices) {
            int cond = c.getNumParagraphConditional();
            if(cond == -1) {
                result.add(paragraphDAO.getParagraph(idB, c.getNumParagraphNext()));
            }
            else if(listHisto != null){
                for(double i : listHisto) {
                    if(i == cond) {
                        result.add(paragraphDAO.getParagraph(idB, c.getNumParagraphNext()));
                        break;
                    }
                }
            }
        }

        return result;
    }



    private void actionChoicesRead(HttpServletRequest request,
        HttpServletResponse response, ChoiceDAO choiceDAO, ParagraphDAO paragraphDAO) {
        int idB = Integer.parseInt(request.getParameter("idBook"));
        int idP = Integer.parseInt(request.getParameter("idPara"));

        List<Paragraph> res = choicesConditionalRead(request, response, choiceDAO, paragraphDAO,
                choiceDAO.getListChoicesRead(idB, idP));
        request.setAttribute("choices", res);
    }

    private void actionWriteBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.getRequestDispatcher("/WEB-INF/writeBook.jsp").forward(request, response);
    }

    private void actionGetHistory(HttpServletRequest request,
        HttpServletResponse response, UserDAO userDAO, UserBookHistoryDAO userBookHistoryDAO) throws ServletException, IOException {
        String login = request.getParameter("utilisateur");
        int idU = userDAO.getIdFromLogin(login);
        request.setAttribute("idUser", idU);
        int idB = Integer.parseInt(request.getParameter("idBook"));
        String res = userBookHistoryDAO.getHistory(idB, idU, response);
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
        boolean isAlready = bookDAO.isAlreadyWithTitle(title);
        if(isAlready) {
            request.setAttribute("errorTitle", title);
            request.getRequestDispatcher("/WEB-INF/writeBook.jsp").forward(request, response);
        } else {
             String loginConnectedUser = (String) request.getSession().getAttribute("utilisateur");
            int idConnectedUser = userDAO.getIdFromLogin(loginConnectedUser);
            int idBook = bookDAO.addBook(title, loginConnectedUser);
            request.setAttribute("idBook", idBook);
            request.setAttribute("idPara", -1);
            userAccessDAO.addNewAccess(idBook, idConnectedUser);
            request.getRequestDispatcher("/WEB-INF/invitedAuthors.jsp").forward(request, response);
        }
    }


    private void actionCreateParagraph(HttpServletRequest request, HttpServletResponse response, ParagraphDAO paragraphDAO, ChoiceDAO choiceDAO, BookDAO bookDAO) throws ServletException, IOException{
           int idBook = Integer.parseInt(request.getParameter("idBook"));
           int numParagraph = paragraphDAO.getCurrentMaxNumParagraph(idBook) + 1;
           String paragraphTitle = request.getParameter("paragraphTitle");
           String paragraphContent = request.getParameter("paragraphContent");
           String author = (String) request.getSession().getAttribute("utilisateur");
           boolean isEnd = request.getParameter("isEnd") != null;
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
                     choiceDAO.addChoice(idBook, numParagraph, numParagraph + i +1, -1);
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
                HttpServletResponse response, ParagraphDAO paragraphDAO,
                ChoiceDAO choiceDAO, UserEditingParagraphDAO userEditingParagraphDAO, BookDAO bookDAO) throws IOException, ServletException{
        int idBook = Integer.parseInt(request.getParameter("idB"));
        int idPara = Integer.parseInt(request.getParameter("idP"));

        boolean isDeletable = choiceDAO.isDeletable(idBook, idPara);
        if(isDeletable) {
                    /* On peut supprimer uniquement si :
                        - il n'y a aucun choix après ce paragraphe
                        - Tous les choix suivant ne sont pas validé ou actuellement édité par quelqu'un
             */
            List<Paragraph> nextChoices = choiceDAO.getListChoices(idBook, idPara);
            boolean hasNoValidateChoice = true;
            for (Paragraph choice : nextChoices) {
                if (!choice.getIsAccessible() || choice.getIsValidate()) {
                    hasNoValidateChoice = false;
                    break;
                }
            }
            if(hasNoValidateChoice){
                // On supprime les choix qui renvoie vers le paragraphe à supprimer
                List<Paragraph> predecessorChoices = choiceDAO.getListPredecessorChoices(idBook, idPara);
                for (Paragraph choice : predecessorChoices){
                    choiceDAO.suppressChoice(idBook, choice.getId(), idPara);
                }
                // On supprime les choix du paragraphe qui n'ont pas été validés
                for (Paragraph choice : nextChoices){
                    choiceDAO.suppressChoice(idBook, idPara, choice.getId());
                    paragraphDAO.deleteParagraph(idBook, choice.getId());
                }
                paragraphDAO.deleteParagraph(idBook, idPara);
                userEditingParagraphDAO.deleteEditing(idBook, idPara);
                if(idPara == 1) { // Si on supprime le 1er paragraphe d'un livre, on supprime carrément le livre
                    bookDAO.deleteBook(idBook);
                }
                try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Paragraph deleted</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Le paragraphe \"" + request.getParameter("title") +"\" a bien été supprimé! </h1>");
                if(idPara == 1) {
                    out.println("<h2>Puisqu'il s'agissait du seul paragraphe de l'histoire, le livre a également été supprimé. </h2>");
                }
                out.println("<a href=\"controleur?action=edition\">Retour à l'édition</a>");
                out.println("</body>");
                out.println("</html>");
                }
            }
        } else {
            request.setAttribute("errorDelete", "Ce paragraphe ne peut pas être supprimé car c'est le seul choix inconditionnel d'un des paragraphes non finaux de cette histoire !");
            request.setAttribute("book", bookDAO.getBook(idBook));
            request.setAttribute("paragraph", paragraphDAO.getParagraph(idBook, idPara));
            request.getRequestDispatcher("/WEB-INF/writeBook.jsp").forward(request, response);
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
            request.setAttribute("validated", log);
        }
    } else if (idUser == idConnectedUser){
        request.setAttribute("errorInAddedUser2", log);
    } else { // l'utilisateur donné est inexistant
        request.setAttribute("errorInAddedUser", log);
    }
    request.setAttribute("idBook", request.getParameter("idBook"));
    int idPara = Integer.parseInt(request.getParameter("idPara"));
    if(idPara == -1) { // On vient des invits de départ
        request.setAttribute("idPara", -1);
        request.getRequestDispatcher("/WEB-INF/invitedAuthors.jsp").forward(request, response);
    } else { // On vient des invits ajoutées après
        request.setAttribute("idPara", request.getParameter("idPara"));
        request.getRequestDispatcher("/WEB-INF/addNewInvit.jsp").forward(request, response);
    }

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

    private void actionEndInvitedAuthors(HttpServletRequest request, HttpServletResponse response, BookDAO bookDAO, ParagraphDAO paragraphDAO) throws ServletException, IOException{
        int idBook = Integer.parseInt(request.getParameter("idBook"));
        Book book = bookDAO.getBook(idBook);
        request.setAttribute("book", book);
        List<Paragraph> list = paragraphDAO.getListParagraphs(idBook);
        request.setAttribute("listPara", list);
        if(request.getParameter("idPara") == null) { // on vient des choix d'invitations qd on crée le livre
            request.getRequestDispatcher("/WEB-INF/writeBook.jsp").forward(request, response);
        } else { // on vient de l'ajout d'invitation après coup
            int idPara = Integer.parseInt(request.getParameter("idPara"));
            request.setAttribute("para", paragraphDAO.getParagraph(idBook, idPara));
            request.getRequestDispatcher("/WEB-INF/bookBeingEdit.jsp").forward(request, response);
        }
}

    private void actionEndInvitedAuthorsOpen(HttpServletRequest request, HttpServletResponse response, BookDAO bookDAO, UserDAO userDAO, UserAccessDAO userAccessDAO, ParagraphDAO paragraphDAO) throws ServletException, IOException{
        //rendre accessible à tous
        List<Integer> listOfIds = userDAO.getListIdUser();
        int idBook = Integer.parseInt(request.getParameter("idBook"));
        for(int id : listOfIds) {
            if(!(userAccessDAO.accessBook(idBook, id))) {
                userAccessDAO.addNewAccess(idBook, id);
            }
        }
        bookDAO.makeOpen(idBook); // On met isOpen à un et cela ne pourra plus être modifié
        actionEndInvitedAuthors(request, response, bookDAO, paragraphDAO);
}

    private void actionUninviteUser(HttpServletRequest request, HttpServletResponse response, UserDAO userDAO, UserAccessDAO userAccessDAO) throws ServletException, IOException{
        int idBook = Integer.parseInt(request.getParameter("idBook"));
        int idUser = userDAO.getIdFromLogin(request.getParameter("loginUser"));
        userAccessDAO.removeAccess(idBook, idUser);
        request.setAttribute("idBook", idBook);
        request.setAttribute("idPara", -1);
        request.getRequestDispatcher("/WEB-INF/invitedAuthors.jsp").forward(request, response);
    }

    private void actionUninviteEveryUser(HttpServletRequest request, HttpServletResponse response, UserDAO userDAO, UserAccessDAO userAccessDAO) throws ServletException, IOException{
        int idBook = Integer.parseInt(request.getParameter("idBook"));
        String loginConnectedUser = (String) request.getSession().getAttribute("utilisateur");
        int idConnectedUser = userDAO.getIdFromLogin(loginConnectedUser);
        userAccessDAO.removeEveryAccess(idBook);
        userAccessDAO.addNewAccess(idBook, idConnectedUser);
        request.setAttribute("idBook", idBook);
        request.setAttribute("idPara", -1);
        request.getRequestDispatcher("/WEB-INF/invitedAuthors.jsp").forward(request, response);
    }

    private void actionChangeInvitations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int idBook = Integer.parseInt(request.getParameter("idBook"));
        request.setAttribute("idBook", idBook);
        int idPara = Integer.parseInt(request.getParameter("idPara"));
        request.setAttribute("idPara", idPara);
        request.getRequestDispatcher("/WEB-INF/addNewInvit.jsp").forward(request, response);
    }

    private void actionGetEditParagraph(HttpServletRequest request, HttpServletResponse response, BookDAO bookDAO, ParagraphDAO paragraphDAO,
                                                UserEditingParagraphDAO userEditingParagraphDAO, UserDAO userDAO)
               throws ServletException, IOException{
        int idBook = Integer.parseInt(request.getParameter("idBook"));
        int numParagraph = Integer.parseInt(request.getParameter("numParagraph"));
        Paragraph paragraph = paragraphDAO.getParagraph(idBook, numParagraph);
        int idUser = userDAO.getIdFromLogin((String) request.getSession().getAttribute("utilisateur"));
        Paragraph editParagraph = userEditingParagraphDAO.getParagraph(idUser);
        if(editParagraph == null || (editParagraph.getIdBook() == idBook && editParagraph.getId() == numParagraph)){
                request.setAttribute("paragraph", paragraph);
                Book book = bookDAO.getBook(idBook);
                request.setAttribute("book", book);
                List<Paragraph> list = paragraphDAO.getListParagraphs(idBook);
                request.setAttribute("listPara", list);
                paragraphDAO.lockParagraph(idBook, numParagraph);
                if(editParagraph == null){
                    userEditingParagraphDAO.addEditing(idUser, idBook, numParagraph);
                }
                request.getRequestDispatcher("/WEB-INF/writeBook.jsp").forward(request, response);
        } else {
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Double edition</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("Vous êtes déjà en train d'éditer un paragraphe");
                out.println("<a href=\"controleur?action=editParagraph&idBook=" + editParagraph.getIdBook() + "&numParagraph=" + editParagraph.getId() + "\">"
                        + "continuer l'édition de \""+ editParagraph.getTitle() + "\"</a>");
                out.println("</body>");
                out.println("</html>");
                }

        }
    }

    private void actionDisplayParaEdit(HttpServletRequest request, HttpServletResponse response, ParagraphDAO paragraphDAO, BookDAO bookDAO) throws ServletException, IOException {
           int idBook = Integer.parseInt(request.getParameter("idBook"));
           int numParagraph = Integer.parseInt(request.getParameter("numParagraph"));
           request.setAttribute("book", bookDAO.getBook(idBook));
           request.setAttribute("para", paragraphDAO.getParagraph(idBook, numParagraph));
           request.getRequestDispatcher("/WEB-INF/bookBeingEdit.jsp").forward(request, response);
    }

       private void actionPostEditParagraph(HttpServletRequest request, HttpServletResponse response,
           ParagraphDAO paragraphDAO, ChoiceDAO choiceDAO, BookDAO bookDAO, UserEditingParagraphDAO userEditingParagraphDAO) throws ServletException, IOException{
           int idBook = Integer.parseInt(request.getParameter("idBook"));
           int numParagraph = Integer.parseInt(request.getParameter("numParagraph"));
           String paragraphTitle = request.getParameter("paragraphTitle");
           String paragraphContent = request.getParameter("paragraphContent");
           String author = (String) request.getSession().getAttribute("utilisateur");
           boolean isEnd = request.getParameter("isEnd") != null;
           boolean isValidate = true;
           boolean isAccess = true;
           boolean isIncond = true;
           boolean isNewParagraph = Boolean.parseBoolean(request.getParameter("isNewParagraph"));
           
           if((!isNewParagraph) && (!isEnd)) {
               // vérif qu'il existe au moins 1 choix inconditionnel, sinon pas possible de décocher isEnd et on doit renvoyer une erreur
               isIncond = choiceDAO.isAnyInconditionalChoice(idBook, numParagraph);
               if(!isIncond) {
                   request.setAttribute("errorIncond", 1);
                   request.setAttribute("book", bookDAO.getBook(idBook));
                   request.setAttribute("paragraph", paragraphDAO.getParagraph(idBook, numParagraph));
                   request.getRequestDispatcher("/WEB-INF/writeBook.jsp").forward(request, response);
               }
           }

           if(isIncond) {
               paragraphDAO.modifyParagraph(idBook,
                                     numParagraph,
                                     paragraphTitle,
                                     paragraphContent,
                                     author,
                                     isEnd,
                                     isValidate,
                                     isAccess);
                String[] choices = request.getParameterValues("choice");
                String[] conditions = request.getParameterValues("condition");
                String[] alreadyExist = request.getParameterValues("isAlreadyExist");
                if (choices != null){
                     for(int i = 0; i < choices.length; i++) {
                         if(alreadyExist[i].equals("true")){
                             int numExist = Integer.parseInt(choices[i]);
                             choiceDAO.addChoice(idBook, numParagraph, numExist, Integer.parseInt(conditions[i]));
                         } else {
                         int lastNumParagraph = paragraphDAO.getCurrentMaxNumParagraph(idBook);
                         paragraphDAO.addParagraph(idBook,
                                                   lastNumParagraph + i + 1,
                                                   choices[i],
                                                   "La suite de l'histoire n'a pas encore été écrite",
                                                   author,
                                                   false,
                                                   false,
                                                   true);
                          choiceDAO.addChoice(idBook, numParagraph, lastNumParagraph + i +1, Integer.parseInt(conditions[i]));
                         }
                     }
                }
                userEditingParagraphDAO.deleteEditing(idBook, numParagraph);
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
       }

       private void actionCancelEditParagraph(HttpServletRequest request, HttpServletResponse response,
                            UserEditingParagraphDAO userEditingParagraphDAO, ParagraphDAO paragraphDAO, UserDAO userDAO) throws IOException{

                int idBook = Integer.parseInt(request.getParameter("idB"));
                int numParagraph = Integer.parseInt(request.getParameter("idP"));
                String login = (String) request.getSession().getAttribute("utilisateur");
                int idUser = userDAO.getIdFromLogin(login);
                if(userEditingParagraphDAO.getParagraph(idUser) != null){
                    paragraphDAO.unlockParagraph(idBook, numParagraph);
                    userEditingParagraphDAO.deleteEditing(idUser);
                }
                response.sendRedirect("controleur?action=edition");
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
           int numParagraphConditional = -1;
           String author = (String) request.getSession().getAttribute("utilisateur");
           boolean isNew = Boolean.parseBoolean(request.getParameter("isNew"));
           boolean isError = false;
           if (isNew) {
                 String choiceText = request.getParameter("choiceText");
                 if(request.getParameter("confirmation") == null) { // On est pas dans le cas de la confirmation qu'on veut créer un paragraphe du même titre
                     isError = paragraphDAO.isParagraphWithThisTitle(idBook, choiceText);
                 }
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
                                              true);
               }
           } else { // on relie à un paragraphe déjà existant
               numNextParagraph = Integer.parseInt(request.getParameter("numNextParagraph"));
           }
           if(!isError) {
                if(request.getParameter("isConditional") != null) {
                    numParagraphConditional = Integer.parseInt(request.getParameter("conditionalToWhich"));
                }
                choiceDAO.addChoice(idBook, numParagraph, numNextParagraph, numParagraphConditional);
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
       boolean isPublished = Boolean.parseBoolean(request.getParameter("isPublished"));
       boolean isError = !(bookDAO.inversePublication(idBook, !isPublished));
       int pubCode = (isError)? -1 : ((isPublished)? 0 : 1); // 1 = publiée avec succès, 0 = dépubliée avec succès, -1 = échec
       request.setAttribute("pubCode", pubCode);
       request.setAttribute("book", bookDAO.getBook(idBook));
       request.setAttribute("para", paragraphDAO.getParagraph(idBook, idPara));
       request.getRequestDispatcher("/WEB-INF/bookBeingEdit.jsp").forward(request, response);
    }

    private void actionGetTypeOpen(HttpServletRequest request, HttpServletResponse response, BookDAO bookDAO) throws ServletException, IOException {
       int idBook = Integer.parseInt(request.getParameter("idBook"));
       boolean isOpen = bookDAO.getOpen(idBook);
       String typeOpen = (isOpen)? "ouverte" : "sur invitation";
       request.setAttribute("typeOpen", typeOpen);
    }

    private void actionDeleteBook(HttpServletRequest request, HttpServletResponse response, BookDAO bookDAO) throws ServletException, IOException {
       int idBook = Integer.parseInt(request.getParameter("idBook"));
       bookDAO.deleteBook(idBook);
       actionEdition(request, response, bookDAO);
    }
}
