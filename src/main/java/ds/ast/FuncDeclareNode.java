package ds.ast;

import java.util.ArrayList;

import exceptions.RuntimeException;
import exceptions.CompilerException;

public class FuncDeclareNode extends ASTNode implements Evaluatable
{
    private String funcName;
    private Value[] arguments;
    private ArrayList<StatementNode> statements;
    private int statementIndex;

    public FuncDeclareNode(String funcName, Value[] arguments,
                           int startingLine, int startingColumn) throws RuntimeException, CompilerException
    {
        super(startingLine, startingColumn);

        this.funcName = funcName;
        this.arguments = arguments;
        this.statementIndex = 0;
    }

    public String getName()
    {
        return this.funcName;
    }

    public Value[] getArguments()
    {
        return this.arguments;
    }

    public void addStatement(StatementNode statement)
    {
        this.statements.add(statement);
    }

    public Evaluatable nextNode() throws CompilerException
    {
        if (!this.hasNext()) {
            throw new CompilerException("No more evaluatable statements available.",
                                        this.startingLine, this.startingColumn);
        }

        return this.statements.get(this.statementIndex++);
    }

    public boolean hasNext()
    {
        return this.statementIndex < this.statements.size();
    }
}