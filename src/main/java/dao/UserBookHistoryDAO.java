package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.Book;
import dao.ParagraphDAO;
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
    public List<Integer> getHistory(int idBook, int idUser) {
        List<Integer> result = new ArrayList<Integer>();
        try (
	     Connection conn = getConn();
             PreparedStatement st = conn.prepareStatement
	       ("SELECT * FROM History WHERE idBook = ? AND idUser = ?");
            ) {
            st.setInt(1, idBook);
            st.setInt(2, idUser);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
               String str = rs.getString("history");
               String[] listeParaId = str.split(" ");
               for(String idStr : listeParaId) {
                   result.add(Integer.parseInt(idStr));
               }
               return result;
            } else {
                throw new DAOException("Erreur BD : idBook = " + idBook + "idUser" + idUser + " n'est pas dans la base history.");
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans UserBookHistoryDAO (getHistory) " + e.getMessage(), e);
	}
    }

    /**
     * Ajoute une historique d'un livre à un utilisateur.
     * numJump doit être initié à la valeur du premier paragraphe.
     */
    public void addHistory(int idBook, int idUser, String history, int numJump) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO History (idBook, idUser, history, numJump"
                       + "VALUES (?, ?, ?, ?)");
	     ) {
            st.setInt(1, idBook);
            st.setInt(2, idUser);
            st.setString(3, history);
            st.setInt(4, numJump);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans UserBookHistoryDAO (addHistory) " + e.getMessage(), e);
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
	       ("UPDATE History SET history = CONCAT(history, ?) WHERE idBook = ? AND idUser = ?");
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