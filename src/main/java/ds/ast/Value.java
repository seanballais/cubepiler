package ds.ast;

public class Value
{
    private String value;
    private ValueType type;

    public Value(String value, ValueType type)
    {
        this.value = value;
        this.type = type;
    }

    public String getValue()
    {
        return this.value;
    }

    public ValueType getType()
    {
        return this.type;
    }
}