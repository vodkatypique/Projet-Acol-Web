      
package modele; 

public class User {

   private final int idUser;
   private String login; 
   private String password;

   public User(int id, String login, String password) {
      this.idUser = id;
      this.login = login;
      this.password = password;
   }

   public int getId() {
      return this.idUser; 
   }
   
   public String getLogin() {
      return this.login; 
   }

   public String getPassword() {
      return this.password;
   }
  

    @Override
    public String toString() {
        return "User{" + "id=" + idUser + " login=" + login + '}';
    }
}

