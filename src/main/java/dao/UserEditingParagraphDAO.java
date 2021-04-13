package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.Book;
import dao.ParagraphDAO;
import modele.Paragraph;
import modele.UserBookHistory;


public class UserEditingParagraphDAO extends AbstractDataBaseDAO {
    
    public UserEditingParagraphDAO(DataSource ds) {
        super(ds);
    }

    /**
     * Renvoie le paragraphe qu'est en train d'éditer idUser.
     */
    public Paragraph getParagraph(int idUser) {
        List<Integer> result = new ArrayList<Integer>();
        try (
	     Connection conn = getConn();
             PreparedStatement st = conn.prepareStatement
	       ("SELECT * FROM UserEditingParagraph JOIN Paragraph ON "
                       + "UserEditingParagraph.idBook = Paragraph.IdBook "
                       + "AND UserEditingParagraph.numParagraph = Paragraph.numParagraph "
                                    + "WHERE idUser = ?");
            ) {
            st.setInt(1, idUser);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                return new Paragraph(
                            rs.getInt("idBook"),
                            rs.getInt("numParagraph"),
                            rs.getString("paragraphTitle"),
                            rs.getString("text"),
                            rs.getString("author"),
                            rs.getBoolean("isEnd"),
                            rs.getBoolean("isValidate"),
                            rs.getBoolean("isAccessible")
                    );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans UserEditingParagraphDAO (GetParagraph) " + e.getMessage(), e);
	}
    }

    /**
     * Renvoie le paragraphe (IdBook, numParagraph) si il est édité par idUser.
     */
    public Paragraph getParagraph(int idUser, int idBook, int numParagraph) {
        List<Integer> result = new ArrayList<Integer>();
        try (
                 Connection conn = getConn();  PreparedStatement st = conn.prepareStatement("SELECT * FROM UserEditingParagraph JOIN Paragraph ON "
                        + "UserEditingParagraph.idBook = Paragraph.IdBook "
                        + "AND UserEditingParagraph.numParagraph = Paragraph.numParagraph "
                        + "WHERE idUser = ? AND Paragraph.numParagraph = ? AND Paragraph.idBook = ? ");) {
            st.setInt(1, idUser);
            st.setInt(2, numParagraph);
            st.setInt(3, idBook);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Paragraph(
                        rs.getInt("idBook"),
                        rs.getInt("numParagraph"),
                        rs.getString("paragraphTitle"),
                        rs.getString("text"),
                        rs.getString("author"),
                        rs.getBoolean("isEnd"),
                        rs.getBoolean("isValidate"),
                        rs.getBoolean("isAccessible")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans UserEditingParagraphDAO (GetParagraph) " + e.getMessage(), e);
        }
    }
    
    /**
     * Ajoute un ligne disant qu'un utilisateur est en train d'éditer un paragraphe.
     */
    public void addEditing(int idUser, int idBook, int numParagraph) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO UserEditingParagraph (idUser, idBook, numParagraph) "
                       + "VALUES (?, ?, ?)");
	     ) {
            st.setInt(1, idUser);
            st.setInt(2, idBook);
            st.setInt(3, numParagraph);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans UserEditingParagraphDAO (addEditing) " + e.getMessage(), e);
        }
    }
    
    /**
     * Supprime la ligne indiquant qu'un utilisateur édite un paragraphe.
     */
    public void deleteEditing(int idUser) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("DELETE FROM UserEditingParagraph WHERE idUser = ?");
	     ) {
            st.setInt(1, idUser);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans UserEditingParagraphDAO (deleteEditing)" + e.getMessage(), e);
        }
    }
    
    /**
     * Supprime la ligne indiquant qu'un utilisateur édite un paragraphe.
     */
    public void deleteEditing(int idBook, int numParagraph) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("DELETE FROM UserEditingParagraph WHERE idBook = ? AND numParagraph = ?");
	     ) {
            st.setInt(1, idBook);
            st.setInt(2, numParagraph);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans UserEditingParagraphDAO (deleteEditing)" + e.getMessage(), e);
        }
    }
    
}
