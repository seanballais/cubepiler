package ds.token;

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

    public int getStartingColumn()
    {
        return this.startingColumn;
    }

    public String toString()
    {
        return String.format("%s\t%s\t\t\t(Line: %d, Column: %d)",
                             this.value,
                             this.type.toString(),
                             this.startingLine,
                             this.startingColumn);
    }
}