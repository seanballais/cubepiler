package ds.ast;

import exceptions.RuntimeException;

public class ArithmeticOperationNode extends OperationNode
{
    public ArithmeticOperationNode(String operation, Computable operator1, Computable operator2, int startingLine, int startingColumn)
    {
        super(operation, operator1, operator2, startingLine, startingColumn);
    }

    public Value compute() throws RuntimeException
    {
        Value value1 = this.operator1.compute();
        Value value2 = this.operator2.compute();

        // Identify resulting value type.
        // NOTE: Operations between booleans and integers/floats, and None and integers/floats are
        //       nor allowed.
        if (value1.getType() == ValueType.BOOLEAN || value2.getType() == ValueType.BOOLEAN) {
            throw new RuntimeException("Cannot perform arithmetic operations with a boolean.",
                                       this.startingLine, this.startingColumn);
        } else if (value1.getType() == ValueType.NONE || value2.getType() == ValueType.NONE) {
            throw new RuntimeException("Cannot perform arithmetic operations with a none type.",
                                       this.startingLine, this.startingColumn);
        } else if (value1.getType() == ValueType.STRING || value2.getType() == ValueType.STRING) {
            return new Value(value1.getValue() + value2.getValue(), ValueType.STRING);
        } else if (value1.getType() == ValueType.FLOAT || value2.getType() == ValueType.FLOAT) {
            return new Value("" + this.computeFloat(value1, value2), ValueType.FLOAT);
        } else if (value1.getType() == ValueType.INTEGER || value2.getType() == ValueType.INTEGER) {
            return new Value("" + this.computeInt(value1, value2), ValueType.INTEGER);
        } else {
            ValueType resultType = value1.getType(); // This assumes that both have the same types.
            if (resultType == ValueType.INTEGER) {
                return new Value("" + this.computeInt(value1, value2), ValueType.INTEGER);
            } else if (resultType == ValueType.FLOAT) {
                return new Value("" + this.computeFloat(value1, value2), ValueType.FLOAT);
            } else {
                throw new RuntimeException("Cannot perform arithmetic operations with unknown types.",
                                           this.startingLine, this.startingColumn);
            }
        }
    }

    private float computeFloat(Value float1, Value float2)
    {
        float value1 = Float.parseFloat(float1.getValue());
        float value2 = Float.parseFloat(float2.getValue());
        switch (this.operation) {
            case "+":
                return value1 + value2;
            case "-":
                return value1 - value2;
            case "*":
                return value1 * value2;
            case "/":
                return value1 / value2;
            case "%":
                return value1 % value2;
            default:
                return 0.0f; // Fallback value.
        }
    }

    private int computeInt(Value integer1, Value integer2)
    {
        int value1 = Integer.parseInt(integer1.getValue());
        int value2 = Integer.parseInt(integer2.getValue());
        switch (this.operation) {
            case "+":
                return value1 + value2;
            case "-":
                return value1 - value2;
            case "*":
                return value1 * value2;
            case "/":
                return value1 / value2;
            case "%":
                return value1 % value2;
            default:
                return 0; // Fallback value.
        }
    }
}