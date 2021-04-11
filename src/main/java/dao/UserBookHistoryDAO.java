package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.Book;
import dao.ParagraphDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import modele.Paragraph;
import modele.UserBookHistory;


public class UserBookHistoryDAO extends AbstractDataBaseDAO {
    
    public UserBookHistoryDAO(DataSource ds) {
        super(ds);
    }

    /**
     * Renvoie l'historique d'un utilisateur sur un livre sous la forme
     * d'une liste des numéros des différents paragraphes.
     */
    public String getHistory(int idBook, int idUser, HttpServletResponse response) throws IOException {
        List<Integer> result = new ArrayList<Integer>();
        try (
	     Connection conn = getConn();
             PreparedStatement st = conn.prepareStatement
	       ("SELECT * FROM UserBookHistory WHERE idBook = ? AND idUser = ?");
            ) {
            st.setInt(1, idBook);
            st.setInt(2, idUser);
            ResultSet rs = st.executeQuery();
            if(rs == null) {
                return "";
            }
            if(rs.next()){
               String str = rs.getString("history");
               return str;
            } else {
                try (PrintWriter out = response.getWriter()) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Download failed !</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1> Aucun historique n'a été enregistré sur votre login. </h1>");
                    out.println("<a href=\"controleur?action=accueil\">Retour à l'accueil</a>");
                    out.println("</body>");
                    out.println("</html>");
                    return "";
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans UserBookHistoryDAO (getHistory) " + e.getMessage(), e);
        }
    }

    /**
     * Ajoute un historique d'un livre à un utilisateur.
     * numJump doit être initié à la valeur du premier paragraphe.
     */
    public void addHistory(int idBook, int idUser, String history) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO UserBookHistory (idBook, idUser, history) "
                       + "VALUES (?, ?, ?)");
	     ) {
            st.setInt(1, idBook);
            st.setInt(2, idUser);
            st.setString(3, history);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans UserBookHistoryDAO (addHistory) " + e.getMessage(), e);
        }
    }
    
    /**
     * Supprime un historique d'un livre à un utilisateur.
     */
    public void suppressHistory(int idBook, int idUser) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("DELETE FROM UserBookHistory WHERE idBook = ? AND idUser = ?");
	     ) {
            st.setInt(1, idBook);
            st.setInt(2, idUser);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans UserBookHistoryDAO (suppressHistory)" + e.getMessage(), e);
        }
    }
    
    
    /**
     * Ajoute un choix à l'historique du livre d'un utilisateur.
     * A supprimer potentiellemnt TODO
     */
    public void addChoice(int idBook, int idUser, int numParagraph) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("UPDATE UserBookHistory SET UserBookHistory = CONCAT(UserBookHistory, ?) WHERE idBook = ? AND idUser = ?");
	     ) {
            st.setString(1, Integer.toString(numParagraph) + " ");
            st.setInt(2, idBook);
            st.setInt(3, idUser);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans UserBookHistoryDAO (addChoice)" + e.getMessage(), e);
        }
    }
    
    
    
}
