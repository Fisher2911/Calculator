package io.github.fisher2911;

import io.github.fisher2911.math.Expression;
import io.github.fisher2911.parser.ExpressionBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
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
