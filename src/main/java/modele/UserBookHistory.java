package modele;

public class UserBookHistory {
    
   private final int idBook;
   private int idUser; 
   private String history;
   private int numJump;

   public UserBookHistory(int idBook, int idUser, String history, int numJump) {
      this.idBook = idBook ;
      this.idUser = idUser;
      this.history = history;
      this.numJump = numJump;
   }

   public int getIdBook() {
      return this.idBook; 
   }
   
   public int getIdUser() {
      return this.idUser; 
   }

   public String getHistory() {
      return this.history;
   }
   
   public int getNumJump()
   {
       return this.numJump;
   }

    @Override
    public String toString() {
        return "UserBookHistory{" + "idBook=" + idBook + " idUser=" + 
                idUser + " history=" + history + " numJump=" + numJump + '}';
    }
    
}
