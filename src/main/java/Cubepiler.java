import lexer.Lexer;
import lexer.Token;
import lexer.SourceException;

public class Cubepiler {
    private static String testSource = "fn main(var arg)\nprint(\"Hello world!\")\nvar sum = 1 + 1\nif sum >= 2\nprint(\"GREATER\")\nend\nend";

    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        
        try {
            System.out.println("Test Source: " + testSource);
            System.out.println("Tokens");
            int itemNumber = 0;
            for (Token token : lexer.getTokens(testSource)) {
                System.out.println(String.format("%d) %s\t%s\tl:%d c:%d", itemNumber, token.getValue(), token.getType(), token.getStartingRow(), token.getStartingColumn()));
                itemNumber++;
            }
        } catch (SourceException se) {
            System.out.println(se.getMessage());
        }
    }
}
