package lexer;

import exceptions.SourceException;

import java.util.LinkedList;
import java.util.HashSet;

import ds.token.Token;
import ds.token.TokenType;

public class Lexer
{
    private String source;
    private LinkedList<Token> tokens;
    private HashSet<String> reservedSymbols;
    private int currentLine;
    private int currentColumn;
    private int currentCharIndex;

    public Lexer()
    {
        this.tokens = new LinkedList<>();
        this.reservedSymbols = new HashSet<>();

        // We start at 1 for the rows and columns since they usually
        // start at 1, notably in text editors.
        this.currentLine = 1;
        this.currentColumn = 1;
    }

    public LinkedList<Token> getTokens(String source) throws SourceException
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
            } else if (c == '#') {
                c = handleComments();
            } else if (c == '\n') {                
                this.tokens.add(new Token("newline",
                                          TokenType.NEWLINE,
                                          this.currentLine,
                                          this.currentColumn));
                c = moveToNextCharacter();
            } else if (!Character.isWhitespace(c)) {
                c = handleOperators();
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
        boolean justPastDecimalPoint = false;
        int numberStartLine = this.currentLine;
        int numberStartColumn = this.currentColumn;
        int decimalPointStartLine = 0;
        int decimalPointStartColumn = 0;
        while (Character.isDigit(c) || c == '.') {
            if (Character.isDigit(c)) {
                number += c;
                justPastDecimalPoint = false;
            } else if (c == '.') {
                if (hasDecimalPoint) {
                    throw new SourceException("Decimal number has too many decimal points.",
                                              this.currentLine,
                                              this.currentColumn);
                } else {
                    number += '.';
                    hasDecimalPoint = true;
                    justPastDecimalPoint = true;
                    decimalPointStartLine = this.currentLine;
                    decimalPointStartColumn = this.currentColumn - 1; // Idk why but if we
                                                                      // don't subtract this by
                                                                      // one, we point to the
                                                                      // wrong character and not
                                                                      // the decimal point itself,
                                                                      // in the error message.
                                                                      // TODO: Please fix this.
                }
            } else {
                throw new SourceException("Unexpected character.",
                                          this.currentLine,
                                          this.currentColumn);
            }

            c = moveToNextCharacter();
        }

        if (justPastDecimalPoint) {
            throw new SourceException("There must be a number after the decimal point.",
                                      decimalPointStartLine,
                                      decimalPointStartColumn);
        }

        if (number != "") {
            TokenType type;
            if (hasDecimalPoint) {
                type = TokenType.FLOAT;
            } else {
                type = TokenType.INTEGER;
            }

            this.tokens.add(new Token(number,
                                      type,
                                      numberStartLine,
                                      numberStartColumn));
        } 

        return c;
    }

    private char handleStrings(char startQuote) throws SourceException
    {
        String stringToken = "";
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
        int stringStartLine = this.currentLine;
        int stringStartColumn = this.currentColumn;
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
            c = moveToNextCharacter();  // Must be explicitly called. Otherwise, we would be
                                        // pointing to the last quote, and when we go back
                                        // to the main lexing loop, the current character
                                        // being pointed to would be the last quote which
                                        // would make the main lexing loop assume it is
                                        // the start of another string.

            // We add the token here since, at this point, the string is presumably valid.
            // There might be a few edge cases that would make the presumption false.
            this.tokens.add(new Token(stringToken,
                                      TokenType.STRING,
                                      stringStartLine,
                                      stringStartColumn));
        } else {
            throw new SourceException("Expected matching closing quote.",
                                      stringStartLine,
                                      stringStartColumn);
        }

        return c;
    }

    private char handleSymbols() throws SourceException
    {
        String symbol = "";
        int symbolStartLine = this.currentLine;
        int symbolStartColumn = this.currentColumn;
        char c = this.source.charAt(this.currentCharIndex);
        while (Character.isLetter(c) || Character.isDigit(c) || c == '_') {
            symbol += c;
            c = moveToNextCharacter();
        }

        if (c == '.') {
            // SERIOUSLY! There is a bug in the column number it gives the error messages,
            throw new SourceException("Symbols cannot have periods in their names.",
                                      this.currentLine,
                                      this.currentColumn);
        }

        if (symbol != "") {
            TokenType type;

            switch (symbol) {
                case "break":
                    type = TokenType.BREAK_KEYWORD;
                    break;
                case "return":
                    type = TokenType.RETURN_KEYWORD;
                    break;
                case "end":
                    type = TokenType.END_KEYWORD;
                    break;
                case "if":
                    type = TokenType.IF_KEYWORD;
                    break;
                case "else":
                    type = TokenType.ELSE_KEYWORD;
                    break;
                case "fn":
                    type = TokenType.FUNCTION_DECLARATION;
                    break;
                case "var":
                    type = TokenType.VARIABLE_DECLARATION;
                    break;
                case "true":
                    type = TokenType.BOOLEAN;
                    break;
                case "false":
                    type = TokenType.BOOLEAN;
                    break;
                default:
                    type = TokenType.USER_DEFINED_SYMBOL;
                    break;
            }

            this.tokens.add(new Token(symbol, type, symbolStartLine, symbolStartColumn));
        }

        return c;
    }

    private char handleOperators() throws SourceException
    {
        int operatorStartLine = this.currentLine;
        int operatorStartColumn = this.currentColumn;

        String operator = String.format("%c", this.source.charAt(this.currentCharIndex));
        char c = moveToNextCharacter(); // Move to the next character so that we get the right
                                        // operator symbol pair of the operator we captured,
                                        // in case there is one.

        char operatorSymbolPartner = this.source.charAt(this.currentCharIndex);

        if (operatorSymbolPartner == '=') {
            operator += "=";
            c = moveToNextCharacter();
        }

        TokenType type;
        if (operator.equals("+") || operator.equals("+=")
                || operator.equals("-") || operator.equals("-=")
                || operator.equals("*") || operator.equals("*=")
                || operator.equals("/") || operator.equals("/=")
                || operator.equals("%") || operator.equals("%=")
                || operator.equals("^") || operator.equals("^=")) {
            type = TokenType.ARITHMETIC_OPERATOR;
        } else if (operator.equals("=")) {
            type = TokenType.ASSIGNMENT_OPERATOR;
        } else if (operator.equals("&") || operator.equals("|") || operator.equals("!")) {
            type = TokenType.LOGICAL_OPERATOR;
        } else if (operator.equals("==") || operator.equals("!=")
                   || operator.equals(">") || operator.equals(">=")
                   || operator.equals("<") || operator.equals("<=")) {
            type = TokenType.COMPARISON_OPERATOR;
        } else if (operator.equals("(") || operator.equals(")") || operator.equals(",")) {
            type = TokenType.SEPARATOR;
        } else {
            // Oh, no!
            throw new SourceException("Unsupported operator.",
                                      operatorStartLine,
                                      operatorStartColumn);
        }

        this.tokens.add(new Token(operator, type, operatorStartLine, operatorStartColumn));

        return c;
    }

    private char handleComments()
    {
        char c = this.source.charAt(this.currentCharIndex);
        while (c != '\n') {
            c = moveToNextCharacter();
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
            this.currentColumn = 1;
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
                                          this.currentColumn - 1);  // We move one column to
                                                                    // point to the correct
                                                                    // start of the escape
                                                                    // sequence.
        }
    }
}