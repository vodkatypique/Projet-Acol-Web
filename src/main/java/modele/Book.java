      
package modele; 

public class Book {

   private final int idBook;
   private String titleBook; 
   private boolean isPublished;
   private boolean isOpen;

   public Book(int id, String titre, boolean isPublished, boolean isOpen) {
      this.titleBook = titre;
      this.idBook = id ;
      this.isPublished = isPublished;
      this.isOpen = isOpen;
   }

   public boolean getIsPublished() { // besoin d'avoir le mm nom pour utiliser les $ jsp
      return this.isPublished; 
   }
   
   public boolean getOpenStatut() {
      return this.isOpen; 
   }

   public String getTitle() {
      return this.titleBook;
   }
   
   public int getId()
   {
       return this.idBook;
   }

    @Override
    public String toString() {
        return "Livre{" + "id=" + idBook + " titre=" + titleBook + '}';
    }
}

