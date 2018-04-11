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
        this.currentColumn = 1;
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
        // in a non-discriminatory manner (i.e. does not identify the
        // token type yet, if the syntax is valid, or if it's a valid
        // symbol). However, it will try to make sure that the number is
        // in the correct form (e.g. no double decimals in the same number).
        char c;
        for (this.currentCharIndex = 0,
             c = this.source.charAt(this.currentCharIndex);
             this.currentCharIndex < this.source.length();) {
            if (Character.isDigit(c)) {
                c = handleNumbers();
            } else if (c == '"') {
                c = handleStrings('"');
            } else if (c == '\'') {
                c = handleStrings('\'');
            } else if (Character.isLetter(c)) {
                c = handleSymbols();
            } else if (c == '\n') {                
                this.tokens.add("newline");
                c = moveToNextCharacter();
            } else if (!Character.isWhitespace(c)) {
                this.tokens.add(String.format("%c", c));
                c = moveToNextCharacter();
            } else {
                c = moveToNextCharacter();
            }
        }
    }

    private char handleNumbers() throws SourceException
    {
        char c = this.source.charAt(this.currentCharIndex);
        boolean hasDecimalPoint = false;
        String number = "";
        while (Character.isDigit(c) || c == '.') {
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

            c = moveToNextCharacter();
        }

        if (number != "") {
            this.tokens.add(number);
        }

        return c;
    }

    private char handleStrings(char startQuote) throws SourceException
    {
        String stringToken = String.format("%c", startQuote);  // We already know that the start is a double quote.
        this.currentCharIndex++;  // We immediately move to the next character since there is no
                                  // need to parse the first double quote again.

        // We should prolly borrow Python's feature where it allows breaking a string in
        // multiple lines using '\'. Yet, it is just one string.
        // e.g.
        //     some_str = "the string is " \
        //                "actually connected."
        // Value of some_str: "this string is actually connected.".

        char c = this.source.charAt(this.currentCharIndex);
        boolean mustEscapeCharacter = false;
        while ((c != startQuote || mustEscapeCharacter)
               && c != '\0') {  // There must be a pair to the first quote 
                                // before we exit the loop.
            if (mustEscapeCharacter) {
                stringToken += unescapeSequence("\\" + c);
                mustEscapeCharacter = false;
            } else if (c == '\\') {
                mustEscapeCharacter = true;
            } else {
                stringToken += c;
            }

            c = moveToNextCharacter();
        }

        if (c == startQuote) {
            stringToken += startQuote;
            c = moveToNextCharacter();  // Must be explicitly called. Otherwise, we would be
                                        // pointing to the last quote, and when we go back
                                        // to the main lexing loop, the current character
                                        // being pointed to would be the last quote which
                                        // would make the main lexing loop assume it is
                                        // the start of another string.
        } else {
            throw new SourceException("Expected matching closing quote.",
                                      this.currentLine,
                                      this.currentColumn);
        }

        if (stringToken.charAt(0) == startQuote
            && stringToken.charAt(stringToken.length() - 1) == startQuote) {
            this.tokens.add(stringToken);
        }

        return c;
    }

    private char handleSymbols()
    {
        String symbol = "";
        char c = this.source.charAt(this.currentCharIndex);
        while (Character.isLetter(c) || Character.isDigit(c) || c == '_') {
            symbol += c;
            c = moveToNextCharacter();
        }

        if (symbol != "") {
            this.tokens.add(symbol);
        }

        return c;
    }

    private char moveToNextCharacter()
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

    private char unescapeSequence(String escapedSequence) throws SourceException
    {
        // We'll be following the escape sequences supported by Java.
        switch (escapedSequence) {
            case "\\'":
                return '\'';
            case "\\\"":
                return '"';
            case "\\\\":
                return '\\';
            case "\\n":
                return '\n';
            case "\\r":
                return '\r';
            case "\\t":
                return '\t';
            case "\\b":
                return '\b';
            case "\\f":
                return '\f';
            default:
                throw new SourceException("Invalid escape sequence. Valid ones are '\\\'', "
                                          + "'\\\"', '\\\\', '\\n', '\\r', '\\t'. '\\b', and '\\f'",
                                          this.currentLine,
                                          this.currentColumn);
        }
    }
}