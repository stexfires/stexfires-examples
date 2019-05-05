package stexfires.examples.util;

import stexfires.util.Strings;

import java.math.BigInteger;
import java.util.stream.Stream;

public final class ExamplesStrings {

    private ExamplesStrings() {
    }

    @SuppressWarnings("UnnecessaryBoxing")
    private static void showStringsMisc() {
        System.out.println("-showStringsMisc---");

        System.out.println(Strings.asString(null));
        System.out.println(Strings.asString("Test"));
        System.out.println(Strings.asString(Boolean.TRUE));
        System.out.println(Strings.asString(BigInteger.TWO));
        System.out.println(Strings.asString(Integer.valueOf(10)));

        System.out.println(Strings.asOptionalString(null));
        System.out.println(Strings.asOptionalString("Test"));
        System.out.println(Strings.asOptionalString(Boolean.TRUE));
        System.out.println(Strings.asOptionalString(BigInteger.TWO));
        System.out.println(Strings.asOptionalString(Integer.valueOf(10)));

        System.out.println(Strings.empty());
    }

    @SuppressWarnings("RedundantArrayCreation")
    private static void showStringsList() {
        System.out.println("-showStringsList---");

        System.out.println(Strings.list("a"));
        System.out.println(Strings.list("a", "b"));
        System.out.println(Strings.list("a", "b", "c"));
        System.out.println(Strings.list());
        System.out.println(Strings.list(""));
        System.out.println(Strings.list((String) null));
        System.out.println(Strings.list(null, null));
        System.out.println(Strings.list(null, null, "Test"));
        System.out.println(Strings.list(new String[]{"a"}));
        System.out.println(Strings.list(new String[]{"a", "b"}));
    }

    @SuppressWarnings("RedundantArrayCreation")
    private static void showStringsStream() {
        System.out.println("-showStringsStream---");

        System.out.println(Strings.collect(Strings.stream("a")));
        System.out.println(Strings.collect(Strings.stream("a", "b")));
        System.out.println(Strings.collect(Strings.stream("a", "b", "c")));
        System.out.println(Strings.collect(Strings.stream()));
        System.out.println(Strings.collect(Strings.stream("")));
        System.out.println(Strings.collect(Strings.stream((String) null)));
        System.out.println(Strings.collect(Strings.stream(null, null)));
        System.out.println(Strings.collect(Strings.stream(null, null, "Test")));
        System.out.println(Strings.collect(Strings.stream(new String[]{"a"})));
        System.out.println(Strings.collect(Strings.stream(new String[]{"a", "b"})));
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
    }

    private static void showStringsCollect() {
        System.out.println("-showStringsCollect---");

        System.out.println(Strings.collect(Stream.empty()));
        System.out.println(Strings.collect(Strings.stream("a")));
        System.out.println(Strings.collect(Strings.stream("a", "b")));
        System.out.println(Strings.collect(Strings.stream("a", "b", "c")));
        System.out.println(Strings.collect(Strings.stream()));
        System.out.println(Strings.collect(Strings.stream("")));
        System.out.println(Strings.collect(Strings.stream((String) null)));
        System.out.println(Strings.collect(Strings.stream(null, null)));
        System.out.println(Strings.collect(Strings.stream(new String[]{"a"})));
    }

    private static void showStringsJoin() {
        System.out.println("-showStringsJoin---");

        System.out.println(Strings.join(Strings.stream("a")));
        System.out.println(Strings.join(Strings.stream("a", "b")));
        System.out.println(Strings.join(Strings.stream("a", "b", "c")));
        System.out.println(Strings.join(Strings.stream()));
        System.out.println(Strings.join(Strings.stream("")));
        System.out.println(Strings.join(Strings.stream((String) null)));
        System.out.println(Strings.join(Strings.stream(null, null)));
        System.out.println(Strings.join(Strings.stream(new String[]{"a"})));

        System.out.println(Strings.join(Strings.stream("a", "b", "c"), ""));
        System.out.println(Strings.join(Strings.stream("a", "b", "c"), Strings.DEFAULT_DELIMITER));
        System.out.println(Strings.join(Strings.stream("a", "b", "c"), "-"));
    }

    private static void showStringsPrintLine() {
        System.out.println("-showStringsPrintLine---");

        Strings.printLine(Strings.stream("a", "b", "c"), ", ");
    }

    private static void showStringsPrintLines() {
        System.out.println("-showStringsPrintLines---");

        Strings.printLines(Strings.stream("a", "b", "c"));
    }

    public static void main(String[] args) {
        showStringsMisc();
        showStringsList();
        showStringsStream();
        showStringsStreamOfNullable();
        showStringsConcat();
        showStringsCollect();
        showStringsJoin();
        showStringsPrintLine();
        showStringsPrintLines();
    }

}
