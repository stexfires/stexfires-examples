package stexfires.examples.util;

import stexfires.util.supplier.SequenceLongSupplier;
import stexfires.util.supplier.SequencePrimitiveLongSupplier;

import java.util.stream.LongStream;
import java.util.stream.Stream;

@SuppressWarnings("MagicNumber")
public final class ExamplesLongSupplier {

    private ExamplesLongSupplier() {
    }

    private static void printStream(String title, Stream<Long> stream) {
        System.out.println(title);
        stream.limit(10L).forEachOrdered(System.out::println);
    }

    private static void printStream(String title, LongStream stream) {
        System.out.println(title);
        stream.limit(10L).forEachOrdered(System.out::println);
    }

    private static void showSequenceLongSupplier() {
        System.out.println("-showSequenceLongSupplier---");

        printStream("constructor 1000",
                Stream.generate(
                        new SequenceLongSupplier(1000L)));
    }

    private static void showSequencePrimitiveLongSupplier() {
        System.out.println("-showSequencePrimitiveLongSupplier---");

        printStream("constructor 1000",
                LongStream.generate(
                        new SequencePrimitiveLongSupplier(1000L)));
    }

    public static void main(String[] args) {
        showSequenceLongSupplier();
        showSequencePrimitiveLongSupplier();
    }

}