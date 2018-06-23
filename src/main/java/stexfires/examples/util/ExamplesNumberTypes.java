package stexfires.examples.util;

import stexfires.util.NumberCheckType;
import stexfires.util.NumberComparisonType;
import stexfires.util.NumberUnaryOperatorType;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ExamplesNumberTypes {

    private static final int[] INT_VALUES = new int[]{
            Integer.MIN_VALUE,
            Integer.MIN_VALUE + 1,
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
            Integer.MAX_VALUE - 1,
            Integer.MAX_VALUE
    };

    private static final long[] LONG_VALUES = new long[]{
            Long.MIN_VALUE,
            Long.MIN_VALUE + 1L,
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
            Long.MAX_VALUE - 1L,
            Long.MAX_VALUE
    };

    private static final List<BigInteger> BIG_INTEGER_VALUES;

    static {
        List<BigInteger> values = new ArrayList<>();
        values.add(BigInteger.valueOf(Long.MIN_VALUE));
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
        values.add(BigInteger.valueOf(3L));
        values.add(BigInteger.valueOf(4L));
        values.add(BigInteger.valueOf(10L));
        values.add(BigInteger.TEN);
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

        for (NumberCheckType type : NumberCheckType.values()) {
            for (int value : INT_VALUES) {
                System.out.println(printTypeValue(type.name(), "int ", value)
                        + "? " + type.check(value));
            }
            for (long value : LONG_VALUES) {
                System.out.println(printTypeValue(type.name(), "long", value)
                        + "? " + type.check(value));
            }
            for (BigInteger value : BIG_INTEGER_VALUES) {
                System.out.println(printTypeValue(type.name(), "BigI", value)
                        + "? " + type.check(value));
            }
        }
    }

    private static void showNumberUnaryOperatorType() {
        System.out.println("-showNumberUnaryOperatorType---");

        for (NumberUnaryOperatorType type : NumberUnaryOperatorType.values()) {
            for (int value : INT_VALUES) {
                try {
                    System.out.println(printTypeValue(type.name(), "int ", value)
                            + "? " + type.operate(value));
                } catch (ArithmeticException e) {
                    System.out.println(printTypeValue(type.name(), "int ", value)
                            + "? " + e.getMessage());
                }
            }
            for (long value : LONG_VALUES) {
                try {
                    System.out.println(printTypeValue(type.name(), "long", value)
                            + "? " + type.operate(value));
                } catch (ArithmeticException e) {
                    System.out.println(printTypeValue(type.name(), "long ", value)
                            + "? " + e.getMessage());
                }
            }
            for (BigInteger value : BIG_INTEGER_VALUES) {
                try {
                    System.out.println(printTypeValue(type.name(), "BigI", value)
                            + "? " + type.operate(value));
                } catch (ArithmeticException e) {
                    System.out.println(printTypeValue(type.name(), "BigI ", value)
                            + "? " + e.getMessage());
                }
            }
        }
    }

    private static void showNumberComparisonType() {
        System.out.println("-showNumberComparisonType---");

        for (NumberComparisonType type : NumberComparisonType.values()) {
            for (int value1 : INT_VALUES) {
                for (int value2 : INT_VALUES) {
                    String result;
                    try {
                        result = String.valueOf(type.compare(value1, value2));
                    } catch (ArithmeticException e) {
                        result = e.getMessage();
                    }
                    if (!"false".equals(result)) {
                        System.out.println(type.name() + "\t int  (" + value1 + ", " + value2 + ")\t ? " + result);
                    }
                }
            }
            for (long value1 : LONG_VALUES) {
                for (long value2 : LONG_VALUES) {
                    String result;
                    try {
                        result = String.valueOf(type.compare(value1, value2));
                    } catch (ArithmeticException e) {
                        result = e.getMessage();
                    }
                    if (!"false".equals(result)) {
                        System.out.println(type.name() + "\t long (" + value1 + ", " + value2 + ")\t ? " + result);
                    }
                }
            }
            for (BigInteger value1 : BIG_INTEGER_VALUES) {
                for (BigInteger value2 : BIG_INTEGER_VALUES) {
                    if (value2 == null) {
                        continue;
                    }
                    String result;
                    try {
                        result = String.valueOf(type.compare(value1, value2));
                    } catch (ArithmeticException e) {
                        result = e.getMessage();
                    }
                    if (!"false".equals(result)) {
                        System.out.println(type.name() + "\t BigI (" + value1 + ", " + value2 + ")\t ? " + result);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        showNumberCheckType();
        showNumberUnaryOperatorType();
        showNumberComparisonType();
    }
}
