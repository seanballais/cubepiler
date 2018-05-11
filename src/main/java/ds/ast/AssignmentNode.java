package ds.ast;

public class AssignmentNode extends ASTNode
{
    private String variableName;
    private String value;

    public AssignmentNode(String variableName, String value, int startingLine, int startingColumn)
    {
        super(startingLine, startingColumn);

        this.variableName = variableName;
        this.value = value;
    }
}