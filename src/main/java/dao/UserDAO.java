package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.User;

public class UserDAO extends AbstractDataBaseDAO {

    public UserDAO(DataSource ds) {
        super(ds);
    }

    /**
     * Renvoie la liste des ouvrages de la table bibliographie.
     */
    public List<User> getListUser() {
        List<User> result = new ArrayList<User>();
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM UserTable");
            while (rs.next()) {
                User user =
                    new User(rs.getInt("idUser"), rs.getString("login"), rs.getString("password"));
                result.add(user);
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
    public void addUser(String idUser, String login, String password) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO UserTable (idUser, login, password) VALUES (?, ?, ?)"); //! a hasher
	     ) {
            st.setString(1, idUser);
            st.setString(2, login);
            st.setString(3, password);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * Récupère l'ouvrage d'identifiant id dans la table bibliographie.
     */
    public User getUser(int id) {
        try (
	     Connection conn = getConn();
             PreparedStatement st = conn.prepareStatement
	       ("SELECT * FROM UserTable WHERE idUser = ?");
            ) {
            st.setString(1, Integer.toString(id));
            ResultSet rs = st.executeQuery();
            if(rs.next()){
               return new User(rs.getInt("id"), rs.getString("login"), rs.getString("password"));
            } else {
                throw new DAOException("Erreur BD : id = " + id +" n'est pas dans la base.");
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
    }

    public int getIdFromLogin(String login) {
        try (
	     Connection conn = getConn();
             PreparedStatement st = conn.prepareStatement
	       ("SELECT idUser FROM UserTable WHERE login = ?");
            ) {
            st.setString(1, login);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                System.out.println(rs.getInt("idUser"));
               return rs.getInt("idUser");
            } else {
                throw new DAOException("Erreur BD : login = " + login +" n'est pas dans la base.");
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
    }
    
    
    /**
     * Modifie l'ouvrage d'identifiant id avec le nouvel auteur et le nouveau
     * titre spécifiés dans la table bibliographie.
     */
    public void modifyUser(int id, String login, String password) {
        String error;
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("UPDATE UserTable SET login = ? , password = ? WHERE idUser = ?");
	     ) {
            st.setString(1, login);
            st.setString(2, password); //! a hasher
            st.setInt(3, id);
            error = st.toString();
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    

    /**
     * Supprime l'ouvrage d'identifiant id dans la table bibliographie.
     */
    public void suppressUser(int id) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("DELETE FROM UserTable WHERE idUser = ?");
	     ) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
}
