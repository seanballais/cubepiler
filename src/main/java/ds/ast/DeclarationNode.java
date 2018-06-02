package ds.ast;

public class DeclarationNode extends ASTNode implements Evaluatable
{
    protected String variableName;
    protected Computable value;

    public DeclarationNode(String variableName, Computable value, int startingLine, int startingColumn)
    {
        super(startingLine, startingColumn);

        this.variableName = variableName;
        this.value = value;
    }

    public String getName()
    {
        return this.variableName;
    }

    public Computable getValue()
    {
        return this.value;
    }
}