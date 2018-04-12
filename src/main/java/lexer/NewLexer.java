package lexer;

import java.util.LinkedList;
import lexer.Token;

public class NewLexer {
    private String source;
    private LinkedList<Token> tokens;
    private int currentLine;
    private int currentColumn;
    private int currentCharIndex;
    private int sLine;
    private int sColumn;

    public NewLexer() {
        this.tokens = new LinkedList<>();

        this.currentLine = 1;
        this.currentColumn = 1;
    }

    public LinkedList<Token> getTokens(String source) throws SourceException {
        this.tokens.clear();
        this.source = source;

        this.tokenize();

        return this.tokens;
    }

    private void tokenize() throws SourceException {
        char c;
        for (this.currentCharIndex = 0, c = this.source.charAt(this.currentCharIndex); this.currentCharIndex < this.source.length();) {
            sLine = this.currentLine;
            sColumn = this.currentColumn;

            if (Character.isDigit(c)) {
                handleNumbers();
            } else if (c == '"') {
                handleStrings();
            } else if (Character.isLetter(c)) {
                handleWords();
            } else if (c == '\n') {
                this.tokens.add(new Token("newLine", Token.TokenType.CHARACTER, sLine, sColumn));
                this.currentLine++;
            } else if (isSymbol(c)) {
                handleSymbols();
            }

            c = updateCurrentCharacter();
        }
    }

    private void handleNumbers() throws SourceException {
        char c = this.source.charAt(this.currentCharIndex);
        boolean hasDecimalPoint = false;
        String number = "";

        while (Character.isDigit(c) || c == '.') {
            if (Character.isDigit(c)) {
                number += c;
            } else if (c == '.') {
                if (hasDecimalPoint) {
                    throw new SourceException("Decimal number has too many decimal points.", this.currentLine, this.currentColumn);
                } else {
                    number += c;
                    hasDecimalPoint = true;
                }
            } else {
                throw new SourceException("Unexpected character.", this.currentLine, this.currentColumn);
            }

            c = updateCurrentCharacter();
        }

        if (number.charAt(number.length() - 1) == '.') {
            throw new SourceException("Unexpected character.", this.currentLine, this.currentColumn);
        }

        if (!number.isEmpty()) {
            if (number.contains("."))
                this.tokens.add(new Token(number, Token.TokenType.FLOAT, sLine, sColumn));
            else
                this.tokens.add(new Token(number, Token.TokenType.INTEGER, sLine, sColumn));
        }

        this.currentCharIndex--;
        this.currentColumn--;
    }

    private void handleStrings() throws SourceException {
        String stringToken = "";
        this.currentCharIndex++;
        char c = this.source.charAt(this.currentCharIndex);

        while (c != '"' && c != '\0') {
            stringToken += c;

            c = updateCurrentCharacter();
        }

        if (c != '"')
            throw new SourceException("Expected matching closing double quote.", this.currentLine, this.currentColumn);
        
        if (!stringToken.isEmpty())
            this.tokens.add(new Token(stringToken, Token.TokenType.STRING, sLine, sColumn));
    }

    private void handleWords() {
        String word = "";
        char c = this.source.charAt(this.currentCharIndex);
        boolean hasNumbers = false;

        while (Character.isLetter(c) || Character.isDigit(c)) {
            word += c;

            if (Character.isDigit(c)) {
                hasNumbers = true;
            }

            c = updateCurrentCharacter();
        }

        if (!word.isEmpty()) {
            if (hasNumbers) {
                this.tokens.add(new Token(word, Token.TokenType.USER_DEFINED_NAME, sLine, sColumn));
            } else {
                this.tokens.add(new Token(word, getType(word), sLine, sColumn));
            }
        }

        this.currentCharIndex--;
        this.currentColumn--;
    }

    private void handleSymbols() {
        String symbol = "";
        char c = this.source.charAt(this.currentCharIndex);

        while (isSymbol(c)) {
            symbol += c;

            c = updateCurrentCharacter();
        }

        if (!symbol.isEmpty())
            this.tokens.add(new Token(symbol, getType(symbol), sLine, sColumn));

        this.currentCharIndex--;
        this.currentColumn--;
    }

    private Token.TokenType getType(String word) {
        if (word.equals("break"))
            return Token.TokenType.BREAK;
        else if (word.equals("next"))
            return Token.TokenType.NEXT;
        else if (word.equals("return"))
            return Token.TokenType.RETURN;
        else if (word.equals("fn"))
            return Token.TokenType.FN;
        else if (word.equals("var"))
            return Token.TokenType.VAR;
        else if (word.equals("end"))
            return Token.TokenType.END;
        else if (word.equals("if"))
            return Token.TokenType.IF;
        else if (word.equals("elsif"))
            return Token.TokenType.ELSIF;
        else if (word.equals("else"))
            return Token.TokenType.ELSE;
        else if (word.equals("while"))
            return Token.TokenType.WHILE;
        else if (word.equals("true"))
            return Token.TokenType.TRUE;
        else if (word.equals("false"))
            return Token.TokenType.FALSE;
        else if (word.equals("+"))
            return Token.TokenType.ADDITION;
        else if (word.equals("-"))
            return Token.TokenType.SUBTRACTION;
        else if (word.equals("/"))
            return Token.TokenType.DIVISION;
        else if (word.equals("*"))
            return Token.TokenType.MULTIPLICATION;
        else if (word.equals("^"))
            return Token.TokenType.EXPONENT;
        else if (word.equals("="))
            return Token.TokenType.ASSIGNMENT;
        else if (word.equals("%"))
            return Token.TokenType.MODULO;
        else if (word.equals("*"))
            return Token.TokenType.MULTIPLICATION;
        else if (word.equals("&"))
            return Token.TokenType.AND;
        else if (word.equals("|"))
            return Token.TokenType.OR;
        else if (word.equals("!"))
            return Token.TokenType.NOT;
        else if (word.equals(">"))
            return Token.TokenType.G_THAN;
        else if (word.equals("<"))
            return Token.TokenType.L_THAN;
        else if (word.equals("=="))
            return Token.TokenType.EQUAL;
        else if (word.equals("!="))
            return Token.TokenType.N_EQUAL;
        else if (word.equals(">="))
            return Token.TokenType.G_EQUAL;
        else if (word.equals("<="))
            return Token.TokenType.L_EQUAL;
        else if (word.equals("("))
            return Token.TokenType.O_PARENTHESIS;
        else if (word.equals(")"))
            return Token.TokenType.C_PARENTHESIS;
        else if (word.equals(","))
            return Token.TokenType.SEPARATOR;
        else
            return Token.TokenType.USER_DEFINED_NAME;
    }

    public boolean isSymbol(char c) {
        return (!Character.isWhitespace(c) && !Character.isDigit(c) && !Character.isLetter(c) && c != '"' && c != '\n');
    }

    private char updateCurrentCharacter() {
        this.currentCharIndex++;

        if (this.currentCharIndex >= this.source.length())
            return '\0';

        char c = this.source.charAt(this.currentCharIndex);

        this.currentColumn++;

        return c;
    }
}