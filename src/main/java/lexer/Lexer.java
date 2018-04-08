package lexer;

import java.util.ArrayList;
import java.util.LinkedList;

public class Lexer
{
    private String source;
    private LinkedList<String> tokens;
    private int currentRow;
    private int currentColumn;
    private int currentCharIndex;

    public Lexer()
    {
        this.tokens = new LinkedList<>();
    }

    public LinkedList<String> getTokens(String source) throws SourceException
    {
        this.tokens.clear();
        this.source = source;

        this.tokenize();

        return this.tokens;
    }

    private void tokenize() throws SourceException
    {
        // This just separates the characters into appriopriate tokens
        // in non-discriminatory manner (i.e. does not identify the
        // token type yet).
        for (this.currentCharIndex = 0; i < this.source.length(); this.currentCharIndex++) {
            char c = this.source.charAt(this.currentCharIndex);

            if (Character.isDigit(c)) {
                // Check for numerical data types.
                handleNumbers();
            }
        }
    }

    private void classifyTokens()
    {

    }

    private void handleNumbers() throws SourceException
    {
        char c = this.source.charAt(this.currentCharIndex);
        boolean hasDecimalPoint = false;
        String number = "";
        while (!Character.isWhitespace(c)
               || c == ','  // For cases where we pass integers as arguments in functions.
               || c == ')'  // For cases where we pass integers as arguments as well.
               ) {
            if (Character.isDigit(c)) {
                number += c;
            } else if (c == '.') {
                if (hasDecimalPoint) {
                    throw new SourceException("Decimal number has too many decimal points.",
                                              this.currentRow,
                                              this.currentColumn);
                } else {
                    number += '.';
                    hasDecimalPoint = true;
                }
            } else {
                throw new SourceException("Unexpected character.",
                                          this.currentRow,
                                          this.currentColumn);
            }

            c = updateCurrentCharacter();
        }

        this.tokens.add(number);
    }

    private char updateCurrentCharacter()
    {
        this.currentCharIndex++;

        char c = this.source.charAt(this.currentCharIndex);

        if (c == '\n') {
            this.currentRow = 0;
            this.currentColumn = 0;
        }

        return c;
    }
}