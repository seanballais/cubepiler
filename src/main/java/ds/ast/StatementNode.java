package ds.ast;

import java.util.ArrayList;

import exceptions.CompilerException;

public class StatementNode extends ASTNode implements Evaluatable
{
    private int index;
    private ArrayList<Evaluatable> nodes;

    public StatementNode(int startingLine, int startingColumn)
    {
        super(startingLine, startingColumn);

        this.nodes = new ArrayList<>();
        this.index = 0;
    }

    public void addNode(Evaluatable evaluatable)
    {
        this.nodes.add(evaluatable);
    }

    public Evaluatable nextNode() throws CompilerException
    {
        if (this.hasNext()) {
            throw new CompilerException("No more evaluatable nodes available.",
                                        this.startingLine, this.startingColumn);
        }

        return this.nodes.get(this.index++);
    }

    public boolean hasNext()
    {
        return this.index >= this.nodes.size();
    }
}