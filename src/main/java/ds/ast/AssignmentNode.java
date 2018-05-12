package ds.ast;

import exceptions.RuntimeException;
import exceptions.CompilerException;

public class AssignmentNode extends ASTNode implements Evaluatable
{
    private String variableName;
    private Value value;

    public AssignmentNode(String variableName, Computable value,
                          int startingLine, int startingColumn) throws RuntimeException, CompilerException
    {
        super(startingLine, startingColumn);

        this.variableName = variableName;
        this.value = value.compute();
    }

    public String getName()
    {
        return this.variableName;
    }

    public Value getValue()
    {
        return this.value;
    }
}