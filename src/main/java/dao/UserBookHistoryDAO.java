package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.Book;
import modele.Choice;
import modele.Paragraphe;
import modele.UserBookHistory;


public class UserBookHistoryDAO extends AbstractDataBaseDAO {
    
    public UserBookHistoryDAO(DataSource ds) {
        super(ds);
    }

    /**
     * Renvoie l'historique d'un utilisateur sur un livre.
     */
    public String getHistory(int idBook, int idUser) {
        // TODO
        return "";
    }

    /**
     * Ajoute un choix à l'historique du livre d'un utilisateur.
     */
    public void addChoice(int idBook, int idUser, int numParagraph) {
        // TODO
    }

    
}
