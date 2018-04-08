package lexer;

enum TokenType
{
    // Data types
    INTEGER,
    FLOAT,
    CHARACTER,
    BOOLEAN,
    STRING,

    // Operators
    ARITHMETIC_OPERATOR,
    LOGICAL_OPERATOR,
    ASSIGHNMENT_OPERATOR,
    COMPARISON_OPERATOR,
    
    // Reserved words
    BREAK_KEYWORD,
    RETURN_KEYWORD,
    END_KEYWORD,
    IF_KEYWORD,
    ELSE_KEYWORD,
    FUNCTION_DECLARATION,
    VARIABLE_DECLARATION,

    // Miscellaneous
    USER_DEFINED_NAME
}

class Token
{
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