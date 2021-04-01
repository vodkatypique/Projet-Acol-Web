      
package modele; 

public class Livre {

   private final int idLivre;
   private String titreLivre; 
   private boolean isPublished;
   private boolean isOpen;

   public Livre(int id, String titre, boolean isPublished, boolean isOpen) {
      this.titreLivre = titre;
      this.idLivre = id ;
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
      return this.titreLivre;
   }
   
   public int getId()
   {
       return this.idLivre;
   }

    @Override
    public String toString() {
        return "Livre{" + "id=" + idLivre + " titre=" + titreLivre + '}';
    }
}

