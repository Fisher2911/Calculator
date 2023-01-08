package io.github.fisher2911.math;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class Operation implements Function<double[], double[]> {

    private static final Map<String, Operation> operations = new HashMap<>();

    public static final Operation ADD = register(new Operation("+", 2, 2, 0) {
        @Override
        public double[] apply(double[] doubles) {
            Operation.checkArgs(this, doubles);
            double[] result = new double[1];
            final double a = doubles[0];
            final double b = doubles[1];
            result[0] = a + b;
//            System.out.println("Adding: " + a + " + " + b + " = " + result[0]);
            return result;
        }
    });

    public static final Operation SUBTRACT = register(new Operation("-", 2, 2, 0) {
        @Override
        public double[] apply(double[] doubles) {
            Operation.checkArgs(this, doubles);
            double[] result = new double[1];
            final double a = doubles[0];
            final double b = doubles[1];
            result[0] = a - b;
//            System.out.println("Subtracting: " + a + " - " + b + " = " + result[0]);
            return result;
        }
    });

    public static final Operation MULTIPLY = register(new Operation("*", 2, 2, 1) {
        @Override
        public double[] apply(double[] doubles) {
            Operation.checkArgs(this, doubles);
            double[] result = new double[1];
            final double a = doubles[0];
            final double b = doubles[1];
            result[0] = a * b;
//            System.out.println("Multiplying: " + a + " * " + b + " = " + result[0]);
            return result;
        }
    });

    public static final Operation DIVIDE = register(new Operation("/", 2, 2, 1) {
        @Override
        public double[] apply(double[] doubles) {
            Operation.checkArgs(this, doubles);
            double[] result = new double[1];
            final double a = doubles[0];
            final double b = doubles[1];
            result[0] = a / b;
//            System.out.println("Dividing: " + a + " / " + b + " = " + result[0]);
            return result;
        }
    });

    public static final Operation POWER = register(new Operation("^", 2, 2, 2) {
        @Override
        public double[] apply(double[] doubles) {
            Operation.checkArgs(this, doubles);
            double[] result = new double[1];
            final double a = doubles[0];
            final double b = doubles[1];
            result[0] = Math.pow(a, b);
//            System.out.println("Powering: " + a + " ^ " + b + " = " + result[0]);
            return result;
        }
    });

    public static final Operation MODULO = register(new Operation("%", 2, 2, 1) {
        @Override
        public double[] apply(double[] doubles) {
            Operation.checkArgs(this, doubles);
            double[] result = new double[1];
            final double a = doubles[0];
            final double b = doubles[1];
            result[0] = a % b;
            return result;
        }
    });

    public static Operation register(Operation operation) {
        operations.put(operation.symbol(), operation);
        return operation;
    }

    public static Operation get(String symbol) {
        return operations.get(symbol);
    }

    private static void checkArgs(Operation operation, double[] input) {
        if (!operation.isCorrectArgAmount(input.length)) {
            throw new IllegalArgumentException("Function " + operation.symbol + " requires between " + operation.minArgs() + " and " + (operation.maxArgs() == -1 ? " infinite " : operation.maxArgs()) + " arguments, but " + input.length + " were given");
        }
    }

    private final String symbol;
    private final int minArgs;
    private final int maxArgs;
    private final int priority;

    public Operation(String symbol, int minArgs, int maxArgs, int priority) {
        this.symbol = symbol;
        this.minArgs = minArgs;
        this.maxArgs = maxArgs;
        this.priority = priority;
    }

    public String symbol() {
        return this.symbol;
    }

    public int minArgs() {
        return this.minArgs;
    }

    public int maxArgs() {
        return this.maxArgs;
    }

    public int priority() {
        return this.priority;
    }

    public boolean isCorrectArgAmount(int argAmount) {
        if (argAmount >= this.minArgs) {
            return this.maxArgs == -1 || argAmount <= this.maxArgs;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "symbol='" + symbol + '\'' +
                ", minArgs=" + minArgs +
                ", maxArgs=" + maxArgs +
                ", priority=" + priority +
                '}';
    }
}
