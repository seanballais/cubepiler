package ds.ast;

import exceptions.RuntimeException;

public interface Computable
{
    public abstract Value compute() throws RuntimeException;
}