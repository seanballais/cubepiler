package ds.ast;

import exceptions.RuntimeException;
import exceptions.CompilerException;

public class AssignmentNode extends ASTNode implements Evaluatable
{
    private String variableName;
    private Computable value;

    public AssignmentNode(String variableName, Computable value, int startingLine, int startingColumn)
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