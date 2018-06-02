package ds.ast;

public class GreaterThanExpressionNode extends OperationNode
{
    public GreaterThanExpressionNode(String operation, Computable operator1, Computable operator2, int startingLine, int startingColumn)
    {
        super(operation, operator1, operator2, startingLine, startingColumn);
    }
}