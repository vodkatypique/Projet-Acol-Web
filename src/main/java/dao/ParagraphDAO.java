package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.Book;
import modele.Paragraph;

public class ParagraphDAO extends AbstractDataBaseDAO {

    public ParagraphDAO(DataSource ds) {
        super(ds);
    }

    /**
     * Renvoie la liste des ouvrages de la table bibliographie.
     */
    public List<Paragraph> getListParagraphs(int idBook) {
        List<Paragraph> result = new ArrayList<Paragraph>();
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("SELECT * FROM Paragraph WHERE idBook = ?");
	     ) {
            st.setInt(1, idBook);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Paragraph Paragraph =
                    new Paragraph(
                            rs.getInt("idBook"),
                            rs.getInt("numParagraph"),
                            rs.getString("paragraphTitle"),
                            rs.getString("text"),
                            rs.getString("author"),
                            rs.getBoolean("isEnd"),
                            rs.getBoolean("isValidate"),
                            rs.getBoolean("isAccessible")
                    );
                result.add(Paragraph);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans ParagraphDAO (getListParagraphs)" + e.getMessage(), e);
	}
	return result;
    }

    /**
     * Ajoute l'ouvrage d'auteur et de titre spécifiés dans la table
     * bibliographie.
     */
    public void addParagraph(int idBook, int numParagraph, String title, String text, String author, boolean isEnd, boolean isValidate, boolean isAccess) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO Paragraph (idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
	     ) {
            st.setInt(1, idBook);
            st.setInt(2, numParagraph);
            st.setString(3, title);
            st.setString(4, text);
            st.setString(5, author);
            st.setBoolean(6, isEnd);
            st.setBoolean(7, isValidate);
            st.setBoolean(8, isAccess);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans ParagraphDAO (addParagraph) " + e.getMessage(), e);
        }
    }

    /**
     * Récupère l'ouvrage d'identifiant id dans la table bibliographie.
     */
    public Paragraph getParagraph(int idBook, int idParagraph) {
        try (
	     Connection conn = getConn();
             PreparedStatement st = conn.prepareStatement
	       ("SELECT * FROM Paragraph WHERE idBook = ? AND numParagraph = ?");
            ) {
            st.setInt(1, idBook);
            st.setInt(2, idParagraph);
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
                throw new DAOException("Erreur BD : idBook = " + idBook + "idPara = " + idParagraph +" n'est pas dans la base.");
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans ParagraphDAO (getParagraph) " + e.getMessage(), e);
	}
    }

    /**
     * Modifie l'ouvrage d'identifiant id avec le nouvel auteur et le nouveau
     * titre spécifiés dans la table bibliographie.
     */
    public void modifyParagraph(int idBook, int numParagraph, String title, String text, String author, boolean isEnd, boolean isValidate, boolean isAccess) {
        String error;
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("UPDATE Paragraph SET paragraphTitle = ?, text = ?, author = ?, isEnd = ?, isValidate = ?, isAccessible = ? WHERE idBook = ? AND numParagraph = ?");
	     ) {
            st.setString(1, title);
            st.setString(2, text);            
            st.setString(3, author);
            st.setBoolean(4, isEnd);
            st.setBoolean(5, isValidate);
            st.setBoolean(6, isAccess);
            st.setInt(7, idBook);
            st.setInt(8, numParagraph);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans ParagraphDAO (modifyParagraph)" + e.getMessage(), e);
        }
    }
    
    /**
     * Verrouille un paragraphe indiquant qu'il est en mode édition par un utilsateur
     */
    public void lockParagraph(int idBook, int numParagraph) {
        String error;
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("UPDATE Paragraph SET isAccessible = 0 WHERE idBook = ? AND numParagraph = ?");
	     ) {
            st.setInt(1, idBook);   
            st.setInt(2, numParagraph);
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans ParagraphDAO (lockParagraph)" + e.getMessage(), e);
        }
    }
    
        /**
     * Déverouille un paragraphe quand un utilisateur à fini de l'éditer
     */
    public void unlockParagraph(int idBook, int numParagraph) {
        String error;
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("UPDATE Paragraph SET isAccessible = 1 WHERE idBook = ? AND numParagraph = ?");
	     ) {
            st.setInt(1, idBook);   
            st.setInt(2, numParagraph);
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans ParagraphDAO (unlockParagraph)" + e.getMessage(), e);
        }
    }
    
    

    /**
     * Supprime l'ouvrage d'identifiant id dans la table bibliographie.
     */
    public void deleteParagraph(int idBook, int idPara) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("DELETE FROM Paragraph WHERE idBook = ? AND numParagraph = ?");
	     ) {
            st.setInt(1, idBook);
            st.setInt(2, idPara);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans ParagraphDAO (suppressParagraph)" + e.getMessage(), e);
        }
    }
    
    /**
     * Indique si l'utilisateur d'identifiant donné à accès au livre 
     * d'identifiant donné en écriture
     */
    public List<String> findAuthors(int idBook) {
        List<String> result = new ArrayList<String>();
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("SELECT DISTINCT Author FROM Paragraph WHERE idBook=?");
	     ) {
            st.setInt(1, idBook);
            ResultSet r = st.executeQuery();
            while(r.next()) {
                result.add(r.getString("author"));
            }            
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans ParagraphDAO (findAuthors) " + e.getMessage(), e);
        }
        return result;
    }
    
    public int getCurrentMaxNumParagraph(int idBook){
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("SELECT MAX(numParagraph) FROM Paragraph WHERE idBook=?");
	     ) {
            st.setInt(1, idBook);
            ResultSet r = st.executeQuery();
            while(r.next()) {
                return r.getInt("MAX(NUMPARAGRAPH)");
            }
            return -1; // book is empty
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans ParagraphDAO (findAuthors) " + e.getMessage(), e);
        }
    }
    
    public boolean isParagraphWithThisTitle(int idBook, String text) {
        try (
	     Connection conn = getConn();
             PreparedStatement st = conn.prepareStatement
	       ("SELECT * FROM Paragraph WHERE idBook = ? AND paragraphTitle = ?");
            ) {
            st.setInt(1, idBook);
            st.setString(2, text);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans ParagraphDAO (isParagraphWithThisTitle) " + e.getMessage(), e);
	}
    }
}
