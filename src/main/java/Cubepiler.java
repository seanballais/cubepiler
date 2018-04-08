import lexer.Lexer;
import lexer.SourceException;

public class Cubepiler {
    private static String testSource = "2345 1234\n987";

    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        
        try {
            System.out.println("Test Source: " + testSource);
            System.out.println("Tokens");
            int itemNumber = 0;
            for (String token : lexer.getTokens(testSource)) {
                System.out.println(String.format("%d) %s", itemNumber, token));
                itemNumber++;
            }
        } catch (SourceException se) {
            System.out.println(se.getMessage());
        }
    }
}
