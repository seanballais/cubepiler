package exceptions;

public class SourceException extends Exception
{
    private static final long serialVersionUID = 5504805705305505310L;

    public SourceException() {}

    public SourceException(String message, int row, int column)
    {
        super(String.format("Syntax Error (Line %d, Column %d): %s",
                            row, column, message));
    }
}