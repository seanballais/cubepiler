package ds.ast;

public class ConditionalNode extends ASTNode implements Evaluatable
{
    private Computable condition;
    private StatementNode statements;
    private StatementNode elseStatements;

    public ConditionalNode(Computable condition, StatementNode statements, StatementNode elseStatements,
                           int startingLine, int startingColumn)
    {
        super(startingLine, startingColumn);

        this.condition = condition;
        this.statements = statements;
        this.elseStatements = elseStatements;
    }
}