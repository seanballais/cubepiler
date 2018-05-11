package exceptions;

public class RuntimeException extends Exception
{
    public RuntimeException() {}

    public RuntimeException(String message, int row, int column)
    {
        super(String.format("Runtime Error (Line %d, Column %d): %s", row, column, message));
    }
}