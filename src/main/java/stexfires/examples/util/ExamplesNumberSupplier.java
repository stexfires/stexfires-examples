package stexfires.examples.util;

import stexfires.util.supplier.RandomNumberSupplier;
import stexfires.util.supplier.SequenceLongSupplier;
import stexfires.util.supplier.SequencePrimitiveLongSupplier;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesNumberSupplier {

    private ExamplesNumberSupplier() {
    }

    private static void printStream(String title, Stream<Number> stream) {
        System.out.println(title);
        stream.limit(10L).forEachOrdered(System.out::println);
    }

    private static void printStream(String title, IntStream stream) {
        System.out.println(title);
        stream.limit(10L).forEachOrdered(System.out::println);
    }

    private static void printStream(String title, LongStream stream) {
        System.out.println(title);
        stream.limit(10L).forEachOrdered(System.out::println);
    }

    private static void printStream(String title, DoubleStream stream) {
        System.out.println(title);
        stream.limit(10L).forEachOrdered(System.out::println);
    }

    private static void showSequenceLongSupplier() {
        System.out.println("-showSequenceLongSupplier---");

        printStream("constructor 1.000",
                Stream.generate(
                        new SequenceLongSupplier(1_000L)));
        printStream("constructor -10",
                Stream.generate(
                        new SequenceLongSupplier(-10L)));
        printStream("constructor -1",
                Stream.generate(
                        new SequenceLongSupplier(-1L)));
    }

    private static void showSequencePrimitiveLongSupplier() {
        System.out.println("-showSequencePrimitiveLongSupplier---");

        printStream("constructor 1.000",
                LongStream.generate(
                        new SequencePrimitiveLongSupplier(1_000L)));
        printStream("constructor -10",
                LongStream.generate(
                        new SequencePrimitiveLongSupplier(-10L)));
        printStream("constructor -1",
                LongStream.generate(
                        new SequencePrimitiveLongSupplier(-1L)));
    }

    private static void showRandomSupplier() {
        System.out.println("-showRandomSupplier---");

        Random random = new Random();

        printStream("randomPrimitiveInteger",
                IntStream.generate(
                        RandomNumberSupplier.randomPrimitiveInteger(random, 10, 100)));
        printStream("randomInteger",
                Stream.generate(
                        RandomNumberSupplier.randomInteger(random, Integer.MIN_VALUE, Integer.MAX_VALUE)));

        printStream("randomPrimitiveLong",
                LongStream.generate(
                        RandomNumberSupplier.randomPrimitiveLong(random, 10, 100)));
        printStream("randomLong",
                Stream.generate(
                        RandomNumberSupplier.randomLong(random, Long.MIN_VALUE, Long.MAX_VALUE)));

        printStream("randomPrimitiveDouble",
                DoubleStream.generate(
                        RandomNumberSupplier.randomPrimitiveDouble(random, 10.0d, 100.0d)));
        printStream("randomDouble",
                Stream.generate(
                        RandomNumberSupplier.randomDouble(random, Double.MIN_VALUE, Double.MAX_VALUE)));

        printStream("randomFloat",
                Stream.generate(
                        RandomNumberSupplier.randomFloat(random, Float.MIN_VALUE, Float.MAX_VALUE)));
        printStream("randomBigInteger",
                Stream.generate(
                        RandomNumberSupplier.randomBigInteger(random, Long.MIN_VALUE, Long.MAX_VALUE)));
        printStream("randomBigDecimal",
                Stream.generate(
                        RandomNumberSupplier.randomBigDecimal(random, Double.MIN_VALUE, Double.MAX_VALUE)));

        printStream("randomPrimitiveDoubleGaussian",
                DoubleStream.generate(
                        RandomNumberSupplier.randomPrimitiveDoubleGaussian(random, 50.0d, 10.0d)));
        printStream("randomDoubleGaussian",
                Stream.generate(
                        RandomNumberSupplier.randomDoubleGaussian(random, 50.0d, 10.0d)));

    }

    private static void showRandomSelectionSupplier() {
        System.out.println("-showRandomSelectionSupplier---");

        Random random = new Random();

        printStream("primitiveIntegerSelection Array",
                IntStream.generate(
                        RandomNumberSupplier.primitiveIntegerSelection(random, 42, 23, 1024)));
        printStream("integerSelection List",
                Stream.generate(
                        RandomNumberSupplier.integerSelection(random, List.of(42, 23, 1024))));
        printStream("integerSelection Array",
                Stream.generate(
                        RandomNumberSupplier.integerSelection(random, 42, 23, 1024)));

        printStream("primitiveLongSelection Array",
                LongStream.generate(
                        RandomNumberSupplier.primitiveLongSelection(random, 42L, 23L, 1024L)));
        printStream("longSelection List",
                Stream.generate(
                        RandomNumberSupplier.longSelection(random, List.of(42L, 23L, 1024L))));
        printStream("longSelection Array",
                Stream.generate(
                        RandomNumberSupplier.longSelection(random, 42L, 23L, 1024L)));

        printStream("primitiveDoubleSelection Array",
                DoubleStream.generate(
                        RandomNumberSupplier.primitiveDoubleSelection(random, 42.0d, 23.0d, 1024.0d)));
        printStream("doubleSelection List",
                Stream.generate(
                        RandomNumberSupplier.doubleSelection(random, List.of(42.0d, 23.0d, 1024.0d))));
        printStream("doubleSelection Array",
                Stream.generate(
                        RandomNumberSupplier.doubleSelection(random, 42.0d, 23.0d, 1024.0d)));

        printStream("floatSelection List",
                Stream.generate(
                        RandomNumberSupplier.floatSelection(random, List.of(42.0f, 23.0f, 1024.0f))));
        printStream("floatSelection Array",
                Stream.generate(
                        RandomNumberSupplier.floatSelection(random, 42.0f, 23.0f, 1024.0f)));

        printStream("bigIntegerSelection List",
                Stream.generate(
                        RandomNumberSupplier.bigIntegerSelection(random, List.of(BigInteger.valueOf(42L), BigInteger.valueOf(23L), BigInteger.valueOf(1024L)))));
        printStream("bigIntegerSelection Array",
                Stream.generate(
                        RandomNumberSupplier.bigIntegerSelection(random, BigInteger.valueOf(42L), BigInteger.valueOf(23L), BigInteger.valueOf(1024L))));

        printStream("bigDecimalSelection List",
                Stream.generate(
                        RandomNumberSupplier.bigDecimalSelection(random, List.of(BigDecimal.valueOf(42.0d), BigDecimal.valueOf(23.0d), BigDecimal.valueOf(1024.0d)))));
        printStream("bigDecimalSelection Array",
                Stream.generate(
                        RandomNumberSupplier.bigDecimalSelection(random, BigDecimal.valueOf(42.0d), BigDecimal.valueOf(23.0d), BigDecimal.valueOf(1024.0d))));

    }

    public static void main(String... args) {
        showSequenceLongSupplier();
        showSequencePrimitiveLongSupplier();
        showRandomSupplier();
        showRandomSelectionSupplier();
    }

}
