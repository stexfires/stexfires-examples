package stexfires.examples.util;

import stexfires.util.Strings;
import stexfires.util.function.StringPredicates;
import stexfires.util.function.StringUnaryOperators;
import stexfires.util.function.Suppliers;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "HardcodedLineSeparator", "SpellCheckingInspection", "MagicNumber"})
public final class ExampleStringFunction {

    @SuppressWarnings("StaticCollection")
    private static final List<String> VALUES;

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
    }

    private ExampleStringFunction() {
    }

    private static String generateLengthString(String value) {
        int length = value.length();
        String lengthString;
        var codePointLength = value.codePoints().count();
        if (length != codePointLength) {
            lengthString = "[" + length + "; " + codePointLength + "]";
        } else {
            lengthString = "[" + length + "]";
        }
        return lengthString;
    }

    private static String printValue(String value) {
        if (value == null) {
            return "(<null>) \t [-] \t ";
        }
        return "('" + value + "') \t " + generateLengthString(value) + " \t ";
    }

    private static String printResult(String value, String result) {
        String eq = Objects.equals(value, result) ? "==" : "!=";
        if (result == null) {
            return eq + " (<null>) \t [-]";
        }
        return eq + " ('" + result + "') \t " + generateLengthString(result) + " \t hex: "
                + String.format("%04x", new BigInteger(1, result.getBytes(StandardCharsets.UTF_16BE)));
    }

    private static void printStringPredicates(Predicate<String> predicate, String name) {
        System.out.println("---- " + name);
        for (String value : VALUES) {
            String result;
            try {
                result = String.valueOf(predicate.test(value));
            } catch (IllegalArgumentException | NullPointerException e) {
                result = e.toString();
            }
            System.out.println(printValue(value)
                    + "? " + result);
        }
        System.out.println("-------------------------------------------------");
    }

    private static void printUnaryOperator(UnaryOperator<String> operator, String name) {
        System.out.println("---- " + name);
        for (String value : VALUES) {
            String result;
            try {
                result = operator.apply(value);
            } catch (IllegalArgumentException | NullPointerException e) {
                result = e.toString();
            }
            System.out.println(printValue(value)
                    + printResult(value, result));
        }
        System.out.println("-------------------------------------------------");
    }

    private static void showStringPredicates() {
        System.out.println("-showStringPredicates---");

        printStringPredicates(StringPredicates.applyOperatorAndTest(StringUnaryOperators.trimToEmpty(), StringPredicates.isEmpty()), "applyOperatorAndTest");
        printStringPredicates(StringPredicates.applyFunctionAndTest(String::length, length -> length > 2 && length < 6), "applyFunctionAndTest");

        printStringPredicates(StringPredicates.concatAnd(StringPredicates.isNullOrEmpty(), StringPredicates.isNullOrBlank()), "concatAnd");
        printStringPredicates(StringPredicates.concatOr(StringPredicates.isEmpty(), StringPredicates.isBlank()), "concatOr");

        printStringPredicates(StringPredicates.isNullOr(StringPredicates.isBlank()), "isNullOr");
        printStringPredicates(StringPredicates.isNotNullAnd(StringPredicates.isBlank()), "isNotNullAnd");

        printStringPredicates(StringPredicates.allLinesMatch(StringPredicates.letterOrDigit(), false), "allLinesMatch letterOrDigit");
        printStringPredicates(StringPredicates.anyLineMatch(StringPredicates.letterOrDigit(), false), "anyLineMatch letterOrDigit");
        printStringPredicates(StringPredicates.noneLineMatch(StringPredicates.isEmpty(), false), "noneLineMatch isEmpty");

        printStringPredicates(StringPredicates.allCodePointsMatch(StringPredicates.codePointBetween(64, 127), false), "allCodePointsMatch codePointBetween");
        printStringPredicates(StringPredicates.allCodePointsMatch(StringPredicates.codePointASCII(), false), "allCodePointsMatch codePointASCII");
        printStringPredicates(StringPredicates.allCodePointsMatch(StringPredicates.codePointCharacterType(Character.DECIMAL_DIGIT_NUMBER), false), "allCodePointsMatch codePointCharacterType");
        printStringPredicates(StringPredicates.allCodePointsMatch(StringPredicates.codePointUnicodeBlock(Character.UnicodeBlock.BASIC_LATIN), false), "allCodePointsMatch codePointUnicodeBlock");
        printStringPredicates(StringPredicates.allCodePointsMatch(StringPredicates.codePointCharCount1(), false), "allCodePointsMatch codePointCharCount1");
        printStringPredicates(StringPredicates.allCodePointsMatch(StringPredicates.codePointCharCount2(), false), "allCodePointsMatch codePointCharCount2");

        printStringPredicates(StringPredicates.allCodePointsMatch(i -> i >= 32 && i <= 127, false), "allCodePointsMatch 32-127");
        printStringPredicates(StringPredicates.allCodePointsMatch(Character::isValidCodePoint, false), "allCodePointsMatch Character::isValidCodePoint");
        printStringPredicates(StringPredicates.anyCodePointMatch(i -> i >= 32 && i <= 127, false), "anyCodePointMatch 32-127");
        printStringPredicates(StringPredicates.noneCodePointMatch(i -> i < 32, true), "noneCodePointMatch <32");

        printStringPredicates(StringPredicates.isNull(), "isNull");
        printStringPredicates(StringPredicates.isNotNull(), "isNotNull");
        printStringPredicates(StringPredicates.isEmpty(), "isEmpty");
        printStringPredicates(StringPredicates.isNullOrEmpty(), "isNullOrEmpty");
        printStringPredicates(StringPredicates.isBlank(), "isBlank");
        printStringPredicates(StringPredicates.isNullOrBlank(), "isNullOrBlank");

        printStringPredicates(StringPredicates.equals(null), "equals null");
        printStringPredicates(StringPredicates.equals(""), "equals empty");
        printStringPredicates(StringPredicates.equals(" "), "equals space");
        printStringPredicates(StringPredicates.equals("\uD83D\uDE00"), "equals \uD83D\uDE00");
        printStringPredicates(StringPredicates.equalsIgnoreCase(null), "equalsIgnoreCase null");
        printStringPredicates(StringPredicates.equalsIgnoreCase(""), "equalsIgnoreCase empty");
        printStringPredicates(StringPredicates.equalsIgnoreCase("ä"), "equalsIgnoreCase ä");
        printStringPredicates(StringPredicates.equalsChar('1'), "equalsChar '1'");
        printStringPredicates(StringPredicates.equalsCodePoint(32), "equalsCodePoint 32");
        printStringPredicates(StringPredicates.equalsFunction(s -> ""), "equalsFunction empty");
        printStringPredicates(StringPredicates.equalsOperator(s -> ""), "equalsOperator empty");
        printStringPredicates(StringPredicates.equalsSupplier(() -> ""), "equalsSupplier empty");

        printStringPredicates(StringPredicates.constantTrue(), "constantTrue");
        printStringPredicates(StringPredicates.constantFalse(), "constantFalse");
        printStringPredicates(StringPredicates.constant(true), "constant true");
        printStringPredicates(StringPredicates.supplier(() -> false), "supplier false");

        printStringPredicates(StringPredicates.alphabetic(), "alphabetic");
        printStringPredicates(StringPredicates.ascii(), "ascii");
        printStringPredicates(StringPredicates.digit(), "digit");
        printStringPredicates(StringPredicates.letter(), "letter");
        printStringPredicates(StringPredicates.letterOrDigit(), "letterOrDigit");
        printStringPredicates(StringPredicates.lowerCase(), "lowerCase");
        printStringPredicates(StringPredicates.upperCase(), "upperCase");
        printStringPredicates(StringPredicates.spaceChar(), "spaceChar");
        printStringPredicates(StringPredicates.whitespace(), "whitespace");

        printStringPredicates(StringPredicates.normalizedNFD(), "normalizedNFD");
        printStringPredicates(StringPredicates.normalizedNFC(), "normalizedNFC");
        printStringPredicates(StringPredicates.normalizedNFKD(), "normalizedNFKD");
        printStringPredicates(StringPredicates.normalizedNFKC(), "normalizedNFKC");

        printStringPredicates(StringPredicates.contains("1"), "contains 1");
        printStringPredicates(StringPredicates.startsWith("1"), "startsWith 1");
        printStringPredicates(StringPredicates.endsWith("3"), "endsWith 3");
        printStringPredicates(StringPredicates.surroundedBy("1", "3"), "surroundedBy");
        printStringPredicates(StringPredicates.containedIn(List.of(" ", "1", "ä")), "containedIn");
        printStringPredicates(StringPredicates.charAt(1, '1'), "charAt");
        printStringPredicates(StringPredicates.matches("[a-i]+"), "matches [a-i]+");
        printStringPredicates(StringPredicates.matches("\\p{Alnum}+"), "matches Alnum");
        printStringPredicates(StringPredicates.matches("\\p{Blank}+"), "matches Blank");

        printStringPredicates(StringPredicates.length(1), "length 1");
        printStringPredicates(StringPredicates.countCodePoints(1), "countCodePoints 1");
        printStringPredicates(StringPredicates.countLines(1), "countLines 1");
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
        printUnaryOperator(StringUnaryOperators.nullToConstant("***"), "nullToConstant ***");
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
        printUnaryOperator(StringUnaryOperators.replaceAll(Strings.REGEX_WHITESPACE, "_"), "replaceAll");
        printUnaryOperator(StringUnaryOperators.replaceFirst(Strings.REGEX_BACKSLASH, "/"), "replaceFirst");
        printUnaryOperator(StringUnaryOperators.removeAll(Strings.REGEX_WHITESPACE), "removeAll");
        printUnaryOperator(StringUnaryOperators.removeFirst(Strings.REGEX_BACKSLASH), "removeFirst");
        printUnaryOperator(StringUnaryOperators.indent(-2), "indent -2");
        printUnaryOperator(StringUnaryOperators.indent(0), "indent 0");
        printUnaryOperator(StringUnaryOperators.indent(2), "indent 2");
        printUnaryOperator(StringUnaryOperators.prefix("<<"), "prefix");
        printUnaryOperator(StringUnaryOperators.suffix(">>"), "suffix");
        printUnaryOperator(StringUnaryOperators.surround("<<", ">>"), "surround");
        printUnaryOperator(StringUnaryOperators.supplier(() -> "supplier"), "supplier");
        printUnaryOperator(StringUnaryOperators.codePointAt(0, "*"), "codePointAt 0");
        printUnaryOperator(StringUnaryOperators.codePointAt(4, "*"), "codePointAt 4");
        printUnaryOperator(StringUnaryOperators.codePointAt(5, Suppliers.sequenceAsString(0L)), "codePointAt 5 sequenceAsString");
        printUnaryOperator(StringUnaryOperators.charAt(0, "*"), "charAt 0");
        printUnaryOperator(StringUnaryOperators.charAt(4, "*"), "charAt 4");
        printUnaryOperator(StringUnaryOperators.charAt(5, Suppliers.sequenceAsString(0L)), "charAt 5 sequenceAsString");
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
        showStringPredicates();
        showStringUnaryOperators();
    }

}
