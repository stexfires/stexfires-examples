package stexfires.examples.util;

import stexfires.util.NumberCheckType;
import stexfires.util.NumberComparisonType;
import stexfires.util.NumberUnaryOperatorType;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesNumberTypes {

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
        values.add(BigInteger.valueOf(Long.MIN_VALUE));
        values.add(BigInteger.valueOf(Integer.MIN_VALUE));
        values.add(BigInteger.valueOf(-12L));
        values.add(BigInteger.valueOf(-10L));
        values.add(BigInteger.valueOf(-4L));
        values.add(BigInteger.valueOf(-3L));
        values.add(BigInteger.valueOf(-2L));
        values.add(BigInteger.valueOf(-1L));
        values.add(BigInteger.valueOf(0L));
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
        values.add(null);
        BIG_INTEGER_VALUES = Collections.unmodifiableList(values);
    }

    private ExamplesNumberTypes() {
    }

    private static String printTypeValue(String typeName, String numberType, Number value) {
        if (value == null) {
            return typeName + "\t " + numberType + " (<null>) \t";
        }
        return typeName + "\t " + numberType + " (" + value + ") \t";
    }

    private static void showNumberCheckType() {
        System.out.println("-showNumberCheckType---");

        System.out.println("static intPredicate: " + NumberCheckType.intPredicate(NumberCheckType.POSITIVE).test(1));
        System.out.println("       intPredicate: " + NumberCheckType.POSITIVE.intPredicate().test(1));

        System.out.println("static longPredicate: " + NumberCheckType.longPredicate(NumberCheckType.POSITIVE).test(1L));
        System.out.println("       longPredicate: " + NumberCheckType.POSITIVE.longPredicate().test(1L));

        System.out.println("static bigIntegerPredicate: " + NumberCheckType.bigIntegerPredicate(NumberCheckType.POSITIVE).test(BigInteger.ONE));
        System.out.println("       bigIntegerPredicate: " + NumberCheckType.POSITIVE.bigIntegerPredicate().test(BigInteger.ONE));

        System.out.println("-INT_VALUES");
        for (NumberCheckType type : NumberCheckType.values()) {
            for (int value : INT_VALUES) {
                System.out.println(printTypeValue(type.name(), "int ", value)
                        + "? " + type.checkInt(value));
            }
        }
        System.out.println("-LONG_VALUES");
        for (NumberCheckType type : NumberCheckType.values()) {
            for (long value : LONG_VALUES) {
                System.out.println(printTypeValue(type.name(), "long", value)
                        + "? " + type.checkLong(value));
            }
        }
        System.out.println("-BIG_INTEGER_VALUES");
        for (NumberCheckType type : NumberCheckType.values()) {
            for (BigInteger value : BIG_INTEGER_VALUES) {
                System.out.println(printTypeValue(type.name(), "BigI", value)
                        + "? " + type.checkBigInteger(value));
            }
        }
    }

    private static void showNumberComparisonType() {
        System.out.println("-showNumberComparisonType---");

        System.out.println("static intPredicate: " + NumberComparisonType.intPredicate(NumberComparisonType.LESS_THAN, 0).test(1));
        System.out.println("       intPredicate: " + NumberComparisonType.LESS_THAN.intPredicate(0).test(1));

        System.out.println("static longPredicate: " + NumberComparisonType.longPredicate(NumberComparisonType.LESS_THAN, 0L).test(1L));
        System.out.println("       longPredicate: " + NumberComparisonType.LESS_THAN.longPredicate(0).test(1L));

        System.out.println("static bigIntegerPredicate: " + NumberComparisonType.bigIntegerPredicate(NumberComparisonType.LESS_THAN, BigInteger.ZERO).test(BigInteger.ONE));
        System.out.println("       bigIntegerPredicate: " + NumberComparisonType.LESS_THAN.bigIntegerPredicate(BigInteger.ZERO).test(BigInteger.ONE));

        System.out.println("-INT_VALUES");
        for (NumberComparisonType type : NumberComparisonType.values()) {
            for (int value1 : INT_VALUES) {
                for (int value2 : INT_VALUES) {
                    String result;
                    try {
                        result = String.valueOf(type.compareInt(value1, value2));
                    } catch (ArithmeticException e) {
                        result = e.getMessage();
                    }
                    System.out.println(printTypeValue(type.name(), "int ", value1) + "(" + value2 + ") ? " + result);
                }
            }
        }
        System.out.println("-LONG_VALUES");
        for (NumberComparisonType type : NumberComparisonType.values()) {
            for (long value1 : LONG_VALUES) {
                for (long value2 : LONG_VALUES) {
                    String result;
                    try {
                        result = String.valueOf(type.compareLong(value1, value2));
                    } catch (ArithmeticException e) {
                        result = e.getMessage();
                    }
                    System.out.println(printTypeValue(type.name(), "long ", value1) + "(" + value2 + ") ? " + result);
                }
            }
        }
        System.out.println("-BIG_INTEGER_VALUES");
        for (NumberComparisonType type : NumberComparisonType.values()) {
            for (BigInteger value1 : BIG_INTEGER_VALUES) {
                for (BigInteger value2 : BIG_INTEGER_VALUES) {
                    if (value2 == null) {
                        continue;
                    }
                    String result;
                    try {
                        result = String.valueOf(type.compareBigInteger(value1, value2));
                    } catch (ArithmeticException e) {
                        result = e.getMessage();
                    }
                    System.out.println(printTypeValue(type.name(), "BigI ", value1) + "(" + value2 + ") ? " + result);
                }
            }
        }
    }

    private static void showNumberUnaryOperatorType() {
        System.out.println("-showNumberUnaryOperatorType---");

        System.out.println("-INT_VALUES");
        for (NumberUnaryOperatorType type : NumberUnaryOperatorType.values()) {
            for (int value : INT_VALUES) {
                try {
                    System.out.println(printTypeValue(type.name(), "int ", value)
                            + "? " + type.operateInt(value));
                } catch (ArithmeticException e) {
                    System.out.println(printTypeValue(type.name(), "int ", value)
                            + "? " + e.getMessage());
                }
            }
        }
        System.out.println("-LONG_VALUES");
        for (NumberUnaryOperatorType type : NumberUnaryOperatorType.values()) {
            for (long value : LONG_VALUES) {
                try {
                    System.out.println(printTypeValue(type.name(), "long", value)
                            + "? " + type.operateLong(value));
                } catch (ArithmeticException e) {
                    System.out.println(printTypeValue(type.name(), "long ", value)
                            + "? " + e.getMessage());
                }
            }
        }
        System.out.println("-BIG_INTEGER_VALUES");
        for (NumberUnaryOperatorType type : NumberUnaryOperatorType.values()) {
            for (BigInteger value : BIG_INTEGER_VALUES) {
                try {
                    System.out.println(printTypeValue(type.name(), "BigI", value)
                            + "? " + type.operateBigInteger(value));
                } catch (ArithmeticException e) {
                    System.out.println(printTypeValue(type.name(), "BigI ", value)
                            + "? " + e.getMessage());
                }
            }
        }
    }

    public static void main(String... args) {
        showNumberCheckType();
        showNumberComparisonType();
        showNumberUnaryOperatorType();
    }
}
