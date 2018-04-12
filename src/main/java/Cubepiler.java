import lexer.NewLexer;
import lexer.Token;
import lexer.SourceException;

public class Cubepiler {
    private static String testSource = "2345 1234\n9.a87varif if lol (xd)\"This must be parsed out.\"(lol)100 if";

    public static void main(String[] args) {
        NewLexer lexer = new NewLexer();
        
        try {
            System.out.println("Test Source: " + testSource);
            System.out.println("Tokens");
            int itemNumber = 0;
            for (Token token : lexer.getTokens(testSource)) {
                System.out.println(String.format("%d) %s", itemNumber, token.getValue()));
                itemNumber++;
            }
        } catch (SourceException se) {
            System.out.println(se.getMessage());
        }
    }
}
