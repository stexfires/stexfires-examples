package stexfires.examples.util;

import stexfires.util.Strings;

import java.util.stream.Stream;

public final class ExamplesStrings {

    private ExamplesStrings() {
    }

    private static void showStringsList() {
        System.out.println("-showStringsList---");

        System.out.println(Strings.list("a"));
        System.out.println(Strings.list("a", "b"));
        System.out.println(Strings.list("a", "b", "c"));
        System.out.println(Strings.list());
        System.out.println(Strings.list(""));
        System.out.println(Strings.list((String) null));
        System.out.println(Strings.list(null, null));
        System.out.println(Strings.list(new String[]{"a"}));
    }

    private static void showStringsStream() {
        System.out.println("-showStringsStream---");

        System.out.println(Strings.collect(Strings.stream("a")));
        System.out.println(Strings.collect(Strings.stream("a", "b")));
        System.out.println(Strings.collect(Strings.stream("a", "b", "c")));
        System.out.println(Strings.collect(Strings.stream()));
        System.out.println(Strings.collect(Strings.stream("")));
        System.out.println(Strings.collect(Strings.stream((String) null)));
        System.out.println(Strings.collect(Strings.stream(null, null)));
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

        System.out.println(Strings.collect(Strings.concat(Stream.of("a"))));
        System.out.println(Strings.collect(Strings.concat(Stream.of("a"), Stream.of("b"))));
        System.out.println(Strings.collect(Strings.concat(Stream.of("a", "b"), Stream.of("c", "d"))));

    }

    private static void showStringsCollect() {
        System.out.println("-showStringsCollect---");

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
    }

    private static void showStringsPrintLine() {
        System.out.println("-showStringsPrintLine---");

        Strings.printLine(Strings.stream("a", "b"), ", ");
    }

    private static void showStringsPrintLines() {
        System.out.println("-showStringsPrintLines---");

        Strings.printLines(Strings.stream("a", "b"));
    }

    public static void main(String[] args) {
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
