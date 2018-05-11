package ds.ast;

public class AssignmentNode extends ASTNode
{
    private String variableName;
    private String value;

    public AssignmentNode(String variableName, String value)
    {
        this.variableName = variableName;
        this.value = value;
    }

    public String getVariableName()
    {
        return this.variableName;
    }

    public String getValue()
    {
        return this.value;
    }
}