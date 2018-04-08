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

    public Token(String value, TokenType type)
    {
        this.value = value;
        this.type = type;
    }

    public String getValue()
    {
        return this.value;
    }

    public TokenType getType()
    {
        return this.type;
    }
}