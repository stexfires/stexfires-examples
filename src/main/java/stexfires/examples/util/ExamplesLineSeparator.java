package stexfires.examples.util;

import stexfires.util.LineSeparator;

import java.util.stream.Collectors;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public final class ExamplesLineSeparator {

    private ExamplesLineSeparator() {
    }

    private static void showLineSeparator() {
        System.out.println("-showLineSeparator---");

        for (LineSeparator type : LineSeparator.values()) {
            System.out.println(type.name() + " (string())        ? " + type.string());
            System.out.println(type.name() + " (supplier() get)  ? " + type.supplier().get());
            System.out.println(type.name() + " (stream() toList) ? " + type.stream().collect(Collectors.toList()));
            System.out.println(type.name() + " (chars() toList)  ? " + type.chars().boxed().collect(Collectors.toList()));
            System.out.println(type.name() + " (name())          ? " + type.name());
            System.out.println(type.name() + " (toString())      ? " + type.toString());
        }
    }

    public static void main(String[] args) {
        showLineSeparator();
    }

}
