package lexer;

public class SourceException extends Exception
{
    public SourceException() {}

    public SourceException(String message, int row, int column)
    {
        super(String.format("Syntax Error (row: %d, column: %d): %s", row, column, message));
    }
}