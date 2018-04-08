package lexer;

import java.util.LinkedList;

public class Lexer
{
    private String source;
    private LinkedList<String> tokens;
    private int currentLine;
    private int currentColumn;
    private int currentCharIndex;

    public Lexer()
    {
        this.tokens = new LinkedList<>();

        // We start at 1 for the rows and columns since they usually
        // start at 1, notably in text editors.
        this.currentLine = 1;
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
        char c;
        for (this.currentCharIndex = 0,
             c = this.source.charAt(this.currentCharIndex);
             this.currentCharIndex < this.source.length();) {
            if (Character.isDigit(c)) {
                // Check for numerical data types.
                handleNumbers();
            }

            c = updateCurrentCharacter();
        }
    }

    private void handleNumbers() throws SourceException
    {
        char c = this.source.charAt(this.currentCharIndex);
        boolean hasDecimalPoint = false;
        String number = "";
        while (!Character.isWhitespace(c)
               && c != ','  // For cases where we pass integers as arguments in functions.
               && c != ')'  // For cases where we pass integers as arguments as well.
               && c != '\0') {
            if (Character.isDigit(c)) {
                number += c;
            } else if (c == '.') {
                if (hasDecimalPoint) {
                    throw new SourceException("Decimal number has too many decimal points.",
                                              this.currentLine,
                                              this.currentColumn);
                } else {
                    number += '.';
                    hasDecimalPoint = true;
                }
            } else {
                throw new SourceException("Unexpected character.",
                                          this.currentLine,
                                          this.currentColumn);
            }

            c = updateCurrentCharacter();
        }

        if (number != "") {
            this.tokens.add(number);
        }
    }

    private char updateCurrentCharacter()
    {
        this.currentCharIndex++;

        if (this.currentCharIndex >= this.source.length()) {
            return '\0';
        }

        char c = this.source.charAt(this.currentCharIndex);

        if (c == '\n') {
            this.currentLine++;
            this.currentColumn = 0;
        } else {
            this.currentColumn++;
        }

        return c;
    }
}