package lexer;

public class Token
{
    private String value;
    private TokenType type;
    private int startingLine;
    private int startingColumn;

    public Token(String value, TokenType type, int startingLine, int startingColumn)
    {
        this.value = value;
        this.type = type;
        this.startingLine = startingLine;
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

    public int getStartingLine()
    {
        return this.startingLine;
    }

    public int getStartiColumn()
    {
        return this.startingColumn;
    }
}