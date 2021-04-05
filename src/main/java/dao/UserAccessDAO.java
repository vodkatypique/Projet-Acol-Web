package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class UserAccessDAO extends AbstractDataBaseDAO {

    public UserAccessDAO(DataSource ds) {
        super(ds);
    }

    /**
     * Indique si l'utilisateur d'identifiant donné à accès au livre 
     * d'identifiant donné en écriture
     */
    public boolean accessBook(int idBook, int idUser) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("SELECT * FROM userAccess WHERE idBook=? AND idUser=?");
	     ) {
            st.setInt(1, idBook);
            st.setInt(2, idUser);
            ResultSet r = st.executeQuery();
            return r.next(); // true ssi il existe un accès
            
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans UserAccessDAO (accessBook)" + e.getMessage(), e);
        }       
    }
    
    public void addNewAccess(int idBook, int idUser) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO UserAccess (idBook, idUser) VALUES (?, ?)");
	     ) {
            st.setInt(1, idBook);
            st.setInt(2, idUser);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD  dans UserAccessDAO (addNewAccess) " + e.getMessage(), e);
        }
    }
    
    public List<Integer> getAllUsersAllowed(int idBook) {
        List<Integer> list = new ArrayList<Integer>();
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("SELECT * FROM userAccess WHERE idBook=?");
	     ) {
            st.setInt(1, idBook);
            ResultSet r = st.executeQuery();
            while(r.next()){
               list.add(r.getInt("idUser"));
            }
            return list;
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans UserAccessDAO (getAllUsersAllowed)" + e.getMessage(), e);
        }       
    }
}
