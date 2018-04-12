package lexer;

public enum TokenType {
    // Data types
    INTEGER, FLOAT, BOOLEAN, STRING,

    // Operators
    ARITHMETIC_OPERATOR, LOGICAL_OPERATOR, ASSIGNMENT_OPERATOR, COMPARISON_OPERATOR,

    // Reserved words
    BREAK_KEYWORD, RETURN_KEYWORD, END_KEYWORD, IF_KEYWORD, ELSE_KEYWORD, FUNCTION_DECLARATION, VARIABLE_DECLARATION,

    // Miscellaneous
    USER_DEFINED_SYMBOL, NEWLINE
}