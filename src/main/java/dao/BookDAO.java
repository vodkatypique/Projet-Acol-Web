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
                    new Book(rs.getInt("idBook"), rs.getString("titleBook"), rs.getBoolean("isPublished"), rs.getBoolean("isOpen"), rs.getString("superAuthor"));
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
    public int addBook(String titre, String superAuthor) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO Book (IdBook, titleBook, isPublished, isOpen, superAuthor) VALUES (SeqBook.NEXTVAL, ?, ?, ?, ?)");
            Statement st2 = conn.createStatement();
	     ) {
            st.setString(1, titre);
            st.setBoolean(2, false);
            st.setBoolean(3, false);
            st.setString(4, superAuthor);
            st.executeUpdate();
            /* Select this book, it is the book with the higher ID*/
            ResultSet rs = st2.executeQuery("SELECT * FROM Book ORDER BY idBook DESC  FETCH FIRST 1 ROWS ONLY");
             if(rs.next()){
               return rs.getInt("idBook");
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
               return new Book(rs.getInt("idBook"), rs.getString("titleBook"), rs.getBoolean("isPublished"), rs.getBoolean("isOpen"), rs.getString("superAuthor"));
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
    public void modifyBook(int id, String title, boolean isPublished, boolean isOpen, String superAuthor) {
        String error;
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("UPDATE Book SET titleBook = ? , isPublished = ?, isOpen = ?, superAuthor = ? WHERE idBook = ?");
	     ) {
            st.setString(1, title);
            st.setBoolean(2, isPublished);
            st.setBoolean(3, isOpen);
            st.setString(4, superAuthor);
            st.setInt(5, id);
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
    
        /**
     * Modifie l'ouvrage d'identifiant id avec le nouvel auteur et le nouveau
     * titre spécifiés dans la table bibliographie.
     */
    public boolean inversePublication(int id, boolean toSet) {        
        try (
            Connection conn = getConn();
            PreparedStatement st = conn.prepareStatement
                ("SELECT * FROM Paragraph WHERE idBook = ? AND isEnd = 1"); 
            PreparedStatement st2 = conn.prepareStatement
                ("UPDATE Book SET isPublished = ? WHERE idBook = ?"); )
        {
            if(toSet == true) { // On souhaite publier => il faut vérif qu'il existe bien un isEnd
                 st.setInt(1, id);
                 ResultSet rs = st.executeQuery();
                 if(!rs.next()) { // il n'y a pas de paragraphe de conclusion => impossible de publier
                     return false;
                 }
            }
            st2.setBoolean(1, toSet);
            st2.setInt(2, id);
            st2.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans BookDAO (inversePublication) " + e.getMessage(), e);
        }    
    }
    
    public void makeOpen(int idBook) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("UPDATE Book SET isOpen = 1 WHERE idBook = ?");
	     ) {
            st.setInt(1, idBook);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans BookDAO (makeOpen) " + e.getMessage(), e);
        }
    }
    
    public boolean getOpen(int idBook) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement("SELECT isOpen FROM Book WHERE idBook=?");
	     ) {
            st.setInt(1, idBook);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("isOpen");
            }
            else {
                throw new DAOException("Erreur dans BookDAO (getOpen) : livre inexistant");
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans BookDAO (getOpen) " + e.getMessage(), e);
	}
    }
 }   
