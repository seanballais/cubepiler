package ds.ast;

import exceptions.RuntimeException;
import exceptions.CompilerException;

public class VarDeclareNode extends DeclarationNode implements Evaluatable
{
    public VarDeclareNode(String variableName, Computable value,
                          int startingLine, int startingColumn) throws RuntimeException, CompilerException
    {
        super(variableName, value, startingLine, startingColumn);
    }
}