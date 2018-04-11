import lexer.Lexer;
import lexer.SourceException;

import java.io.FileReader;
import java.io.BufferedReader;

public class Cubepiler {
    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/java/test_assets/test_src.txt"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());

                line = br.readLine();
            }

            String testSource = sb.toString();
            System.out.println("Test Source");
            System.out.println("~~~~~~~~~~~");
            System.out.println(testSource);
            System.out.println("Tokens");
            int itemNumber = 0;
            for (String token : lexer.getTokens(testSource)) {
                System.out.println(String.format("%d)\t%s", itemNumber, token));
                itemNumber++;
            }

            br.close();
        } catch (SourceException se) {
            System.out.println(se.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
