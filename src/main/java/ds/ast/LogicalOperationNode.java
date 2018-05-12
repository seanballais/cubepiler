package ds.ast;

import exceptions.CompilerException;
import exceptions.RuntimeException;

public class LogicalOperationNode extends OperationNode
{
    public LogicalOperationNode(String operation, Computable operator1, Computable operator2, int startingLine, int startingColumn)
    {
        super(operation, operator1, operator2, startingLine, startingColumn);
    }

    /**public Value compute() throws RuntimeException, CompilerException
    {
        Value value1 = this.operator1.compute();
        Value value2 = this.operator2.compute();

        // Question: Disallow using logical operators on non-booleans?
        Value result;
        boolean b;
        if (value1.getType() == ValueType.BOOLEAN && value2.getType() == ValueType.BOOLEAN) {
            if (value1.getValue().equals("")) {
                throw new CompilerException("value1 in LogicalOperationNode should not be empty",
                                             this.startingLine, this.startingColumn);
            } else if (value2.getValue().equals("") && !this.operation.equals("!")) {
                throw new CompilerException("value2 in LogicalOperationNode should not be empty",
                                             this.startingLine, this.startingColumn);
            }

            switch(this.operation) {
                case "&":
                    b = Boolean.parseBoolean(value1.getValue())
                        && Boolean.parseBoolean(value2.getValue());
                    result = new Value(Boolean.toString(b), ValueType.BOOLEAN);
                    break;
                case "|":
                    b = Boolean.parseBoolean(value1.getValue())
                        || Boolean.parseBoolean(value2.getValue());
                    result = new Value(Boolean.toString(b), ValueType.BOOLEAN);
                    break;
                case "!":
                    b = !Boolean.parseBoolean(value1.getValue());
                    result = new Value(Boolean.toString(b), ValueType.BOOLEAN);
                    break;
                default:
                    throw new CompilerException("Unknown logical operator in LogicalOperationNode.",
                                                this.startingLine, this.startingColumn);
            }
        } else {
            throw new RuntimeException("Operators should be of boolean type.",
                                       this.startingLine, this.startingColumn);
        }

        return result;
    }**/
}