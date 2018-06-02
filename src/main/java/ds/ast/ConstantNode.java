package ds.ast;

public class ConstantNode extends ASTNode implements Computable
{
    private Value value;

    public ConstantNode(Value value, int startingLine, int startingColumn)
    {
        super(startingLine, startingColumn);

        this.value = value;
    }

    public Value getValue()
    {
        return this.value;
    }
}