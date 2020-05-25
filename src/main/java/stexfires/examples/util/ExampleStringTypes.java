package stexfires.examples.util;

import stexfires.util.StringCheckType;
import stexfires.util.StringComparisonType;
import stexfires.util.StringUnaryOperatorType;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "Java9CollectionFactory", "HardcodedLineSeparator"})
public final class ExampleStringTypes {

    private static final List<String> VALUES;
    private static final List<String> COMPARE_VALUES;

    static {
        List<String> values = new ArrayList<>();
        values.add("Hello world!");
        values.add("world");
        values.add("a");
        values.add("b");
        values.add("c");
        values.add("A");
        values.add("B");
        values.add("C");
        values.add("abc");
        values.add("ABC");
        values.add("Abc");
        values.add("aBc");
        values.add(" aBc");
        values.add("aBc ");
        values.add(" aBc ");
        values.add("a B c");
        values.add("a\tB\tc");
        values.add("\taBc");
        values.add("!\"#$%&'()*+,-./");
        values.add("1234567890");
        values.add(":;<=>?@[\\]^_`{|}~");
        values.add("A1");
        values.add("-123");
        values.add("123");
        values.add("+123");
        values.add("-1.23");
        values.add("1.23");
        values.add("+1.23");
        values.add(" \u20ac ");
        values.add(" \u00df ");
        values.add(" \u1e9e ");
        values.add(" \u00f6 ");
        values.add(" o\u0308 ");
        values.add(" \u00e9 ");
        values.add(" e\u0301 ");
        values.add(" \ufb03 ");
        values.add(" ffi ");
        values.add(" \u00e7 ");
        values.add(" c\u0327 ");
        values.add(" \u00c5 ");
        values.add(" A\u030a ");
        values.add(" \u212b ");
        values.add(null);
        values.add("");
        values.add(" ");
        values.add("  ");
        values.add(" \t ");
        values.add("a\r\nB\nc");
        values.add("aBc\r\n");
        values.add("\r\naBc");
        values.add("----");
        VALUES = Collections.unmodifiableList(values);

        List<String> compareValues = new ArrayList<>();
        compareValues.add("a");
        compareValues.add("c");
        compareValues.add("i");
        compareValues.add("o");
        compareValues.add("A");
        compareValues.add("D");
        compareValues.add("");
        compareValues.add("\t");
        compareValues.add("[ac]+");
        compareValues.add("[a-i]+");
        compareValues.add("\\d+");
        compareValues.add("\\p{Alnum}+");
        compareValues.add("\\p{Blank}+");
        COMPARE_VALUES = Collections.unmodifiableList(compareValues);
    }

    private ExampleStringTypes() {
    }

    private static String printTypeValue(String typeName, String value) {
        if (value == null) {
            return typeName + "\t (<null>) [-] \t";
        }
        return typeName + "\t ('" + value + "') [" + value.length() + "] \t";
    }

    private static String printResult(String value, String result) {
        String eq = Objects.equals(value, result) ? "==" : "!=";
        if (result == null) {
            return eq + " (<null>) [-]";
        }
        return eq + " ('" + result + "') [" + result.length() + "] \t hex: "
                + String.format("%04x", new BigInteger(1, result.getBytes(StandardCharsets.UTF_16BE)));
    }

    private static void showStringCheckType() {
        System.out.println("-showStringCheckType---");

        for (StringCheckType type : StringCheckType.values()) {
            for (String value : VALUES) {
                System.out.println(printTypeValue(type.name(), value) + "? "
                        + type.checkString(value));
            }
        }
    }

    private static void showStringComparisonType() {
        System.out.println("-showStringComparisonType---");

        for (StringComparisonType type : StringComparisonType.values()) {
            for (String value1 : VALUES) {
                for (String value2 : COMPARE_VALUES) {
                    String result;
                    try {
                        result = String.valueOf(type.compareString(value1, value2));
                    } catch (Exception e) {
                        result = e.getMessage();
                    }
                    if (!"false".equals(result)) {
                        System.out.println(type.name() + "\t ('" + value1 + "', '" + value2 + "') \t? " + result);
                    }
                }
            }
        }
    }

    private static void showStringUnaryOperatorType() {
        System.out.println("-showStringUnaryOperatorType---");

        for (StringUnaryOperatorType type : StringUnaryOperatorType.values()) {
            for (String value : VALUES) {
                System.out.println(printTypeValue(type.name(), value)
                        + printResult(value, type.operateString(value)));
            }
        }
    }

    public static void main(String[] args) {
        showStringCheckType();
        showStringComparisonType();
        showStringUnaryOperatorType();
    }

}
