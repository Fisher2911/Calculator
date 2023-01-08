package io.github.fisher2911;

import io.github.fisher2911.math.Expression;
import io.github.fisher2911.math.MathFunction;
import io.github.fisher2911.parser.ExpressionBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        final Expression expression = ExpressionBuilder.builder("100 * 3 - 5 - 2 - 3 + 100 * 3 - 2 / 6").build();
//        final Expression expression = ExpressionBuilder.builder("((5 + 2) / 3 * 2 + 1 * (3 + 5 * (1 + 3))").build();
//        System.out.println(Arrays.toString(expression.evaluate(Collections.emptyMap())));
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter expression: ");
            final String expressionString = scanner.nextLine();
            if (expressionString.equalsIgnoreCase("exit")) {
                break;
            }
            final Expression expression1 = ExpressionBuilder.builder(expressionString).build();
            System.out.println(Arrays.toString(expression1.evaluate(Collections.emptyMap())));
        }
    }
}
