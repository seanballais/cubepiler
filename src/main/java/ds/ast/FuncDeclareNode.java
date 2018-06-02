package ds.ast;

import exceptions.CompilerException;

public class FuncDeclareNode extends ASTNode implements Evaluatable
{
    private String funcName;
    private Value[] arguments;
    private StatementNode statements;

    public FuncDeclareNode(String funcName, Value[] arguments,
                           int startingLine, int startingColumn) throws CompilerException
    {
        super(startingLine, startingColumn);

        this.funcName = funcName;
        this.arguments = arguments;
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
        this.statements.addNode(statement);
    }

    public Evaluatable nextStatement() throws CompilerException
    {
        if (!this.hasNext()) {
            throw new CompilerException("No more evaluatable statements available.",
                                        this.startingLine, this.startingColumn);
        }

        return this.statements.nextNode();
    }

    public boolean hasNext()
    {
        return this.statements.hasNext();
    }
}