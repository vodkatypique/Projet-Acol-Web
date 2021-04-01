package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.Livre;

public class LivreDAO extends AbstractDataBaseDAO {

    public LivreDAO(DataSource ds) {
        super(ds);
    }

    /**
     * Renvoie la liste des ouvrages de la table bibliographie.
     */
    public List<Livre> getListeLivre() {
        List<Livre> result = new ArrayList<Livre>();
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Book");
            while (rs.next()) {
                Livre livre =
                    new Livre(rs.getInt("idLivre"), rs.getString("titreLivre"), rs.getBoolean("isPublished"), rs.getBoolean("isOpen"));
                result.add(livre);
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
    public void ajouterLivre(String titre) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO Book (titreLivre, isPublished, isOpen) VALUES (?, ?, ?)");
	     ) {
            st.setString(1, titre);
            st.setBoolean(2, false);
            st.setBoolean(2, false);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * Récupère l'ouvrage d'identifiant id dans la table bibliographie.
     */
    public Livre getLivre(int id) {
        try (
	     Connection conn = getConn();
             PreparedStatement st = conn.prepareStatement
	       ("SELECT * FROM Book WHERE idLivre = ?");
            ) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
               return new Livre(rs.getInt("idLivre"), rs.getString("titreLivre"), rs.getBoolean("isPublished"), rs.getBoolean("isOpen"));
            } else {
                throw new DAOException("Erreur BD : id = " + id +" n'est pas dans la base.");
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
    }

    /**
     * Modifie l'ouvrage d'identifiant id avec le nouvel auteur et le nouveau
     * titre spécifiés dans la table bibliographie.
     */
    public void modifierLivre(int id, String titre, boolean isPublished, boolean isOpen) {
        String error;
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("UPDATE Book SET titreLivre = ? , isPublished = ?, isOpen = ? WHERE idLivre = ?");
	     ) {
            st.setString(1, titre);
            st.setBoolean(2, isPublished);
            st.setBoolean(3, isOpen);
            st.setInt(4, id);
            error = st.toString();
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    

    /**
     * Supprime l'ouvrage d'identifiant id dans la table bibliographie.
     */
    public void supprimerLivre(int id) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("DELETE FROM Book WHERE idLivre = ?");
	     ) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
}
