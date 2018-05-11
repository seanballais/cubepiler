package ds.ast;

import exceptions.CompilerException;
import exceptions.RuntimeException;

public abstract class OperationNode extends ASTNode implements Computable
{
    protected String operation;
    protected Computable operator1;
    protected Computable operator2;

    protected OperationNode(String operation, Computable operator1, Computable operator2, int startingLine, int startingColumn)
    {
        super(startingLine, startingColumn);

        this.operation = operation;
        this.operator1 = operator1;
        this.operator2 = operator2;
    }

    public abstract Value compute() throws RuntimeException, CompilerException;
}