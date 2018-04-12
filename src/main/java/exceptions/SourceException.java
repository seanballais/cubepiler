package exceptions;

public class SourceException extends Exception
{
    public SourceException() {}

    public SourceException(String message, int row, int column)
    {
        super(String.format("Syntax Error (Line %d, Column %d): %s", row, column, message));
    }
}