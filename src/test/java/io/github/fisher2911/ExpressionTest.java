package io.github.fisher2911;


import io.github.fisher2911.parser.ExpressionBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

public class ExpressionTest {

    @Test
    void testSmallEquation0() {
        Assertions.assertArrayEquals(
                new double[]{5 * 1 - -2 + 3 * 2 * 5 - 4 * 2 * 1 * 9532.352 * -341 + 5 + -5 - +3 * 2 * 3},
                ExpressionBuilder
                        .builder("5 * 1 - -2 + 3 * 2 * 5 - 4 * 2 * 1 * 9532.352 * -341 + 5 + -5 - +3 * 2 * 3").build().evaluate(Collections.emptyMap())
        );
    }

    @Test
    void testSmallEquation() {
        Assertions.assertArrayEquals(
                new double[]{1 + 2 * 7 + 3 + 4 * 2 / -1.5312},
                ExpressionBuilder
                        .builder("1 + 2 * 7 + 3 + 4 * 2 / -1.5312").build().evaluate(Collections.emptyMap())
        );
    }

    @Test
    void testSmallEquation2() {
        Assertions.assertArrayEquals(
                new double[]{1 + 2 * 7 + 3 + 4 * 2 / -1.5312 * 0345.1153},
                ExpressionBuilder
                        .builder("1 + 2 * 7 + 3 + 4 * 2 / -1.5312 * 0345.1153").build().evaluate(Collections.emptyMap())
        );
    }

    @Test
    void testSmallEquation3() {
        Assertions.assertArrayEquals(
                new double[]{1 + 2 * 7 + 3 + 4 * 2 / -1.5312 * 0345.1153 - 100 * 3.5 / 93 * 90.41},
                ExpressionBuilder
                        .builder("1 + 2 * 7 + 3 + 4 * 2 / -1.5312 * 0345.1153 - 100 * 3.5 / 93 * 90.41").build().evaluate(Collections.emptyMap())
        );
    }

    @Test
    void testParenthesis0() {
        Assertions.assertArrayEquals(
                new double[]{(((1) * 2 + ((2))))},
                ExpressionBuilder
                        .builder("((1) * 2 + ((2)))").build().evaluate(Collections.emptyMap())
        );
    }

    @Test
    void testSimpleEquation() {
        Assertions.assertArrayEquals(
                new double[]{1 + 2 - 3 + 4 + 5 * 9 + 3.0 / 6 * 2 / 3.6612 * 1000},
                ExpressionBuilder
                .builder("1 + 2 - 3 + 4 + 5 * 9 + 3 / 6 * 2 / 3.6612 * 1000").build().evaluate(Collections.emptyMap())
        );
    }

    @Test
    void testSimpleEquation2() {
        Assertions.assertArrayEquals(
                new double[]{1.0 / 30234 * 39.241 - 85.423 * 194 / 0.3515 - 32 + 17 * 3 - 100 / 3.51},
                ExpressionBuilder
                        .builder("1.0 / 30234 * 39.241 - 85.423 * 194 / 0.3515 - 32 + 17 * 3 - 100 / 3.51").build().evaluate(Collections.emptyMap())
        );
    }

    @Test
    void testParenthesisEquation() {
        Assertions.assertArrayEquals(
                new double[]{(1 + 2 - 3 + 4 + Math.pow(5, 2)) * 9 + 3.0 / (6 * 2 / 3.6612 * 1000)},
                ExpressionBuilder
                        .builder("(1 + 2 - 3 + 4 + 5 ^ 2) * 9 + 3.0 / (6 * 2 / 3.6612 * 1000)").build().evaluate(Collections.emptyMap())
        );
    }

    @Test
    void testParenthesisEquation2() {
        Assertions.assertArrayEquals(
                new double[]{(1 + 2 - (((-3))) + 4 + 5) * 9 + 3.0 / 6 * (2 / (3.6612)) * 1000},
                ExpressionBuilder
                        .builder("(1 + 2 - (((-3))) + 4 + 5) * 9 + 3.0 / 6 * (2 / (3.6612)) * 1000").build().evaluate(Collections.emptyMap())
        );
    }

    @Test
    void testFunctionsEquation0() {
        Assertions.assertArrayEquals(
                new double[]{Math.cos(Math.cos(3))},
                ExpressionBuilder
                        .builder("cos((cos(((3)))))").build().evaluate(Collections.emptyMap())
        );
    }

    @Test
    void testFunctionsEquation1() {
        Assertions.assertArrayEquals(
                new double[]{(1 + 2 - ((Math.cos(-3))) + 4 + 5) * 9 + Math.tan(3.0) / 6 * (2 / (3.6612)) * 1000},
                ExpressionBuilder
                        .builder("(1 + 2 - ((cos(-3))) + 4 + 5) * 9 + tan(3.0) / 6 * (2 / (3.6612)) * 1000").build().evaluate(Collections.emptyMap())
        );
    }

    @Test
    void testVariablesEquation0() {
        Assertions.assertArrayEquals(
                new double[]{1 + 3 * 2 + 6},
                ExpressionBuilder
                        .builder("1 + 3x + 6").withVariables("x").build().evaluate(Map.of("x", 2.0))
        );
    }

    @Test
    void testVariablesEquation1() {
        Assertions.assertArrayEquals(
                new double[]{1 + 3 * 2 / 7.0 - 3.712 + 6},
                ExpressionBuilder
                        .builder("a + 3b / c - d + e").withVariables("a", "b", "c", "d", "e").build().evaluate(Map.of("a", 1.0, "b", 2.0, "c", 7.0, "d", 3.712, "e", 6.0))
        );
    }
}
