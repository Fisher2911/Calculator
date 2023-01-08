package io.github.fisher2911.math;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class MathFunction implements java.util.function.Function<double[], double[]> {

    private static final Map<String, MathFunction> FUNCTIONS = new HashMap<>();

    public static final MathFunction SIN = register(new MathFunction("sin", 1, 1) {
        @Override
        public double[] apply(double[] doubles) {
            MathFunction.checkArgs(this, doubles);
            double[] result = new double[doubles.length];
            for (int i = 0; i < doubles.length; i++) {
                result[i] = Math.sin(doubles[i]);
            }
            return result;
        }
    });

    public static final MathFunction COS = register(new MathFunction("cos", 1, 1) {
        @Override
        public double[] apply(double[] doubles) {
            MathFunction.checkArgs(this, doubles);
            double[] result = new double[doubles.length];
            for (int i = 0; i < doubles.length; i++) {
                result[i] = Math.cos(doubles[i]);
            }
            return result;
        }
    });

    public static final MathFunction TAN = register(new MathFunction("tan", 1, 1) {
        @Override
        public double[] apply(double[] doubles) {
            MathFunction.checkArgs(this, doubles);
            double[] result = new double[doubles.length];
            for (int i = 0; i < doubles.length; i++) {
                result[i] = Math.tan(doubles[i]);
            }
            return result;
        }
    });

    public static final MathFunction ASIN = register(new MathFunction("asin", 1, 1) {
        @Override
        public double[] apply(double[] doubles) {
            MathFunction.checkArgs(this, doubles);
            double[] result = new double[doubles.length];
            for (int i = 0; i < doubles.length; i++) {
                result[i] = Math.asin(doubles[i]);
            }
            return result;
        }
    });

    public static final MathFunction ACOS = register(new MathFunction("acos", 1, 1) {
        @Override
        public double[] apply(double[] doubles) {
            MathFunction.checkArgs(this, doubles);
            double[] result = new double[doubles.length];
            for (int i = 0; i < doubles.length; i++) {
                result[i] = Math.acos(doubles[i]);
            }
            return result;
        }
    });

    public static final MathFunction ATAN = register(new MathFunction("atan", 1, 1) {
        @Override
        public double[] apply(double[] doubles) {
            MathFunction.checkArgs(this, doubles);
            double[] result = new double[doubles.length];
            for (int i = 0; i < doubles.length; i++) {
                result[i] = Math.atan(doubles[i]);
            }
            return result;
        }
    });

    public static final MathFunction SQRT = register(new MathFunction("sqrt", 1, 1) {
        @Override
        public double[] apply(double[] doubles) {
            MathFunction.checkArgs(this, doubles);
            double[] result = new double[doubles.length];
            for (int i = 0; i < doubles.length; i++) {
                result[i] = Math.sqrt(doubles[i]);
            }
            return result;
        }
    });


    public static final MathFunction LOG = register(new MathFunction("log", 1, 2) {
        @Override
        public double[] apply(double[] doubles) {
            MathFunction.checkArgs(this, doubles);
            final double base;
            if (doubles.length == 1) {
                base = 10;
            } else {
                base = doubles[1];
            }
            double[] result = new double[1];
            for (int i = 0; i < doubles.length; i++) {
                result[i] = Math.log(doubles[i]) / Math.log(base);
            }
            return result;
        }
    });

    public static final MathFunction LN = register(new MathFunction("ln", 1, 1) {
        @Override
        public double[] apply(double[] doubles) {
            MathFunction.checkArgs(this, doubles);
            double[] result = new double[doubles.length];
            for (int i = 0; i < doubles.length; i++) {
                result[i] = Math.log(doubles[i]);
            }
            return result;
        }
    });

    public static final MathFunction EXP = register(new MathFunction("exp", 1, 1) {
        @Override
        public double[] apply(double[] doubles) {
            MathFunction.checkArgs(this, doubles);
            double[] result = new double[doubles.length];
            for (int i = 0; i < doubles.length; i++) {
                result[i] = Math.exp(doubles[i]);
            }
            return result;
        }
    });

    public static final MathFunction ABS = register(new MathFunction("abs", 1, 1) {
        @Override
        public double[] apply(double[] doubles) {
            MathFunction.checkArgs(this, doubles);
            double[] result = new double[doubles.length];
            for (int i = 0; i < doubles.length; i++) {
                result[i] = Math.abs(doubles[i]);
            }
            return result;
        }
    });

    public static final MathFunction FLOOR = register(new MathFunction("floor", 1, 1) {
        @Override
        public double[] apply(double[] doubles) {
            MathFunction.checkArgs(this, doubles);
            double[] result = new double[doubles.length];
            for (int i = 0; i < doubles.length; i++) {
                result[i] = Math.floor(doubles[i]);
            }
            return result;
        }
    });

    public static final MathFunction CEIL = register(new MathFunction("ceil", 1, 1) {
        @Override
        public double[] apply(double[] doubles) {
            MathFunction.checkArgs(this, doubles);
            double[] result = new double[doubles.length];
            for (int i = 0; i < doubles.length; i++) {
                result[i] = Math.ceil(doubles[i]);
            }
            return result;
        }
    });

    public static final MathFunction ROUND = register(new MathFunction("round", 1, 1) {
        @Override
        public double[] apply(double[] doubles) {
            MathFunction.checkArgs(this, doubles);
            double[] result = new double[doubles.length];
            for (int i = 0; i < doubles.length; i++) {
                result[i] = Math.round(doubles[i]);
            }
            return result;
        }
    });

    public static final MathFunction MAX = register(new MathFunction("max", 2, Integer.MAX_VALUE) {
        @Override
        public double[] apply(double[] doubles) {
            MathFunction.checkArgs(this, doubles);
            double[] result = new double[1];
            result[0] = Math.max(doubles[0], doubles[1]);
            return result;
        }
    });

    public static final MathFunction MIN = register(new MathFunction("min", 2, Integer.MAX_VALUE) {
        @Override
        public double[] apply(double[] doubles) {
            MathFunction.checkArgs(this, doubles);
            double[] result = new double[1];
            result[0] = Math.min(doubles[0], doubles[1]);
            return result;
        }
    });

    private static MathFunction register(MathFunction function) {
        FUNCTIONS.put(function.name(), function);
        return function;
    }

    private static void checkArgs(MathFunction function, double[] input) {
        if (!function.isCorrectArgAmount(input.length)) {
            throw new IllegalArgumentException("Function " + function.name() + " requires between " + function.minArgs + " and " + (function.maxArgs() == -1 ? " infinite " : function.maxArgs()) + " arguments, but " + input.length + " were given");
        }
    }

    public static MathFunction get(String name) {
        return FUNCTIONS.get(name);
    }

    public static Collection<String> getFunctionNames() {
        return FUNCTIONS.keySet();
    }

    private final String name;
    private final int minArgs;
    private final int maxArgs;

    public MathFunction(String name, int minArgs, int maxArgs) {
        this.name = name;
        this.minArgs = minArgs;
        this.maxArgs = maxArgs;
    }

    public String name() {
        return this.name;
    }

    public int minArgs() {
        return this.minArgs;
    }

    public int maxArgs() {
        return this.maxArgs;
    }

    public boolean isCorrectArgAmount(int argAmount) {
        if (argAmount >= this.minArgs) {
            return this.maxArgs == -1 || argAmount <= this.maxArgs;
        }
        return false;
    }

}
