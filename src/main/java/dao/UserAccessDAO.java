package dao;

import java.sql.*;
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
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }       
    }
}
