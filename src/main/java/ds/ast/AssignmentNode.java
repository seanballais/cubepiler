package ds.ast;

public class AssignmentNode extends DeclarationNode implements Evaluatable
{
    public AssignmentNode(String variableName, Computable value, int startingLine, int startingColumn)
    {
        super(variableName, value, startingLine, startingColumn);
    }
}