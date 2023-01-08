package io.github.fisher2911.math;

import io.github.fisher2911.util.Utils;

import java.util.Collection;

public enum TokenType {

    NUMBER,
    VARIABLE,
    OPERATOR,
    FUNCTION,
    LEFT_PARENTHESIS,
    RIGHT_PARENTHESIS,
    COMMA,
    WHITESPACE,
    UNKNOWN;

    public static TokenType fromString(String s, Collection<String> variables) {
        if (Utils.isInt(s) || Utils.isDouble(s) || s.equals(".")) {
            return NUMBER;
        }
        if (Operation.get(s) != null) {
            return OPERATOR;
        }
        if (MathFunction.get(s) != null) {
            return FUNCTION;
        }
        if (s.equals("(")) {
            return LEFT_PARENTHESIS;
        }
        if (s.equals(")")) {
            return RIGHT_PARENTHESIS;
        }
        if (s.equals(",")) {
            return COMMA;
        }
        if (s.isBlank() && s.length() > 0) {
            return WHITESPACE;
        }
        if (variables.contains(s)) {
            return VARIABLE;
        }
        return UNKNOWN;
    }

    public boolean isSingleCharacter() {
        return this == LEFT_PARENTHESIS || this == RIGHT_PARENTHESIS || this == COMMA;
    }

    public boolean hasNumberValue() {
        return this == NUMBER || this == VARIABLE;
    }

    public boolean createsUnaryOperator() {
        return this == OPERATOR || this == LEFT_PARENTHESIS;
    }

}
