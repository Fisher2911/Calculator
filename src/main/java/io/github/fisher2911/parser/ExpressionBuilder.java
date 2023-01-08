package io.github.fisher2911.parser;

import io.github.fisher2911.math.Expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ExpressionBuilder {

    private final String expression;
    private final Set<String> variables;

    private ExpressionBuilder(String expression, Set<String> variables) {
        this.expression = expression;
        this.variables = variables;
    }

    public static ExpressionBuilder builder(String expression) {
        return new ExpressionBuilder(expression, new HashSet<>());
    }

    public ExpressionBuilder withVariable(String variable) {
        this.variables.add(variable);
        return this;
    }

    public ExpressionBuilder withVariables(String... variables) {
        this.variables.addAll(Arrays.asList(variables));
        return this;
    }

    public Expression build() {
        return ExpressionParser.parse(this.expression, this.variables);
    }
}
