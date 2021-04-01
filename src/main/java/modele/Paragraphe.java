      
package modele; 

public class Paragraphe {

   private final int idLivre;
   private final int numParagraph; 
   private String titreParagraphe;
   private String auteur;
   private boolean isEnd;
   private boolean isValidate;

   public Paragraphe(int id, int numParagraphe, String titre, String auteur, boolean isEnd, boolean isValidate, boolean isAccessible) {
      this.titreParagraphe = titre;
      this.auteur = auteur; 
      this.idLivre = id;
      this.numParagraph = numParagraphe;
      this.isEnd = isEnd;
      this.isValidate = isValidate;
   }

   public String getAuteur() {
      return this.auteur; 
   }

   public String getTitre() {
      return this.titreParagraphe;
   }
   
   public int getId()
   {
       return this.numParagraph;
   }

    @Override
    public String toString() {
        return "Paragraphe{" + "id=" + numParagraph + ", auteur=" + auteur + ", titre=" + titreParagraphe + '}';
    }
}

