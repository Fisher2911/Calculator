package io.github.fisher2911.math;

import io.github.fisher2911.util.LIFOArrayDeque;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused", "ClassCanBeRecord"})
public class Expression {

    private final String expression;
    private final ArrayDeque<Token> tokenStack;
    private final Collection<String> variables;

    public Expression(String expression, ArrayDeque<Token> tokenStack, Collection<String> variables) {
        this.expression = expression;
        this.tokenStack = tokenStack;
        this.variables = variables;
    }

    public double[] evaluate(Map<String, Double> variableValues) {
        final ArrayDeque<Token> copyStack = new ArrayDeque<>();
        for (Token token : this.tokenStack) {
            if (token.type() == TokenType.VARIABLE) {
                if (!copyStack.isEmpty()) {
                    final Token previous = copyStack.peekLast();
                    if (previous.type().hasNumberValue()) {
                        copyStack.add(new Token(TokenType.OPERATOR, "*"));
                    }
                }
                copyStack.add(new Token(TokenType.NUMBER, String.valueOf(variableValues.get(token.value()))));
                continue;
            }
            copyStack.add(token);
        }
        return parse(copyStack);
    }

    private double[] parse(ArrayDeque<Token> stack) {
        final ArrayDeque<Token> numberStack = new LIFOArrayDeque<>();
        final ArrayDeque<Token> operatorStack = new LIFOArrayDeque<>();
        final ArrayDeque<Token> functionStack = new LIFOArrayDeque<>();
        while (!stack.isEmpty()) {
            final Token token = stack.pop();
            switch (token.type()) {
                case FUNCTION -> functionStack.add(token);
                case LEFT_PARENTHESIS -> {
                    final double parsed = this.parse(stack)[0];
                    if (!functionStack.isEmpty()) {
                        final MathFunction function = MathFunction.get(functionStack.pop().value());
                        numberStack.add(new Token(TokenType.NUMBER, String.valueOf(function.apply(new double[]{parsed})[0])));
                        continue;
                    }
                    numberStack.add(new Token(TokenType.NUMBER, String.valueOf(parsed)));
                }
                case NUMBER, RIGHT_PARENTHESIS -> {
                    if (token.type() == TokenType.NUMBER) {
                        numberStack.add(token);
                    }
                    if (stack.isEmpty() || token.type() == TokenType.RIGHT_PARENTHESIS) {
                        this.completeStack(operatorStack, numberStack);
                    }
                    if (token.type() == TokenType.RIGHT_PARENTHESIS) {
                        return new double[]{Double.parseDouble(numberStack.pop().value())};
                    }
                }
                case OPERATOR -> {
                    if (operatorStack.isEmpty()) {
                        operatorStack.add(token);
                        continue;
                    }
                    final Token previousOperatorToken = operatorStack.peek();
                    final Operation previousOperation = Operation.get(previousOperatorToken.value());
                    final Operation operation = Operation.get(token.value());
                    if (previousOperation.priority() >= operation.priority()) {
                        operatorStack.pop();
                        this.doOperation(numberStack, previousOperation);
                        this.reduceToOneNumberInStack(numberStack, operatorStack, operation.priority());
                        operatorStack.add(token);
                        continue;
                    }
                    operatorStack.add(token);
                }
            }
        }
        this.completeStack(operatorStack, numberStack);
        return new double[]{Double.parseDouble(numberStack.pop().value())};
    }

    private void completeStack(ArrayDeque<Token> operatorStack, ArrayDeque<Token> numberStack) {
        while (!operatorStack.isEmpty()) {
            final Operation operation = Operation.get(operatorStack.pop().value());
            final double right = Double.parseDouble(numberStack.pop().value());
            final double left = Double.parseDouble(numberStack.pop().value());
            numberStack.add(new Token(TokenType.NUMBER, String.valueOf(operation.apply(new double[]{left, right})[0])));
        }
    }

    private void reduceToOneNumberInStack(ArrayDeque<Token> numberStack, ArrayDeque<Token> operationStack, int originalPriority) {
        while (numberStack.size() > 1) {
            final Token operatorToken = operationStack.peek();
            if (operatorToken == null) break;
            final Operation operation = Operation.get(operatorToken.value());
            if (operation.priority() < originalPriority) break;
            operationStack.pop();
            this.doOperation(numberStack, operation);
        }
    }

    private void doOperation(ArrayDeque<Token> numberStack, Operation operation) {
        final Token numberToken1 = numberStack.pop();
        final Token numberToken2 = numberStack.pop();
        final double right = Double.parseDouble(numberToken1.value());
        final double left;
        if (numberToken2.type() == TokenType.RIGHT_PARENTHESIS) {
            left = this.parse(new ArrayDeque<>(List.of(numberToken2)))[0];
        } else {
            left = Double.parseDouble(numberToken2.value());
        }
        numberStack.add(new Token(TokenType.NUMBER, String.valueOf(operation.apply(new double[]{left, right})[0])));
    }

    // this works without functions
//    private double[] parse(ArrayDeque<Token> stack) {
//        final ArrayDeque<Token> numberStack = new LIFOArrayDeque<>();
//        final ArrayDeque<Token> operatorStack = new LIFOArrayDeque<>();
//        while (!stack.isEmpty()) {
//            final Token token = stack.pop();
//            switch (token.type()) {
//                case LEFT_PARENTHESIS -> numberStack.add(new Token(TokenType.NUMBER, String.valueOf(this.parse(stack)[0])));
//                case NUMBER, RIGHT_PARENTHESIS -> {
//                    if (token.type() == TokenType.NUMBER) {
//                        numberStack.add(token);
//                    }
//                    if (stack.isEmpty() || token.type() == TokenType.RIGHT_PARENTHESIS) {
//                        this.completeStack(operatorStack, numberStack);
//                    }
//                    if (token.type() == TokenType.RIGHT_PARENTHESIS) {
//                        return new double[]{Double.parseDouble(numberStack.pop().value())};
//                    }
//                }
//                case OPERATOR -> {
//                    if (operatorStack.isEmpty()) {
//                        operatorStack.add(token);
//                        continue;
//                    }
//                    final Token previousOperatorToken = operatorStack.peek();
//                    final Operation previousOperation = Operation.get(previousOperatorToken.value());
//                    final Operation operation = Operation.get(token.value());
//                    if (previousOperation.priority() >= operation.priority()) {
//                        operatorStack.pop();
//                        this.doOperation(numberStack, previousOperation);
//                        this.reduceToOneNumberInStack(numberStack, operatorStack, operation.priority());
//                        operatorStack.add(token);
//                        continue;
//                    }
//                    operatorStack.add(token);
//                }
//            }
//        }
//        this.completeStack(operatorStack, numberStack);
//        return new double[]{Double.parseDouble(numberStack.pop().value())};
//    }
//
//    private void completeStack(ArrayDeque<Token> operatorStack, ArrayDeque<Token> numberStack) {
//        while (!operatorStack.isEmpty()) {
//            final Operation operation = Operation.get(operatorStack.pop().value());
//            final double right = Double.parseDouble(numberStack.pop().value());
//            final double left = Double.parseDouble(numberStack.pop().value());
//            numberStack.add(new Token(TokenType.NUMBER, String.valueOf(operation.apply(new double[]{left, right})[0])));
//        }
//    }
//
//    private void reduceToOneNumberInStack(ArrayDeque<Token> numberStack, ArrayDeque<Token> operationStack, int originalPriority) {
//        while (numberStack.size() > 1) {
//            final Token operatorToken = operationStack.peek();
//            if (operatorToken == null) break;
//            final Operation operation = Operation.get(operatorToken.value());
//            if (operation.priority() < originalPriority) break;
//            operationStack.pop();
//            this.doOperation(numberStack, operation);
//        }
//    }
//
//    private void doOperation(ArrayDeque<Token> numberStack, Operation operation) {
//        final Token numberToken1 = numberStack.pop();
//        final Token numberToken2 = numberStack.pop();
//        final double right = Double.parseDouble(numberToken1.value());
//        final double left;
//        if (numberToken2.type() == TokenType.RIGHT_PARENTHESIS) {
//            left = this.parse(new ArrayDeque<>(List.of(numberToken2)))[0];
//        } else {
//            left = Double.parseDouble(numberToken2.value());
//        }
//        numberStack.add(new Token(TokenType.NUMBER, String.valueOf(operation.apply(new double[]{left, right})[0])));
//    }

    private String insertVariables(String string, Map<String, Double> variableValues) {
        String replaced = string;
        for (String variable : this.variables) {
            replaced = replaced.replace(variable, String.valueOf(variableValues.get(variable)));
        }
        return replaced;
    }

    public String expression() {
        return this.expression;
    }

    public ArrayDeque<Token> tokenStack() {
        return this.tokenStack;
    }

    public Collection<String> variables() {
        return this.variables;
    }
}
