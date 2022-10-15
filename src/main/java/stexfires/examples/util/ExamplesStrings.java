package stexfires.examples.util;

import stexfires.util.Strings;

import java.math.BigInteger;
import java.util.Locale;
import java.util.stream.Stream;

@SuppressWarnings({"UseOfSystemOutOrSystemErr", "HardcodedLineSeparator"})
public final class ExamplesStrings {

    private ExamplesStrings() {
    }

    private static void showStringsMisc() {
        System.out.println("-showStringsMisc---");

        Integer ten = 10;

        System.out.println(Strings.asString(null));
        System.out.println(Strings.asString("Test"));
        System.out.println(Strings.asString(Boolean.TRUE));
        System.out.println(Strings.asString(BigInteger.TWO));
        System.out.println(Strings.asString(ten));

        System.out.println(Strings.asOptionalString(null));
        System.out.println(Strings.asOptionalString("Test"));
        System.out.println(Strings.asOptionalString(Boolean.TRUE));
        System.out.println(Strings.asOptionalString(BigInteger.TWO));
        System.out.println(Strings.asOptionalString(ten));

        System.out.println(Strings.empty());
    }

    private static void showStringsList() {
        System.out.println("-showStringsList---");

        System.out.println(Strings.list("a"));
        System.out.println(Strings.list("a", "b"));
        System.out.println(Strings.list("a", "b", "c"));
        System.out.println(Strings.list("a", null, "c"));
        System.out.println(Strings.list());
        System.out.println(Strings.list(""));
        System.out.println(Strings.list((String) null));
        System.out.println(Strings.list(null, null));
        System.out.println(Strings.list(null, null, "Test"));
        System.out.println(Strings.list(new String[]{"a"}));
    }

    private static void showStringsListOfNullable() {
        System.out.println("-showStringsListOfNullable---");

        System.out.println(Strings.listOfNullable("a"));
        System.out.println(Strings.listOfNullable(""));
        System.out.println(Strings.listOfNullable(null));
    }

    private static void showStringsStream() {
        System.out.println("-showStringsStream---");

        System.out.println(Strings.collect(Strings.stream("a")));
        System.out.println(Strings.collect(Strings.stream("a", "b")));
        System.out.println(Strings.collect(Strings.stream("a", "b", "c")));
        System.out.println(Strings.collect(Strings.stream("a", null, "c")));
        System.out.println(Strings.collect(Strings.stream()));
        System.out.println(Strings.collect(Strings.stream("")));
        System.out.println(Strings.collect(Strings.stream((String) null)));
        System.out.println(Strings.collect(Strings.stream(null, null)));
        System.out.println(Strings.collect(Strings.stream(null, null, "Test")));
        System.out.println(Strings.collect(Strings.stream(new String[]{"a"})));
    }

    private static void showStringsStreamOfNullable() {
        System.out.println("-showStringsStreamOfNullable---");

        System.out.println(Strings.collect(Strings.streamOfNullable("a")));
        System.out.println(Strings.collect(Strings.streamOfNullable("")));
        System.out.println(Strings.collect(Strings.streamOfNullable(null)));
    }

    private static void showStringsConcat() {
        System.out.println("-showStringsConcat---");

        System.out.println(Strings.collect(Strings.concat(Stream.empty())));
        System.out.println(Strings.collect(Strings.concat()));
        System.out.println(Strings.collect(Strings.concat(Stream.of("a"))));
        System.out.println(Strings.collect(Strings.concat(Stream.of("a"), Stream.of("b"))));
        System.out.println(Strings.collect(Strings.concat(Stream.of("a", "b"), Stream.of("c", "d"))));
        System.out.println(Strings.collect(Strings.concat(Stream.of("a", "b"), Stream.empty(), Stream.of("c", "d"))));
    }

    private static void showStringsCollect() {
        System.out.println("-showStringsCollect---");

        System.out.println(Strings.collect(Stream.empty()));
        System.out.println(Strings.collect(Strings.stream("a")));
        System.out.println(Strings.collect(Strings.stream("a", "b")));
        System.out.println(Strings.collect(Strings.stream("a", "b", "c")));
        System.out.println(Strings.collect(Strings.stream("a", null, "c")));
        System.out.println(Strings.collect(Strings.stream()));
        System.out.println(Strings.collect(Strings.stream("")));
        System.out.println(Strings.collect(Strings.stream((String) null)));
        System.out.println(Strings.collect(Strings.stream(null, null)));
        System.out.println(Strings.collect(Strings.stream(null, null, "Test")));
        System.out.println(Strings.collect(Strings.stream(new String[]{"a"})));
    }

    private static void showStringsToList() {
        System.out.println("-showStringsToList---");

        System.out.println(Strings.toList(Stream.empty()));
        System.out.println(Strings.toList(Strings.stream("a")));
        System.out.println(Strings.toList(Strings.stream("a", "b")));
        System.out.println(Strings.toList(Strings.stream("a", "b", "c")));
        System.out.println(Strings.toList(Strings.stream("a", null, "c")));
        System.out.println(Strings.toList(Strings.stream()));
        System.out.println(Strings.toList(Strings.stream("")));
        System.out.println(Strings.toList(Strings.stream((String) null)));
        System.out.println(Strings.toList(Strings.stream(null, null)));
        System.out.println(Strings.toList(Strings.stream(null, null, "Test")));
        System.out.println(Strings.toList(Strings.stream(new String[]{"a"})));
    }

    private static void showStringsJoin() {
        System.out.println("-showStringsJoin---");

        System.out.println(Strings.join(Stream.empty()));
        System.out.println(Strings.join(Strings.stream("a")));
        System.out.println(Strings.join(Strings.stream("a", "b")));
        System.out.println(Strings.join(Strings.stream("a", "b", "c")));
        System.out.println(Strings.join(Strings.stream("a", null, "c")));
        System.out.println(Strings.join(Strings.stream()));
        System.out.println(Strings.join(Strings.stream("")));
        System.out.println(Strings.join(Strings.stream((String) null)));
        System.out.println(Strings.join(Strings.stream(null, null)));
        System.out.println(Strings.join(Strings.stream(null, null, "Test")));
        System.out.println(Strings.join(Strings.stream(new String[]{"a"})));

        System.out.println(Strings.join(Strings.stream("a", "b", "c"), ""));
        System.out.println(Strings.join(Strings.stream("a", "b", "c"), Strings.DEFAULT_DELIMITER));
        System.out.println(Strings.join(Strings.stream("a", "b", "c"), "-"));
    }

    private static void showStringsPrintLine() {
        System.out.println("-showStringsPrintLine---");

        Strings.printLine(Stream.empty(), ", ");
        Strings.printLine(Strings.stream("a", "b", "c"), ", ");
        Strings.printLine(Strings.stream("a", null, "c"), ", ");
    }

    private static void showStringsPrintLines() {
        System.out.println("-showStringsPrintLines---");

        Strings.printLines(Stream.empty());
        Strings.printLines(Strings.stream("a", "b", "c"));
        Strings.printLines(Strings.stream("a", null, "c"));
    }

    private static void showStringsSplit() {
        System.out.println("-showStringsSplit---");

        System.out.println(Strings.collect(Strings.splitTextByLines("First line\nSecond Line\nThird line")));
        System.out.println(Strings.collect(Strings.splitTextByChars("ABC\uD83D\uDE00o\u0308A\u030a")));
        System.out.println(Strings.collect(Strings.splitTextByCodePoints("ABC\uD83D\uDE00o\u0308A\u030a")));

        System.out.println(Strings.collect(Strings.splitTextByRegex("ABC-def--GHI-", "-", -1)));
        System.out.println(Strings.collect(Strings.splitTextByRegex("ABC-def--GHI-", "-", 0)));
        System.out.println(Strings.collect(Strings.splitTextByRegex("ABC-def--GHI-", "-", 2)));

        System.out.println(Strings.collect(Strings.splitTextByLength("abcdefghi", 3)));
        System.out.println(Strings.collect(Strings.splitTextByLength("abcdefghij", 3)));
        System.out.println(Strings.collect(Strings.splitTextByLength("abcdefghijk", 3)));
    }

    private static void showStringsBreak() {
        System.out.println("-showStringsBreak---");

        String text = "Hello world! This is a sentence. Is this also a sentence? He said: \"Hello world\".\nSpecial characters: \uD83D\uDE00, o\u0308, A\u030a.";

        System.out.println(Strings.collect(Strings.breakTextBySentence(text, Locale.ENGLISH)));
        System.out.println(Strings.collect(Strings.breakTextByWord(text, Locale.ENGLISH)));
        System.out.println(Strings.collect(Strings.breakTextByLine(text, Locale.ENGLISH)));
        System.out.println(Strings.collect(Strings.breakTextByCharacter(text, Locale.ENGLISH)));
    }

    public static void main(String... args) {
        showStringsMisc();
        showStringsList();
        showStringsListOfNullable();
        showStringsStream();
        showStringsStreamOfNullable();
        showStringsConcat();
        showStringsCollect();
        showStringsToList();
        showStringsJoin();
        showStringsPrintLine();
        showStringsPrintLines();
        showStringsSplit();
        showStringsBreak();
    }

}
