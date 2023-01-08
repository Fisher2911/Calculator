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
        return new Expression(expression, tokenStack, variables);
    }
}
