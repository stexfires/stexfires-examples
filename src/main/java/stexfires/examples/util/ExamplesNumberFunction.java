package stexfires.examples.util;

import stexfires.util.function.NumberPredicates.BigIntegerPredicates;
import stexfires.util.function.NumberPredicates.PrimitiveIntPredicates;
import stexfires.util.function.NumberPredicates.PrimitiveLongPredicates;
import stexfires.util.function.NumberUnaryOperators.BigIntegerUnaryOperators;
import stexfires.util.function.NumberUnaryOperators.PrimitiveIntUnaryOperators;
import stexfires.util.function.NumberUnaryOperators.PrimitiveLongUnaryOperators;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesNumberFunction {

    private static final int[] INT_VALUES = new int[]{
            Integer.MIN_VALUE,
            Integer.MIN_VALUE + 1,
            -12,
            -10,
            -4,
            -3,
            -2,
            -1,
            0,
            1,
            2,
            3,
            4,
            10,
            12,
            Integer.MAX_VALUE - 1,
            Integer.MAX_VALUE
    };

    private static final long[] LONG_VALUES = new long[]{
            Long.MIN_VALUE,
            Long.MIN_VALUE + 1L,
            -12L,
            -10L,
            -4L,
            -3L,
            -2L,
            -1L,
            0L,
            1L,
            2L,
            3L,
            4L,
            10L,
            12L,
            Long.MAX_VALUE - 1L,
            Long.MAX_VALUE
    };

    @SuppressWarnings("StaticCollection")
    private static final List<BigInteger> BIG_INTEGER_VALUES;

    static {
        List<BigInteger> values = new ArrayList<>();
        values.add(new BigInteger("-9999999999999999999999"));
        values.add(new BigInteger("-9223372036854775808"));
        values.add(BigInteger.valueOf(Long.MIN_VALUE));
        values.add(BigInteger.valueOf(Integer.MIN_VALUE));
        values.add(BigInteger.valueOf(-12L));
        values.add(BigInteger.valueOf(-10L));
        values.add(BigInteger.valueOf(-4L));
        values.add(BigInteger.valueOf(-3L));
        values.add(BigInteger.valueOf(-2L));
        values.add(BigInteger.valueOf(-1L));
        values.add(BigInteger.valueOf(0L));
        values.add(new BigInteger("-0"));
        values.add(new BigInteger("0"));
        values.add(new BigInteger("+0"));
        values.add(BigInteger.ZERO);
        values.add(BigInteger.valueOf(1L));
        values.add(BigInteger.ONE);
        values.add(BigInteger.valueOf(2L));
        values.add(BigInteger.TWO);
        values.add(BigInteger.valueOf(1L).shiftLeft(1));
        values.add(BigInteger.valueOf(3L));
        values.add(BigInteger.valueOf(4L));
        values.add(BigInteger.valueOf(10L));
        values.add(BigInteger.TEN);
        values.add(BigInteger.valueOf(12L));
        values.add(BigInteger.valueOf(Integer.MAX_VALUE));
        values.add(BigInteger.valueOf(Long.MAX_VALUE));
        values.add(new BigInteger("9223372036854775807"));
        values.add(new BigInteger("9999999999999999999999"));
        values.add(null);
        BIG_INTEGER_VALUES = Collections.unmodifiableList(values);
    }

    @SuppressWarnings("StaticCollection")
    private static final List<BigInteger> BIG_INTEGER_PARAMETER;

    static {
        List<BigInteger> values = new ArrayList<>();
        values.add(new BigInteger("-9999999999999999999999"));
        values.add(BigInteger.valueOf(-12L));
        values.add(BigInteger.valueOf(-10L));
        values.add(BigInteger.valueOf(-4L));
        values.add(BigInteger.valueOf(-3L));
        values.add(BigInteger.valueOf(-2L));
        values.add(BigInteger.valueOf(-1L));
        values.add(BigInteger.ZERO);
        values.add(BigInteger.ONE);
        values.add(BigInteger.TWO);
        values.add(BigInteger.valueOf(3L));
        values.add(BigInteger.valueOf(4L));
        values.add(BigInteger.TEN);
        values.add(BigInteger.valueOf(12L));
        values.add(new BigInteger("9999999999999999999999"));
        BIG_INTEGER_PARAMETER = Collections.unmodifiableList(values);
    }

    private ExamplesNumberFunction() {
    }

    private static void printMethodInfo(String className, String methodeName, String parameter) {
        StringBuilder b = new StringBuilder();
        b.append("---");
        b.append(className);
        b.append(" ");
        b.append(methodeName);
        if (parameter != null) {
            b.append(" (");
            b.append(parameter);
            b.append(")");
        }
        System.out.println(b);
    }

    private static String createResultString(Boolean result) {
        if (result == null) {
            return " <NULL> ";
        } else if (result) {
            return " TRUE   ";
        } else {
            return " false  ";
        }
    }

    private static void printResult(Boolean result, Number value, String exceptionMessage) {
        System.out.println(createResultString(result) + value + (exceptionMessage != null ? " " + exceptionMessage : ""));
    }

    private static void printResult(Number result, Number value, String exceptionMessage) {
        System.out.println(" " + (result != null ? result.toString() : "<NULL>") + " <-- " + value + (exceptionMessage != null ? " " + exceptionMessage : ""));
    }

    private static void testPrimitiveIntPredicate(String methodName, IntPredicate predicate, String parameter) {
        printMethodInfo("PrimitiveIntPredicates", methodName, parameter);

        for (int value : INT_VALUES) {
            Boolean result = null;
            String exceptionMessage = null;
            try {
                result = predicate.test(value);
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
    }

    private static void testPrimitiveLongPredicate(String methodName, LongPredicate predicate, String parameter) {
        printMethodInfo("PrimitiveLongPredicates", methodName, parameter);

        for (long value : LONG_VALUES) {
            Boolean result = null;
            String exceptionMessage = null;
            try {
                result = predicate.test(value);
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
    }

    private static void testBigIntegerPredicate(String methodName, Predicate<BigInteger> predicate, String parameter) {
        printMethodInfo("BigIntegerPredicates", methodName, parameter);

        for (BigInteger value : BIG_INTEGER_VALUES) {
            Boolean result = null;
            String exceptionMessage = null;
            try {
                result = predicate.test(value);
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
    }

    private static void applyPrimitiveIntUnaryOperator(String methodName, IntUnaryOperator operator, String parameter) {
        printMethodInfo("PrimitiveIntUnaryOperators", methodName, parameter);

        for (int value : INT_VALUES) {
            Number result = null;
            String exceptionMessage = null;
            try {
                result = operator.applyAsInt(value);
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
    }

    private static void applyPrimitiveLongUnaryOperator(String methodName, LongUnaryOperator operator, String parameter) {
        printMethodInfo("PrimitiveLongUnaryOperators", methodName, parameter);

        for (long value : LONG_VALUES) {
            Number result = null;
            String exceptionMessage = null;
            try {
                result = operator.applyAsLong(value);
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
    }

    private static void applyBigIntegerUnaryOperator(String methodName, UnaryOperator<BigInteger> operator, String parameter) {
        printMethodInfo("BigIntegerUnaryOperators", methodName, parameter);

        for (BigInteger value : BIG_INTEGER_VALUES) {
            Number result = null;
            String exceptionMessage = null;
            try {
                result = operator.apply(value);
            } catch (ArithmeticException e) {
                exceptionMessage = e.getMessage();
            }
            printResult(result, value, exceptionMessage);
        }
    }

    private static void showPrimitiveIntPredicates() {
        System.out.println("-showPrimitiveIntPredicates---");

        testPrimitiveIntPredicate("zero", PrimitiveIntPredicates.zero(), null);

        for (int parameter : INT_VALUES) {
            testPrimitiveIntPredicate("lessThan", PrimitiveIntPredicates.lessThan(parameter), String.valueOf(parameter));
        }
    }

    private static void showPrimitiveLongPredicates() {
        System.out.println("-showPrimitiveLongPredicates---");

        testPrimitiveLongPredicate("zero", PrimitiveLongPredicates.zero(), null);

        for (long parameter : LONG_VALUES) {
            testPrimitiveLongPredicate("lessThan", PrimitiveLongPredicates.lessThan(parameter), String.valueOf(parameter));
        }
    }

    private static void showBigIntegerPredicates() {
        System.out.println("-showBigIntegerPredicates---");

        testBigIntegerPredicate("zero", BigIntegerPredicates.zero(), null);

        for (BigInteger parameter : BIG_INTEGER_PARAMETER) {
            testBigIntegerPredicate("lessThan", BigIntegerPredicates.lessThan(parameter), String.valueOf(parameter));
        }
    }

    private static void showPrimitiveIntUnaryOperators() {
        System.out.println("-showPrimitiveIntUnaryOperators---");

        applyPrimitiveIntUnaryOperator("toZero", PrimitiveIntUnaryOperators.toZero(), null);

        for (int parameter : INT_VALUES) {
            applyPrimitiveIntUnaryOperator("addExact", PrimitiveIntUnaryOperators.addExact(parameter), String.valueOf(parameter));
        }
    }

    private static void showPrimitiveLongUnaryOperators() {
        System.out.println("-showPrimitiveLongUnaryOperators---");

        applyPrimitiveLongUnaryOperator("toZero", PrimitiveLongUnaryOperators.toZero(), null);

        for (long parameter : LONG_VALUES) {
            applyPrimitiveLongUnaryOperator("addExact", PrimitiveLongUnaryOperators.addExact(parameter), String.valueOf(parameter));
        }
    }

    private static void showBigIntegerUnaryOperators() {
        System.out.println("-showBigIntegerUnaryOperators---");

        applyBigIntegerUnaryOperator("toZero", BigIntegerUnaryOperators.toZero(), null);

        for (BigInteger parameter : BIG_INTEGER_PARAMETER) {
            applyBigIntegerUnaryOperator("add", BigIntegerUnaryOperators.add(parameter), String.valueOf(parameter));
        }
    }

    public static void main(String... args) {
        showPrimitiveIntPredicates();
        showPrimitiveLongPredicates();
        showBigIntegerPredicates();
        showPrimitiveIntUnaryOperators();
        showPrimitiveLongUnaryOperators();
        showBigIntegerUnaryOperators();
    }

}
