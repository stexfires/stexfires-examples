package stexfires.examples.util;

import stexfires.util.supplier.LocalTimeStringSupplier;
import stexfires.util.supplier.SequenceStringSupplier;
import stexfires.util.supplier.ThreadNameStringSupplier;

import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesStringSupplier {

    private ExamplesStringSupplier() {
    }

    private static void printStream(String title, Stream<String> stream) {
        System.out.println(title);
        stream.limit(10L).forEachOrdered(System.out::println);
    }

    private static void showLocalTimeStringSupplier() {
        System.out.println("-showLocalTimeStringSupplier---");

        printStream("constructor",
                Stream.generate(
                        new LocalTimeStringSupplier()));
    }

    private static void showSequenceStringSupplier() {
        System.out.println("-showSequenceStringSupplier---");

        printStream("constructor 1.000",
                Stream.generate(
                        new SequenceStringSupplier(1_000L)));
    }

    private static void showThreadNameStringSupplier() {
        System.out.println("-showThreadNameStringSupplier---");

        printStream("constructor",
                Stream.generate(
                        new ThreadNameStringSupplier()));
    }

    public static void main(String[] args) {
        showLocalTimeStringSupplier();
        showSequenceStringSupplier();
        showThreadNameStringSupplier();
    }

}
