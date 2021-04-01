      
package modele; 

public class Livre {

   private final int id;
   private final String auteur; 
   private final String titre; 

   public Livre(int id, String auteur, String titre) {
      this.titre = titre;
      this.auteur = auteur; 
      this.id = id ;
   }

   public String getAuteur() {
      return this.auteur; 
   }

   public String getTitre() {
      return this.titre;
   }
   
   public int getId()
   {
       return this.id;
   }

    @Override
    public String toString() {
        return "Ouvrage{" + "id=" + id + ", auteur=" + auteur + ", titre=" + titre + '}';
    }
}

