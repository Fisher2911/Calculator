package io.github.fisher2911.parser;

import io.github.fisher2911.math.Expression;
import io.github.fisher2911.math.Token;
import io.github.fisher2911.math.TokenType;
import io.github.fisher2911.util.LIFOArrayDeque;

import java.util.ArrayDeque;
import java.util.Collection;

public class ExpressionParser {

    public static Expression parse(String expression, Collection<String> variables) {
        final ArrayDeque<Token> tokenStack = new ArrayDeque<>();
        final StringBuilder builder = new StringBuilder();
        System.out.println(expression);
        boolean unaryOperator = false;
        for (char c : expression.replaceAll("\\s+", "").toCharArray()) {
            final String charAsString = String.valueOf(c);
            final TokenType charType = TokenType.fromString(charAsString, variables);
            final String builderString = builder.toString();
            final TokenType builderType = TokenType.fromString(builderString, variables);
            if (charType.isSingleCharacter()) {
                if (builder.isEmpty()) {
                    builder.append(charAsString);
                    continue;
                }
                if (builderType == TokenType.UNKNOWN) {
                    throw new IllegalArgumentException("Unknown token: " + builderString);
                }
                tokenStack.add(new Token(builderType, builderString));
                builder.setLength(0);
                builder.append(charAsString);
                continue;
            }
            final TokenType combinedType = TokenType.fromString(builderString + charAsString, variables);
            if (builderType == combinedType || (unaryOperator && charType == TokenType.NUMBER && builderType == TokenType.OPERATOR)) {
                builder.append(charAsString);
                unaryOperator = false;
                continue;
            }
            final TokenType peekedType = tokenStack.isEmpty() ? TokenType.UNKNOWN : tokenStack.peekLast().type();
            final boolean createsUnaryOperator = (!builderType.hasNumberValue() && builderType != TokenType.RIGHT_PARENTHESIS) ||
                    (builderString.isEmpty() && !peekedType.hasNumberValue() && peekedType != TokenType.RIGHT_PARENTHESIS);
            if (charType == TokenType.OPERATOR && ((builder.isEmpty() && tokenStack.isEmpty()) || createsUnaryOperator /*!peekedType.hasNumberValue()*/)) {
                if (!builder.isEmpty()) {
                    tokenStack.add(new Token(builderType, builderString));
                    builder.setLength(0);
                }
                builder.append(charAsString);
                unaryOperator = true;
                continue;
            }
            if (builderString.isEmpty()) {
                builder.append(charAsString);
                continue;
            }
            if (builderType == TokenType.UNKNOWN) {
                builder.append(charAsString);
                continue;
            }
            tokenStack.add(new Token(builderType, builderString));
            builder.setLength(0);
            builder.append(charAsString);
        }
        if (!builder.isEmpty()) {
            tokenStack.add(new Token(TokenType.fromString(builder.toString(), variables), builder.toString()));
        }
        final StringBuilder parsed = new StringBuilder();
        final ArrayDeque<Token> finalStack = new ArrayDeque<>(tokenStack);
        while (!tokenStack.isEmpty()) {
            final Token token = tokenStack.pop();
            parsed.append(token.value()).append(" ");
        }
        System.out.println("Parsed: " + parsed);
        return new Expression(expression, finalStack, variables);
    }

//    public static Expression parse(String expression, Collection<String> variables) {
//        final char[] chars = expression.replaceAll("\\s+", "").toCharArray();
//        final ArrayDeque<Token> stack = new ArrayDeque<>();
//        final ArrayDeque<Character> characters = new ArrayDeque<>();
//        System.out.println(expression);
//        for (final char c : chars) {
//            final String charAsString = String.valueOf(c);
//            final StringBuilder builder = new StringBuilder();
//            while (!characters.isEmpty()) {
//                final char character = characters.pollFirst();
//                builder.append(character);
//            }
//            final String builderString = builder.toString();
//            final TokenType charType = TokenType.fromString(charAsString, variables);
//            final TokenType type = TokenType.fromString(builderString, variables);
//            if (charType.isSingleCharacter()) {
//                if (type != TokenType.UNKNOWN) {
//                    stack.add(new Token(type, builderString));
//                }
//                stack.add(new Token(charType, charAsString));
//                continue;
//            }
//            final TokenType previousType = stack.isEmpty() ? null : stack.peekLast().type();
//            final boolean isUnaryOperator = previousType == null || previousType.createsUnaryOperator() && builder.isEmpty() && charType == TokenType.OPERATOR;
//            System.out.println("isUnaryOperator: " + isUnaryOperator + " " + previousType + " " + c);
//            if (!builder.isEmpty() && (!isUnaryOperator || type != charType) && type != TokenType.UNKNOWN && previousType != type /* && type != charType*/ /*&& type != TokenType.UNKNOWN && !isPreviousOperator*/) {
//                System.out.println("Adding: " + builderString);
//                stack.add(new Token(type, builderString));
//            } else {
//                for (char character : builderString.toCharArray()) {
//                    characters.add(character);
//                }
//            }
//            characters.add(c);
//        }
//        if (characters.size() > 0) {
//            final StringBuilder builder = new StringBuilder();
//            while (!characters.isEmpty()) {
//                final char character = characters.pollFirst();
//                builder.append(character);
//            }
//            final String builderString = builder.toString();
//            final TokenType type = TokenType.fromString(builderString, variables);
//            stack.add(new Token(type, builderString));
//        }
//        final StringBuilder parsed = new StringBuilder();
//        final ArrayDeque<Token> finalStack = new ArrayDeque<>(stack);
//        System.out.println(stack);
//        while (!stack.isEmpty()) {
//            final Token token = stack.pop();
//            parsed.append(token.value()).append(" ");
//        }
//        System.out.println("Parsed: " + parsed);
//        return new Expression(expression, finalStack, variables);
//    }

    private static boolean isSameType(TokenType first, TokenType second, char secondValue) {
        if (first == TokenType.OPERATOR && second == TokenType.OPERATOR && secondValue == '-') {
            return false;
        }
        return first == second;
//        if (first == TokenType.OPERATOR && firstValue.equals(Operation.SUBTRACT.symbol()) && second == TokenType.NUMBER) {
//            return true;
//        }
//        return false;
    }
}
