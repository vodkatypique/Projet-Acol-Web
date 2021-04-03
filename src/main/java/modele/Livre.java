      
package modele; 

public class Livre {

   private final int idBook;
   private String titleBook; 
   private boolean isPublished;
   private boolean isOpen;

   public Livre(int id, String titre, boolean isPublished, boolean isOpen) {
      this.idBook = id ;
      this.titleBook = titre;
      this.isPublished = isPublished;
      this.isOpen = isOpen;
   }

   public boolean getPublishedStatut() {
      return this.isPublished; 
   }
   
   public boolean getOpenStatut() {
      return this.isOpen; 
   }

   public String getTitre() {
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

