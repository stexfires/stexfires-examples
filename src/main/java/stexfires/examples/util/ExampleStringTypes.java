package stexfires.examples.util;

import stexfires.util.StringCheckType;
import stexfires.util.StringComparisonType;
import stexfires.util.function.StringUnaryOperators;
import stexfires.util.supplier.SequenceStringSupplier;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "Java9CollectionFactory", "HardcodedLineSeparator", "SpellCheckingInspection"})
public final class ExampleStringTypes {

    @SuppressWarnings("StaticCollection")
    private static final List<String> VALUES;
    @SuppressWarnings("StaticCollection")
    private static final List<String> COMPARE_VALUES;

    static {
        List<String> values = new ArrayList<>();

        // null, empty, blank, spaces, escapes
        values.add(null);
        values.add("");
        values.add(" ");
        values.add("  ");
        values.add("\t");
        values.add("\t\t");
        values.add(" \t ");
        values.add("\n");
        values.add("\r\n");
        values.add(" \r\n ");
        values.add(" \\ ");
        values.add(" \\\\ ");
        values.add(" \n ");
        values.add(" \\n ");
        values.add("\"");

        // Spaces around and beetween letters
        values.add("abc");
        values.add("abc ");
        values.add(" abc");
        values.add(" a b c ");
        values.add("a b c");
        values.add("a\tb\tc");
        values.add(" a\tb\tc ");
        values.add("\tabc");
        values.add("a\r\nb\nc");
        values.add("abc\r\n");
        values.add("\r\nabc");

        // letters
        values.add("a");
        values.add(" a ");
        values.add("A");
        values.add("ä");
        values.add("Ä");
        values.add("\u00e0");
        values.add("\u00e1");
        values.add("\u00e2");
        values.add("\u00e3");
        values.add("\u00e4");
        values.add("\u00e5");
        values.add("\u00e6");
        values.add("\u212b");
        values.add("a\u0308");
        values.add("A\u0308");
        values.add("A\u030a");
        values.add("aa");
        values.add("aA");
        values.add("Aa");
        values.add("AA");
        values.add("A1");
        values.add("a1");

        values.add("\u00df");
        values.add("\u1e9e");
        values.add("s");
        values.add("ss");
        values.add("S");
        values.add("SS");

        values.add("\u00e7");
        values.add("c\u0327");

        values.add("\u00e9");
        values.add("e\u0301");

        values.add("\u00f6");
        values.add("o\u0308");

        values.add("\ufb03");
        values.add("ffi");

        // two chars for one codePoint
        values.add("\uD83D\uDE00");
        values.add("\uD83D\uDE01");
        values.add("\uD83D\uDE00\uD83D\uDE00");

        // symbols, punctuations, signs
        values.add("\u20ac");
        values.add("!#§$%&*+,-./:;=?@_");
        values.add("()[]{}<>");
        values.add("'");
        values.add("`");
        values.add("^");
        values.add("°²µ|~");
        values.add("%1$s %2$d");

        // numbers
        values.add("0");
        values.add("1");
        values.add("12");
        values.add("123");
        values.add("1234");
        values.add("12345");
        values.add("123456");
        values.add("1.2");
        values.add("1,2");
        values.add("0123456789");
        values.add("0123456789ABCDEF");
        values.add("-123");
        values.add("123");
        values.add("+123");
        values.add("-1.23");
        values.add("1.23");
        values.add("+1.23");

        // words, sentences
        values.add("Hello world!");
        values.add("Hello");
        values.add("world");
        values.add("lo wo");
        values.add("world!");
        values.add("!");
        values.add("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.");

        // indent
        //noinspection TextBlockMigration
        values.add("  1.\n"
                + "    1.1\n"
                + "    1.2\n"
                + "  2.\n"
                + "    2.1\n"
                + "  3.\n"
                + "    3.1\n"
                + "      3.1.1");
        values.add("""
                1.
                  1.1
                  1.2
                2.
                  2.1
                3.
                  3.1
                    3.1.1
                """);

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

        System.out.println("static stringPredicate: " + StringCheckType.stringPredicate(StringCheckType.EMPTY).test(""));
        System.out.println("       stringPredicate: " + StringCheckType.EMPTY.stringPredicate().test(""));

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
                    } catch (RuntimeException e) {
                        result = e.getMessage();
                    }
                    if (!"false".equals(result)) {
                        System.out.println(type.name() + "\t ('" + value1 + "', '" + value2 + "') \t? " + result);
                    }
                }
            }
        }
    }

    private static void printUnaryOperator(UnaryOperator<String> operator, String name) {
        for (String value : VALUES) {
            String result;
            try {
                result = operator.apply(value);
            } catch (IllegalArgumentException e) {
                result = e.toString();
            }
            System.out.println(printTypeValue(name, value)
                    + printResult(value, result));
        }

    }

    private static void showStringUnaryOperators() {
        System.out.println("-showStringUnaryOperators---");

        printUnaryOperator(StringUnaryOperators.of(Function.identity()), "of identity function");
        printUnaryOperator(StringUnaryOperators.concat(StringUnaryOperators.trimToEmpty(), StringUnaryOperators.lowerCase(Locale.ENGLISH)), "concat 2");
        printUnaryOperator(StringUnaryOperators.concat(StringUnaryOperators.trimToEmpty(), StringUnaryOperators.lowerCase(Locale.ENGLISH), StringUnaryOperators.prefix("Prefix: ")), "concat 3");
        printUnaryOperator(StringUnaryOperators.conditionalOperator(Objects::nonNull, StringUnaryOperators.strip(), (s) -> "***NULL***"), "conditional");
        printUnaryOperator(StringUnaryOperators.identity(), "identity");
        printUnaryOperator(StringUnaryOperators.constant("constant"), "constant");
        printUnaryOperator(StringUnaryOperators.duplicate(), "duplicate");
        printUnaryOperator(StringUnaryOperators.reverse(), "reverse");
        printUnaryOperator(StringUnaryOperators.toNull(), "toNull");
        printUnaryOperator(StringUnaryOperators.toEmpty(), "toEmpty");
        printUnaryOperator(StringUnaryOperators.emptyToNull(), "emptyToNull");
        printUnaryOperator(StringUnaryOperators.nullToEmpty(), "nullToEmpty");
        printUnaryOperator(StringUnaryOperators.trimToNull(), "trimToNull");
        printUnaryOperator(StringUnaryOperators.trimToEmpty(), "trimToEmpty");
        printUnaryOperator(StringUnaryOperators.strip(), "strip");
        printUnaryOperator(StringUnaryOperators.stripIndent(), "stripIndent");
        printUnaryOperator(StringUnaryOperators.stripTrailing(), "stripTrailing");
        printUnaryOperator(StringUnaryOperators.stripLeading(), "stripLeading");
        printUnaryOperator(StringUnaryOperators.translateEscapes(), "translateEscapes");
        printUnaryOperator(StringUnaryOperators.removeHorizontalWhitespaces(), "removeHorizontalWhitespaces");
        printUnaryOperator(StringUnaryOperators.removeWhitespaces(), "removeWhitespaces");
        printUnaryOperator(StringUnaryOperators.removeVerticalWhitespaces(), "removeVerticalWhitespaces");
        printUnaryOperator(StringUnaryOperators.removeLeadingHorizontalWhitespaces(), "removeLeadingHorizontalWhitespaces");
        printUnaryOperator(StringUnaryOperators.removeLeadingWhitespaces(), "removeLeadingWhitespaces");
        printUnaryOperator(StringUnaryOperators.removeLeadingVerticalWhitespaces(), "removeLeadingVerticalWhitespaces");
        printUnaryOperator(StringUnaryOperators.removeTrailingHorizontalWhitespaces(), "removeTrailingHorizontalWhitespaces");
        printUnaryOperator(StringUnaryOperators.removeTrailingWhitespaces(), "removeTrailingWhitespaces");
        printUnaryOperator(StringUnaryOperators.removeTrailingVerticalWhitespaces(), "removeTrailingVerticalWhitespaces");
        printUnaryOperator(StringUnaryOperators.tabToSpace(), "tabToSpace");
        printUnaryOperator(StringUnaryOperators.tabToSpaces2(), "tabToSpaces2");
        printUnaryOperator(StringUnaryOperators.tabToSpaces4(), "tabToSpaces4");
        printUnaryOperator(StringUnaryOperators.tabToSpaces8(), "tabToSpaces8");
        printUnaryOperator(StringUnaryOperators.normalizeNFD(), "normalizeNFD");
        printUnaryOperator(StringUnaryOperators.normalizeNFC(), "normalizeNFC");
        printUnaryOperator(StringUnaryOperators.normalizeNFKD(), "normalizeNFKD");
        printUnaryOperator(StringUnaryOperators.normalizeNFKC(), "normalizeNFKC");
        printUnaryOperator(StringUnaryOperators.lengthAsString(), "lengthAsString");

        printUnaryOperator(StringUnaryOperators.lowerCase(Locale.GERMAN), "lowerCase");
        printUnaryOperator(StringUnaryOperators.upperCase(Locale.GERMAN), "upperCase");
        printUnaryOperator(StringUnaryOperators.repeat(2), "repeat 2");
        printUnaryOperator(StringUnaryOperators.replaceAll(StringUnaryOperators.REGEX_WHITESPACE, "_"), "replaceAll");
        printUnaryOperator(StringUnaryOperators.replaceFirst(StringUnaryOperators.REGEX_BACKSLASH, "/"), "replaceFirst");
        printUnaryOperator(StringUnaryOperators.removeAll(StringUnaryOperators.REGEX_WHITESPACE), "removeAll");
        printUnaryOperator(StringUnaryOperators.removeFirst(StringUnaryOperators.REGEX_BACKSLASH), "removeFirst");
        printUnaryOperator(StringUnaryOperators.indent(-2), "indent -2");
        printUnaryOperator(StringUnaryOperators.indent(0), "indent 0");
        printUnaryOperator(StringUnaryOperators.indent(2), "indent 2");
        printUnaryOperator(StringUnaryOperators.prefix("<<"), "prefix");
        printUnaryOperator(StringUnaryOperators.postfix(">>"), "postfix");
        printUnaryOperator(StringUnaryOperators.surround("<<", ">>"), "surround");
        printUnaryOperator(StringUnaryOperators.supplier(() -> "supplier"), "supplier");
        printUnaryOperator(StringUnaryOperators.codePointAt(0, "*"), "codePointAt 0");
        printUnaryOperator(StringUnaryOperators.codePointAt(4, "*"), "codePointAt 4");
        printUnaryOperator(StringUnaryOperators.codePointAt(5, new SequenceStringSupplier(0L)), "codePointAt 5 SequenceStringSupplier");
        printUnaryOperator(StringUnaryOperators.charAt(0, "*"), "charAt 0");
        printUnaryOperator(StringUnaryOperators.charAt(4, "*"), "charAt 4");
        printUnaryOperator(StringUnaryOperators.charAt(5, new SequenceStringSupplier(0L)), "charAt 5 SequenceStringSupplier");
        printUnaryOperator(StringUnaryOperators.format(Locale.ENGLISH, "*", 1), "format with Locale");
        printUnaryOperator(StringUnaryOperators.format(null, "*", 2), "format without Locale");
        printUnaryOperator(StringUnaryOperators.substring(2), "substring 2");
        printUnaryOperator(StringUnaryOperators.substring(2, 4), "substring 2 4");
        printUnaryOperator(StringUnaryOperators.removeFromStart(2), "removeFromStart 2");
        printUnaryOperator(StringUnaryOperators.removeFromEnd(2), "removeFromEnd 2");
        printUnaryOperator(StringUnaryOperators.keepFromStart(2), "keepFromStart 2");
        printUnaryOperator(StringUnaryOperators.keepFromEnd(2), "keepFromEnd 2");
        printUnaryOperator(StringUnaryOperators.padStart("<->", 5), "padStart 5");
        printUnaryOperator(StringUnaryOperators.padEnd("<->", 5), "padEnd 5");
    }

    public static void main(String... args) {
        showStringCheckType();
        showStringComparisonType();
        showStringUnaryOperators();
    }

}
