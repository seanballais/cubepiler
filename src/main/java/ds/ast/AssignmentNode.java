package ds.ast;

import exceptions.RuntimeException;
import exceptions.CompilerException;

public class AssignmentNode extends ASTNode implements Computable
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

    public Value compute() throws RuntimeException, CompilerException
    {
        Value value1 = value.compute();

        if (value1.getValue().equals("") && (value1.getType() != ValueType.NONE
                                             || value1.getType() != ValueType.STRING)) {
            throw new CompilerException("value1 in AssignmentNode should not be empty",
                                        this.startingLine, this.startingColumn);
        }

        return value1;
    }
}