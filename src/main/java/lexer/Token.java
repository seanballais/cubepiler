package lexer;

public class Token
{
    enum TokenType {
        // Data types
        INTEGER,
        FLOAT,
        CHARACTER,
        BOOLEAN,
        STRING,

        // Arithmetic Operators
        ADDITION,
        SUBTRACTION,
        DIVISION,
        MULTIPLICATION,
        EXPONENT,
        ASSIGNMENT,
        MODULO,
            
        // Logical Operators
        AND,
        OR,
        NOT,
            
        // Relational Operators
        G_THAN,
        L_THAN,
        EQUAL,
        N_EQUAL,
        G_EQUAL,
        L_EQUAL,
        
        // Key Words
        BREAK,
        NEXT,
        RETURN,
        FN,
        VAR,
        END,
        IF,
        ELSIF,
        ELSE,
        WHILE,
            
        //Boolean Values
        TRUE,
        FALSE,

        // Miscellaneous
        USER_DEFINED_NAME
    }

    private String value;
    private TokenType type;
    private int startingRow;
    private int startingColumn;

    public Token(String value, TokenType type, int startingRow, int startingColumn)
    {
        this.value = value;
        this.type = type;
        this.startingRow = startingRow;
        this.startingColumn = startingColumn;
    }

    public String getValue()
    {
        return this.value;
    }

    public TokenType getType()
    {
        return this.type;
    }

    public int getStartingRow()
    {
        return this.startingRow;
    }

    public int getStartiColumn()
    {
        return this.startingColumn;
    }
}
