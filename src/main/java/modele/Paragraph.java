      
package modele; 

public class Paragraph {

   private final int idBook;
   private final int numParagraph; 
   private String paragraphTitle;
   private String text;
   private String author;
   private boolean isEnd;
   private boolean isValidate;
   private boolean isAccessible;

   public Paragraph(int id, int numParagraph, String title, String text, String author, boolean isEnd, boolean isValidate, boolean isAccessible) {
      this.idBook = id;
      this.numParagraph = numParagraph;
      this.paragraphTitle = title;
      this.text = text;
      this.author = author; 
      this.isEnd = isEnd;
      this.isValidate = isValidate;
      this.isAccessible = isAccessible;
   }

   public String getAuthor() {
      return this.author; 
   }

   public String getTitle() {
      return this.paragraphTitle;
   }
   
   public int getId()
   {
       return this.numParagraph;
   }
   
   public String getText()
   {
       return this.text;
   }

    @Override
    public String toString() {
        return "Paragraphe{" + "id=" + numParagraph + ", auteur=" + author + ", titre=" + paragraphTitle + ", text=" + text + '}';
    }
}

