package ds.ast;

public class DivideExpressionNode extends OperationNode
{
    public DivideExpressionNode(String operation, Computable operator1, Computable operator2, int startingLine, int startingColumn)
    {
        super(operation, operator1, operator2, startingLine, startingColumn);
    }
}