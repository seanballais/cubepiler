package ds.ast;

public class LessThanExpressionNode extends OperationNode
{
    public LessThanExpressionNode(String operation, Computable operator1, Computable operator2, int startingLine, int startingColumn)
    {
        super(operation, operator1, operator2, startingLine, startingColumn);
    }
}