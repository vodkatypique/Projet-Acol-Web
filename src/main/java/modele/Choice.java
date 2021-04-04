package modele;

public class Choice {
    
   private final int idBook;
   private int numParagraphCurrent; 
   private int numParagraphNext;
   private int numParagraphConditional;

   public Choice(int id, int current, int next, int conditional) {
      this.idBook = id ;
      this.numParagraphCurrent = current;
      this.numParagraphNext = next;
      this.numParagraphConditional = conditional;
   }

   public int getIdBook() {
      return this.idBook; 
   }
   
   public int getNumParagraphCurrent() {
      return this.numParagraphCurrent; 
   }

   public int getNumParagraphNext() {
      return this.numParagraphNext;
   }
   
   public int getNumParagraphConditional()
   {
       return this.numParagraphConditional;
   }

    @Override
    public String toString() {
        String next = "";
        String conditional = "";
        if (numParagraphNext != 0){
            next = " next=" + numParagraphNext;
        }
        if (numParagraphNext != 0){
            conditional = " conditional=" + numParagraphConditional;
        }
        return "Choice{" + "idBook=" + idBook + " current=" + 
                numParagraphCurrent + next + conditional + '}';
    }
    
}
