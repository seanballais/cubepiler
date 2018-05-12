package ds.ast;

import exceptions.RuntimeException;
import exceptions.CompilerException;

public class ConstantNode extends ASTNode implements Computable
{
    public Value value;

    public ConstantNode(Value value, int startingLine, int startingColumn)
    {
        super(startingLine, startingColumn);

        this.value = value;
    }

    public Value compute() throws RuntimeException, CompilerException
    {
        if (value.getValue().equals("") && (value.getType() != ValueType.NONE
                                            || value.getType() != ValueType.STRING)) {
            throw new CompilerException("ConstantNode cannot have an empty value with a non-None"
                                        + " or non-string type.",
                                        this.startingLine, this.startingColumn);
        }

        return this.value;
    }
}