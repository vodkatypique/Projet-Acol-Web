package controleur;

import dao.DAOException;
import dao.BookDAO;
import dao.ChoiceDAO;
import dao.ParagraphDAO;
import dao.UserAccessDAO;
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
        
        try {
            // actions depuis la page ppale = liste des livres disponibles
            if (action == null || action.equals("accueil")) {
                actionDisplay(request, response, bookDAO);
            } else if (action.equals("getBook")){
                actionGetBook(request, response, bookDAO);
            } else if (action.equals("getParagraph")){
                actionGetParagraph(request, response, paragraphDAO);
            } else if (action.equals("access")){
                actionGetAccess(request, response, userDAO, userAccessDAO);
            } else if (action.equals("authors")){
                actionGetAuthors(request, response, paragraphDAO);
            } else if (action.equals("edition")){
                actionEdit(request, response, bookDAO);
            } else if (action.equals("read")){
                actionRead(request, response, bookDAO, paragraphDAO);
            } else if (action.equals("getChoices")){
                actionChoices(request, response, choiceDAO);
            } else if (action.equals("writeBook")){
                actionWriteBook(request, response);
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

        
        if (action.equals("createNewBook")) {
            actionCreateNewBook(request, response, bookDAO);
        }else if(action.equals("createParagraph")) {
            actionCreateParagraph(request, response, paragraphDAO, choiceDAO);
        
  
        } else {
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
    
    private void actionEdit(HttpServletRequest request, 
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
                               BookDAO bookDAO) throws DAOException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String v = request.getParameter("view");
        if (v.equals("listBooksToRead") || v.equals("listBooksToEdit")) {
            Book book = bookDAO.getBook(id);
            request.setAttribute("book", book);
            getServletContext().getRequestDispatcher("/WEB-INF/" + v + ".jsp").forward(request, response);
        } else if (v.equals("edit")) {
            
        }
        else invalidParameters(request, response);
    }
    
    private void actionGetParagraph(HttpServletRequest request, HttpServletResponse response, 
                               ParagraphDAO paragraphDAO) throws DAOException, ServletException, IOException {
        int idB = Integer.parseInt(request.getParameter("idBook"));
        int idP = Integer.parseInt(request.getParameter("idPara"));
        String v = request.getParameter("view");
        if (v.equals("listBooksToRead") || v.equals("listBooksToEdit")) {
            Paragraph para = paragraphDAO.getParagraph(idB, idP);
            request.setAttribute("paragraph", para);
            //getServletContext().getRequestDispatcher("/WEB-INF/" + v + ".jsp").forward(request, response); 
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
    
    private void actionRead(HttpServletRequest request, 
        HttpServletResponse response, BookDAO bookDAO, ParagraphDAO paragraphDAO) throws ServletException, IOException {
    int idB = Integer.parseInt(request.getParameter("idBook"));
    Book book = bookDAO.getBook(idB);
    int idP = Integer.parseInt(request.getParameter("idPara"));
    Paragraph para = paragraphDAO.getParagraph(idB, idP);
    request.setAttribute("bookBeingRead", book);
    request.setAttribute("paragraphBeingRead", para);
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
    }

    private void actionCreateNewBook(HttpServletRequest request, HttpServletResponse response, BookDAO bookDAO) throws ServletException, IOException{
        String title = request.getParameter("title");
        Book book = bookDAO.addBook(title);
        request.setAttribute("book", book);
        request.getRequestDispatcher("/WEB-INF/writeBook.jsp").forward(request, response);
    }
    
  
    private void actionCreateParagraph(HttpServletRequest request, HttpServletResponse response, ParagraphDAO paragraphDAO, ChoiceDAO choiceDAO) throws ServletException, IOException{
           // TODO not yet implemented
           int idBook = Integer.parseInt(request.getParameter("idBook"));
           int numParagraph = paragraphDAO.getCurrentMaxNumParagraph(idBook) + 1;
           String paragraphTitle = request.getParameter("paragraphTitle");
           String paragraphContent = request.getParameter("paragraphContent");
           String author = (String) request.getSession().getAttribute("utilisateur");
            // TO DO  Ajouter les booléens au formulaire
           boolean isEnd = false;
           boolean isValidate = false;
           boolean isAccess = false;
           paragraphDAO.addParagraph(idBook,
                                     numParagraph,
                                     paragraphTitle,
                                     paragraphContent, 
                                     author, 
                                     isEnd, 
                                     isValidate, 
                                     isAccess);
           String[] choices = request.getParameterValues("choice");
           for(int i = 0; i < choices.length; i++) {
               paragraphDAO.addParagraph(idBook, 
                                         numParagraph + i + 1, 
                                         choices[i], 
                                         "La suite de l'histoire n'a pas encore été écrite", 
                                         author, 
                                         false, 
                                         false, 
                                         false);
                choiceDAO.addChoice(idBook, numParagraph, numParagraph + i +1, 0); // TO DO choix disponible avec condition
           }
           
           
    }
}
