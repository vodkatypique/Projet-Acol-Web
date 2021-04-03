package controleur;

import dao.DAOException;
import dao.BookDAO;
import java.io.*;
import java.net.http.HttpClient;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import modele.Book;

/**
 * Le contrôleur de l'application.
 */
@WebServlet(name = "Controleur", urlPatterns = {"/controleur"})
public class Controleur extends HttpServlet {

    @Resource(name = "jdbc/Book")
    private DataSource dsBook;
    
    @Resource(name = "jdbc/UserTable")
    private DataSource dsUser;

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

        try {
            // actions depuis la page ppale = liste des livres disponibles
            if (action == null) {
                actionDisplay(request, response, bookDAO);
            } else if (action.equals("getBook")){
                actionGetBook(request, response, bookDAO);
            } else if (action.equals("access")){
                actionAccess(request, response, bookDAO);
            } else if (action.equals("authors")){
                actionAuthors(request, response, bookDAO);
            } else {
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
        request.getSession().invalidate();

        try {
            if (action == null) {
                actionIndex(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
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

    /**
     * 
     * Affiche la page d’accueil avec la liste de tous les ouvrages. 
     */
    private void actionGetBook(HttpServletRequest request, HttpServletResponse response, 
                               BookDAO bookDAO) throws DAOException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String v = request.getParameter("view");
        if (v.equals("read")) {
            Book book = bookDAO.getBook(id);
            request.setAttribute("book", book);
            getServletContext().getRequestDispatcher("/WEB-INF/" + v + ".jsp").forward(request, response); // Peut être des .html plutôt ? (et pas besoin d'une var si on rajoute pas d'autre trucs que read)
        }
        else invalidParameters(request, response);
    }
    
     private void actionAccess(HttpServletRequest request, 
            HttpServletResponse response, 
            BookDAO bookDAO) throws ServletException, IOException {
         int iB = Integer.parseInt(request.getParameter("idBook"));
         int iU = Integer.parseInt(request.getParameter("idUser"));
         boolean is = bookDAO.accessBook(iB, iU);
         request.setAttribute("isAccess", is);
     }
     
    private void actionAuthors(HttpServletRequest request, 
            HttpServletResponse response, 
            BookDAO bookDAO) throws ServletException, IOException {
         int iB = Integer.parseInt(request.getParameter("idBook"));
         List<String> authors = bookDAO.findAuthors(iB);
         request.setAttribute("authors", authors);
     }
}
