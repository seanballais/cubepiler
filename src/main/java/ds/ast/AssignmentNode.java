package ds.ast;

import exceptions.RuntimeException;
import exceptions.CompilerException;

public class AssignmentNode extends DeclarationNode implements Evaluatable
{
    public AssignmentNode(String variableName, Computable value,
                          int startingLine, int startingColumn) throws RuntimeException, CompilerException
    {
        super(variableName, value, startingLine, startingColumn);
    }
}