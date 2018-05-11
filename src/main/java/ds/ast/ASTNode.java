package ds.ast;

public abstract class ASTNode
{
    protected int startingLine;
    protected int startingColumn;

    protected ASTNode(int startingLine, int startingColumn)
    {
        this.startingLine = startingLine;
        this.startingColumn = startingColumn;
    }
}