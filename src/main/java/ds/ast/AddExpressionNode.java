package ds.ast;

public class AddExpressionNode extends OperationNode
{
    public AddExpressionNode(String operation, Computable operator1, Computable operator2, int startingLine, int startingColumn)
    {
        super(operation, operator1, operator2, startingLine, startingColumn);
    }
}