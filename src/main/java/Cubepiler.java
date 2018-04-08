import lexer.Lexer;
import lexer.SourceException;

public class Cubepiler {
    private static String testSource = "";

    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        
        try {
            int itemNumber = 0;
            for (String token : lexer.getTokens(testSource)) {
                itemNumber++;
                System.out.println(String.format("%d) %s", itemNumber, token));
            }
        } catch (SourceException se) {
            System.out.println(se.getMessage());
        }
    }
}
