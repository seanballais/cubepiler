package ds.ast;

public abstract class OperationNode extends ASTNode implements Computable
{
    protected String operation;
    protected Computable operator1;
    protected Computable operator2;

    protected OperationNode(String operation, Computable operator1, Computable operator2, int startingLine, int startingColumn)
    {
        super(startingLine, startingColumn);

        this.operation = operation;
        this.operator1 = operator1;
        this.operator2 = operator2;
    }

    public Computable getOperator1()
    {
        return this.operator1;
    }

    public Computable getOperator2()
    {
        return this.operator2;
    }
}