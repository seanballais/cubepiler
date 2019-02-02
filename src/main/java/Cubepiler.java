import java.util.LinkedList;

import java.io.FileReader;
import java.io.BufferedReader;

import lexer.Lexer;
import parser.Parser;
import ds.token.Token;
import exceptions.SourceException;

public class Cubepiler {
    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        
        try {
            String sourceFilepath = "src/main/java/test_assets/test_src.txt";
            BufferedReader br = new BufferedReader(
                                    new FileReader(sourceFilepath)
                                );
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());

                line = br.readLine();
            }

            String testSource = sb.toString();
            LinkedList<Token> tokens = lexer.getTokens(testSource);
            System.out.println("Test Source");
            System.out.println("~~~~~~~~~~~");
            System.out.println(testSource);
            System.out.println("Tokens");
            int itemNumber = 0;
            for (Token token : tokens) {
                System.out.println(String.format("%d)\t%s",
                                                 itemNumber, token.toString()));
                itemNumber++;
            }

            br.close();

            new Parser(tokens).parse();
        } catch (SourceException se) {
            System.out.println(se.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
