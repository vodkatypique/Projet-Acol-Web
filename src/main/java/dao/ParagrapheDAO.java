package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.Livre;
import modele.Paragraphe;

public class ParagrapheDAO extends AbstractDataBaseDAO {

    public ParagrapheDAO(DataSource ds) {
        super(ds);
    }

    /**
     * Renvoie la liste des ouvrages de la table bibliographie.
     */
    public List<Paragraphe> getListeParagraph(int idBook) {
        List<Paragraphe> result = new ArrayList<Paragraphe>();
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("SELECT * FROM Paragraph WHERE idBook = ?");
	     ) {
            st.setInt(1, idBook);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Paragraphe Paragraph =
                    new Paragraphe(
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
            throw new DAOException("Erreur BD " + e.getMessage(), e);
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
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * Récupère l'ouvrage d'identifiant id dans la table bibliographie.
     */
    public Paragraphe getParagraph(int idBook, int idParagraph) {
        try (
	     Connection conn = getConn();
             PreparedStatement st = conn.prepareStatement
	       ("SELECT * FROM Paragraph WHERE idBook = ?, numParagraph = ?");
            ) {
            st.setInt(1, idBook);
            st.setInt(2, idParagraph);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
               return new Paragraphe(
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
                throw new DAOException("Erreur BD : idLvre = " + idBook + "idPara = " + idParagraph +" n'est pas dans la base.");
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
    }

    /**
     * Modifie l'ouvrage d'identifiant id avec le nouvel auteur et le nouveau
     * titre spécifiés dans la table bibliographie.
     */
    public void modifyParagraph(int idBook, int numParagraph, String title, String author, boolean isEnd, boolean isValidate, boolean isAccess) {
        String error;
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("UPDATE Paragraph SET paragraphTitle = ?, author = ?, isEnd = ?, isValidate = ?, isAccessible = ? WHERE idLivre = ? AND numParagraph = ?");
	     ) {
            st.setString(1, title);
            st.setString(2, author);
            st.setBoolean(3, isEnd);
            st.setBoolean(4, isValidate);
            st.setBoolean(5, isAccess);
            st.setInt(6, idBook);
            st.setInt(7, numParagraph);
            error = st.toString();
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    

    /**
     * Supprime l'ouvrage d'identifiant id dans la table bibliographie.
     */
    public void suppressParagraph(int idBook, int idPara) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("DELETE FROM Paragraph WHERE idBook = ? AND numParagraph = ?");
	     ) {
            st.setInt(1, idBook);
            st.setInt(2, idPara);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
}
