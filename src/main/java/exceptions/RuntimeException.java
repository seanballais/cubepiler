package exceptions;

public class RuntimeException extends Exception
{
    private static final long serialVersionUID = 1001020560530990530L;

    public RuntimeException() {}

    public RuntimeException(String message, int row, int column)
    {
        super(String.format("Runtime Error (Line %d, Column %d): %s", row, column, message));
    }
}