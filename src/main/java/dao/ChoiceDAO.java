package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.Book;
import modele.Choice;
import modele.Paragraph;

public class ChoiceDAO extends AbstractDataBaseDAO {
    
    public ChoiceDAO(DataSource ds) {
        super(ds);
    }

    /**
     * Renvoie la liste des choix sous forme de paragraphes, pour le paragraphe du livre courrant
     */
    public List<Paragraph> getListChoices(int idBook, int numParagraphCurrent) {
        List<Paragraph> result = new ArrayList<Paragraph>();
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("SELECT * FROM Choice JOIN Paragraph ON Choice.idBook = Paragraph.idBook AND Choice.numParagraphNext = Paragraph.numParagraph WHERE Choice.idBook = ? AND numParagraphCurrent = ?");
	     ) {
            st.setInt(1, idBook);
            st.setInt(2, numParagraphCurrent);
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
            throw new DAOException("Erreur BD dans ChoiceDAO (getListChoices) " + e.getMessage(), e);
	}
	return result;
    }
    
    public List<Paragraph> getListPredecessorChoices(int idBook, int numParagraphCurrent) {
        List<Paragraph> result = new ArrayList<Paragraph>();
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("SELECT * FROM Choice JOIN Paragraph ON Choice.idBook = Paragraph.idBook AND Choice.numParagraphCurrent = Paragraph.numParagraph WHERE Choice.idBook = ? AND numParagraphNext = ?");
	     ) {
            st.setInt(1, idBook);
            st.setInt(2, numParagraphCurrent);
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
            throw new DAOException("Erreur BD dans ChoiceDAO (getListChoices) " + e.getMessage(), e);
	}
	return result;
    }

    /**
     * Ajoute un choix à un paragraphe d'un livre.
     */
    public void addChoice(int idBook, int current, int next, int conditional) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO Choice (idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional) "
                       + "VALUES (?, ?, ?, ?)");
	     ) {
            st.setInt(1, idBook);
            st.setInt(2, current);
            st.setInt(3, next);
            st.setInt(4, conditional);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans ChoiceDAO (addChoice) " + e.getMessage(), e);
        }
    }

    /**
     * Modifie le choix conditionnel d'un paragraphe précis dans un livre prédéterminé
     */
    public void modifyChoice(int idBook, int numParagraphCurrent, int numParagraphNext, int numParagraphConditional) {
        String error;
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("UPDATE Choice SET numParagraphConditional = ? WHERE idBook = ? AND numParagraphCurrent = ? AND numParagraphNext = ?");
	     ) {
            st.setInt(1, numParagraphConditional);
            st.setInt(2, idBook);
            st.setInt(3, numParagraphCurrent);
            st.setInt(4, numParagraphNext);
            error = st.toString();
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans ChoiceDAO (modifyChoice) " + e.getMessage(), e);
        }
    }
    
    
    /**
     * Supprime le choix d'un paragraphe.
     */
    public void suppressChoice(int idBook, int current, int next) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("DELETE FROM Choice WHERE idBook = ? AND numParagraphCurrent = ? AND numParagraphNext = ?");
	     ) {
            st.setInt(1, idBook);
            st.setInt(2, current);
            st.setInt(3, next);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans ChoiceDAO (suppressChoice) " + e.getMessage(), e);
        }
    }
    
    public boolean isAlreadyHere(int idBook, int current, int next) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("SELECT * FROM Choice WHERE idBook = ? AND numParagraphCurrent = ? AND numParagraphNext = ?");
	     ) {
            st.setInt(1, idBook);
            st.setInt(2, current);
            st.setInt(3, next);
            ResultSet rs = st.executeQuery();
            return rs.next();
            
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans ChoiceDAO (isAlreadyHere) " + e.getMessage(), e);
        }
    }
    
    public boolean isAnyInconditionalChoice(int idBook, int idPara) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("SELECT * FROM Choice WHERE idBook = ? AND numParagraphCurrent = ? AND numParagraphConditional = -1");
	     ) {
            st.setInt(1, idBook);
            st.setInt(2, idPara);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans ChoiceDAO (isAnyInconditionalChoice) " + e.getMessage(), e);
        }
    }
    
        public boolean isDeletable(int idBook, int idParaNext) {
                // Idée : On regarde tous les paragraphes non finaux pr lesquels idParaNext est un paragraphe inconditionnel
                // Puis on regarde pour chacun de ces paragraphes s'ils y a d'autres choix inconditionnels
        try (
            Connection conn = getConn();
            PreparedStatement st = conn.prepareStatement
                ("SELECT P.numParagraph FROM Choice C JOIN Paragraph P " +
"                 ON C.idBook = P.idBook AND C.numParagraphCurrent = P.numParagraph " +
"                 WHERE C.idBook = ? AND C.numParagraphNext = ? AND C.numParagraphConditional = -1 AND P.isEnd = 0"); 
            PreparedStatement st2 = conn.prepareStatement
                ("SELECT *  FROM Choice " +
"                WHERE idBook = ? AND numParagraphConditional = -1 AND numParagraphNext != ? AND numParagraphCurrent = ?"); )
        {
                 st.setInt(1, idBook);
                 st.setInt(2, idParaNext);
                 //st2.setInt(1, idBook);
                 //st2.setInt(2, idParaNext);
                 ResultSet rs = st.executeQuery();
                 while(rs.next()) {
                     int numCur = rs.getInt("numParagraph");
                     st2.setInt(1, idBook);
                     st2.setInt(2, idParaNext);
                     st2.setInt(3, numCur);
                     ResultSet rs2 = st2.executeQuery();
                     if(!(rs.next())) { // S'il n'y a pas d'autres choix inconditionnels pour ce paragraphe là, c'est mort (pas supprimable)
                         return false;
                     }
                 }
                return true;
            
        } catch (SQLException e) {
            throw new DAOException("Erreur BD dans ChoiceDAO (isDeletable) " + e.getMessage(), e);
        }    
    }    
}
