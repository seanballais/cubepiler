package exceptions;

public class CompilerException extends Exception
{
    private static final long serialVersionUID = 5310109910204905304L;

    public CompilerException() {}

    public CompilerException(String message, int row, int column)
    {
        super(String.format("Compiler Error (Line %d, Column %d): %s", row, column, message));
    }
}