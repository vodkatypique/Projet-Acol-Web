package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.Book;
import modele.Paragraphe;

public class ParagrapheDAO extends AbstractDataBaseDAO {

    public ParagrapheDAO(DataSource ds) {
        super(ds);
    }

    /**
     * Renvoie la liste des ouvrages de la table bibliographie.
     */
    public List<Paragraphe> getListeParagraphe(int idLivre) {
        List<Paragraphe> result = new ArrayList<Paragraphe>();
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("SELECT * FROM Paragraph WHERE idLivre = ?");
	     ) {
            st.setInt(1, idLivre);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Paragraphe paragraphe =
                    new Paragraphe(
                            rs.getInt("idLivre"),
                            rs.getInt("numParagraph"),
                            rs.getString("titreParagraphe"),
                            rs.getString("auteur"),
                            rs.getBoolean("isEnd"),
                            rs.getBoolean("isValidate"),
                            rs.getBoolean("isAccessible")
                    );
                result.add(paragraphe);
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
    public void ajouterParagraphe(int idLivre, int numParagraph, String titre, String auteur, boolean isEnd, boolean isValidate, boolean isAccess) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO Paragraph (idLivre, numParagraph, titreParagraphe, auteur, isEnd, isValidate, isAccessible) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?)");
	     ) {
            st.setInt(1, idLivre);
            st.setInt(2, numParagraph);
            st.setString(3, titre);
            st.setString(4, auteur);
            st.setBoolean(5, isEnd);
            st.setBoolean(6, isValidate);
            st.setBoolean(7, isAccess);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * Récupère l'ouvrage d'identifiant id dans la table bibliographie.
     */
    public Paragraphe getParagrpahe(int idLivre, int idParagraph) {
        try (
	     Connection conn = getConn();
             PreparedStatement st = conn.prepareStatement
	       ("SELECT * FROM Paragraph WHERE idLivre = ?, numParagraph = ?");
            ) {
            st.setInt(1, idLivre);
            st.setInt(2, idParagraph);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
               return new Paragraphe(
                            rs.getInt("idLivre"),
                            rs.getInt("numParagraph"),
                            rs.getString("titreParagraphe"),
                            rs.getString("auteur"),
                            rs.getBoolean("isEnd"),
                            rs.getBoolean("isValidate"),
                            rs.getBoolean("isAccessible")
                    );

            } else {
                throw new DAOException("Erreur BD : idLvre = " + idLivre + "idPara = " + idParagraph +" n'est pas dans la base.");
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
    }

    /**
     * Modifie l'ouvrage d'identifiant id avec le nouvel auteur et le nouveau
     * titre spécifiés dans la table bibliographie.
     */
    public void modifierParagraph(int idLivre, int numParagraph, String titre, String auteur, boolean isEnd, boolean isValidate, boolean isAccess) {
        String error;
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("UPDATE Paragraph SET titreParagraphe = ?, auteur = ?, isEnd = ?, isValidate = ?, isAccessible = ? WHERE idLivre = ? AND numParagraph = ?");
	     ) {
            st.setString(1, titre);
            st.setString(2, auteur);
            st.setBoolean(3, isEnd);
            st.setBoolean(4, isValidate);
            st.setBoolean(5, isAccess);
            st.setInt(6, idLivre);
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
    public void supprimerOuvrage(int idLivre, int idPara) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("DELETE FROM Paragraph WHERE idLivre = ? AND numPAragraph = ?");
	     ) {
            st.setInt(1, idLivre);
            st.setInt(2, idPara);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
}
