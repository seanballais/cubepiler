package ds.ast;

import exceptions.CompilerException;
import exceptions.RuntimeException;

public class ComparisonNode extends OperationNode
{
    public ComparisonNode(String operation, Computable operator1, Computable operator2, int startingLine, int startingColumn)
    {
        super(operation, operator1, operator2, startingLine, startingColumn);
    }

    public Value compute() throws RuntimeException, CompilerException
    {
        Value value1 = this.operator1.compute();
        Value value2 = this.operator2.compute();

        if (value1.getValue().equals("") && (value1.getType() != ValueType.NONE
                                             || value1.getType() != ValueType.STRING)) {
            throw new CompilerException("value1 in ArithmeticOperationNode should not be empty",
                                        this.startingLine, this.startingColumn);
        } else if (value2.getValue().equals("") && (value2.getType() != ValueType.NONE
                                                    || value2.getType() != ValueType.STRING)) {
            throw new CompilerException("value2 in ArithmeticOperationNode should not be empty",
                                        this.startingLine, this.startingColumn);
        }

        // Identify resulting value type.
        // NOTE: Operations between booleans and integers/floats, and None and integers/floats are
        //       nor allowed.
        if (value1.getType() == ValueType.BOOLEAN || value2.getType() == ValueType.BOOLEAN) {
            throw new RuntimeException("Cannot perform comparison with a boolean.",
                                       this.startingLine, this.startingColumn);
        } else if (value1.getType() == ValueType.NONE || value2.getType() == ValueType.NONE) {
            throw new RuntimeException("Cannot perform comparison with a none type. Specify a value first.",
                                       this.startingLine, this.startingColumn);
        } else if (value1.getType() == ValueType.STRING || value2.getType() == ValueType.STRING) {
            if (!this.operation.equals("==")) {
                throw new RuntimeException("Cannot perform > or < on strings.",
                                           this.startingLine, this.startingColumn);
            }

            return new Value(Boolean.toString(value1.equals(value2)), ValueType.BOOLEAN);
        } else if (value1.getType() == ValueType.FLOAT || value2.getType() == ValueType.FLOAT) {
            return new Value(Boolean.toString(this.compareFloats(value1, value2)), ValueType.BOOLEAN);
        } else if (value1.getType() == ValueType.INTEGER || value2.getType() == ValueType.INTEGER) {
            return new Value(Boolean.toString(this.compareInts(value1, value2)), ValueType.BOOLEAN);
        } else {
           throw new CompilerException("Cannot perform arithmetic operations with unknown types in"
                                       + " ComparisonNode.",
                                       this.startingLine, this.startingColumn);
        }
    }

    private boolean compareFloats(Value value1, Value value2) throws CompilerException
    {
        float float1 = Float.parseFloat(value1.getValue());
        float float2 = Float.parseFloat(value2.getValue());

        switch(this.operation) {
            case ">":
                return float1 > float2;
            case "<":
                return float1 < float2;
            case "==":
                return float1 == float2;
            default:
                throw new CompilerException("Unknown comparison operation, '" + this.operation + "'.",
                                            this.startingLine, this.startingColumn);
        }
    }

    private boolean compareInts(Value value1, Value value2) throws CompilerException
    {
        int int1 = Integer.parseInt(value1.getValue());
        int int2 = Integer.parseInt(value2.getValue());

        switch(this.operation) {
            case ">":
                return int1 > int2;
            case "<":
                return int1 < int2;
            case "==":
                return int1 == int2;
            default:
                throw new CompilerException("Unknown comparison operation, '" + this.operation + "'.",
                                            this.startingLine, this.startingColumn);
        }
    }
}