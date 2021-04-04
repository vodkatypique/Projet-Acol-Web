package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.Book;

public class BookDAO extends AbstractDataBaseDAO {

    public BookDAO(DataSource ds) {
        super(ds);
    }

    /**
     * Renvoie la liste des ouvrages de la table bibliographie.
     */
    public List<Book> getBooksList() {
        List<Book> result = new ArrayList<Book>();
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Book");
            while (rs.next()) {
                Book livre =
                    new Book(rs.getInt("idBook"), rs.getString("titleBook"), rs.getBoolean("isPublished"), rs.getBoolean("isOpen"));
                result.add(livre);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans BookDAO (getBooksList) " + e.getMessage(), e);
	}
	return result;
    }

    /**
     * Ajoute l'ouvrage d'auteur et de titre spécifiés dans la table
     * bibliographie.
     */
    public Book addBook(String titre) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO Book (IdBook, titleBook, isPublished, isOpen) VALUES (SeqBook.NEXTVAL, ?, ?, ?)");
            Statement st2 = conn.createStatement();
	     ) {
            st.setString(1, titre);
            st.setBoolean(2, false);
            st.setBoolean(3, false);
            st.executeUpdate();
            /* Select this book, it is the book with the higher ID*/
            ResultSet rs = st2.executeQuery("SELECT * FROM Book ORDER BY idBook DESC  FETCH FIRST 1 ROWS ONLY");
             if(rs.next()){
               return new Book(rs.getInt("idBook"), rs.getString("titleBook"), rs.getBoolean("isPublished"), rs.getBoolean("isOpen"));
            } else {
                throw new DAOException("Erreur BD le livre n'a pas été ajouté");
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD  dans BookDAO (addBook)" + e.getMessage(), e);
        }
    }

    /**
     * Récupère l'ouvrage d'identifiant id dans la table bibliographie.
     */
    public Book getBook(int id) {
        try (
	     Connection conn = getConn();
             PreparedStatement st = conn.prepareStatement
	       ("SELECT * FROM Book WHERE idBook = ?");
            ) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
               return new Book(rs.getInt("idBook"), rs.getString("titleBook"), rs.getBoolean("isPublished"), rs.getBoolean("isOpen"));
            } else {
                throw new DAOException("Erreur BD : id = " + id +" n'est pas dans la base.");
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans BookDAO (getBook) " + e.getMessage(), e);
	}
    }

    /**
     * Modifie l'ouvrage d'identifiant id avec le nouvel auteur et le nouveau
     * titre spécifiés dans la table bibliographie.
     */
    public void modifyBook(int id, String title, boolean isPublished, boolean isOpen) {
        String error;
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("UPDATE Book SET titleBook = ? , isPublished = ?, isOpen = ? WHERE idBook = ?");
	     ) {
            st.setString(1, title);
            st.setBoolean(2, isPublished);
            st.setBoolean(3, isOpen);
            st.setInt(4, id);
            error = st.toString();
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans BookDAO (modifyBook) " + e.getMessage(), e);
        }
    }



    /**
     * Supprime l'ouvrage d'identifiant id dans la table bibliographie.
     */
    public void suppressBook(int id) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("DELETE FROM Book WHERE idBook = ?");
	     ) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD  dans BookDAO (suppressBook) " + e.getMessage(), e);
        }       
    }
}
