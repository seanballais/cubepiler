package ds.ast;

public abstract class ASTNode
{
    protected int startingLine;
    protected int startingColumn;

    public ASTNode(int startingLine, int startingColumn)
    {
        this.startingLine = startingLine;
        this.startingColumn = startingColumn;
    }
}