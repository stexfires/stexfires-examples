package stexfires.examples.util;

import stexfires.util.function.RandomNumberSuppliers;
import stexfires.util.function.Suppliers;

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

    private static void showSuppliers() {
        System.out.println("-showSuppliers---");

        printStream("sequenceAsLong 1.000",
                Stream.generate(
                        Suppliers.sequenceAsLong(1_000L)));
        printStream("sequenceAsLong -10",
                Stream.generate(
                        Suppliers.sequenceAsLong(-10L)));
        printStream("sequenceAsLong -1",
                Stream.generate(
                        Suppliers.sequenceAsLong(-1L)));

        printStream("sequenceAsPrimitiveLong 1.000",
                LongStream.generate(
                        Suppliers.sequenceAsPrimitiveLong(1_000L)));
        printStream("sequenceAsPrimitiveLong -10",
                LongStream.generate(
                        Suppliers.sequenceAsPrimitiveLong(-10L)));
        printStream("sequenceAsPrimitiveLong -1",
                LongStream.generate(
                        Suppliers.sequenceAsPrimitiveLong(-1L)));
    }

    private static void showRandomSupplier() {
        System.out.println("-showRandomSupplier---");

        Random random = new Random();

        printStream("randomPrimitiveInt",
                IntStream.generate(
                        RandomNumberSuppliers.randomPrimitiveInt(random, 10, 100)));
        printStream("randomInteger",
                Stream.generate(
                        RandomNumberSuppliers.randomInteger(random, Integer.MIN_VALUE, Integer.MAX_VALUE)));

        printStream("randomPrimitiveLong",
                LongStream.generate(
                        RandomNumberSuppliers.randomPrimitiveLong(random, 10, 100)));
        printStream("randomLong",
                Stream.generate(
                        RandomNumberSuppliers.randomLong(random, Long.MIN_VALUE, Long.MAX_VALUE)));

        printStream("randomPrimitiveDouble",
                DoubleStream.generate(
                        RandomNumberSuppliers.randomPrimitiveDouble(random, 10.0d, 100.0d)));
        printStream("randomDouble",
                Stream.generate(
                        RandomNumberSuppliers.randomDouble(random, Double.MIN_VALUE, Double.MAX_VALUE)));

        printStream("randomFloat",
                Stream.generate(
                        RandomNumberSuppliers.randomFloat(random, Float.MIN_VALUE, Float.MAX_VALUE)));
        printStream("randomBigInteger",
                Stream.generate(
                        RandomNumberSuppliers.randomBigInteger(random, Long.MIN_VALUE, Long.MAX_VALUE)));
        printStream("randomBigDecimal",
                Stream.generate(
                        RandomNumberSuppliers.randomBigDecimal(random, Double.MIN_VALUE, Double.MAX_VALUE)));

        printStream("randomPrimitiveDoubleGaussian",
                DoubleStream.generate(
                        RandomNumberSuppliers.randomPrimitiveDoubleGaussian(random, 50.0d, 10.0d)));
        printStream("randomDoubleGaussian",
                Stream.generate(
                        RandomNumberSuppliers.randomDoubleGaussian(random, 50.0d, 10.0d)));

    }

    private static void showRandomSelectionSupplier() {
        System.out.println("-showRandomSelectionSupplier---");

        Random random = new Random();

        printStream("primitiveIntSelection Array",
                IntStream.generate(
                        RandomNumberSuppliers.primitiveIntSelection(random, 42, 23, 1024)));
        printStream("integerSelection List",
                Stream.generate(
                        RandomNumberSuppliers.integerSelection(random, List.of(42, 23, 1024))));
        printStream("integerSelection Array",
                Stream.generate(
                        RandomNumberSuppliers.integerSelection(random, 42, 23, 1024)));

        printStream("primitiveLongSelection Array",
                LongStream.generate(
                        RandomNumberSuppliers.primitiveLongSelection(random, 42L, 23L, 1024L)));
        printStream("longSelection List",
                Stream.generate(
                        RandomNumberSuppliers.longSelection(random, List.of(42L, 23L, 1024L))));
        printStream("longSelection Array",
                Stream.generate(
                        RandomNumberSuppliers.longSelection(random, 42L, 23L, 1024L)));

        printStream("primitiveDoubleSelection Array",
                DoubleStream.generate(
                        RandomNumberSuppliers.primitiveDoubleSelection(random, 42.0d, 23.0d, 1024.0d)));
        printStream("doubleSelection List",
                Stream.generate(
                        RandomNumberSuppliers.doubleSelection(random, List.of(42.0d, 23.0d, 1024.0d))));
        printStream("doubleSelection Array",
                Stream.generate(
                        RandomNumberSuppliers.doubleSelection(random, 42.0d, 23.0d, 1024.0d)));

        printStream("floatSelection List",
                Stream.generate(
                        RandomNumberSuppliers.floatSelection(random, List.of(42.0f, 23.0f, 1024.0f))));
        printStream("floatSelection Array",
                Stream.generate(
                        RandomNumberSuppliers.floatSelection(random, 42.0f, 23.0f, 1024.0f)));

        printStream("bigIntegerSelection List",
                Stream.generate(
                        RandomNumberSuppliers.bigIntegerSelection(random, List.of(BigInteger.valueOf(42L), BigInteger.valueOf(23L), BigInteger.valueOf(1024L)))));
        printStream("bigIntegerSelection Array",
                Stream.generate(
                        RandomNumberSuppliers.bigIntegerSelection(random, BigInteger.valueOf(42L), BigInteger.valueOf(23L), BigInteger.valueOf(1024L))));

        printStream("bigDecimalSelection List",
                Stream.generate(
                        RandomNumberSuppliers.bigDecimalSelection(random, List.of(BigDecimal.valueOf(42.0d), BigDecimal.valueOf(23.0d), BigDecimal.valueOf(1024.0d)))));
        printStream("bigDecimalSelection Array",
                Stream.generate(
                        RandomNumberSuppliers.bigDecimalSelection(random, BigDecimal.valueOf(42.0d), BigDecimal.valueOf(23.0d), BigDecimal.valueOf(1024.0d))));

    }

    public static void main(String... args) {
        showSuppliers();
        showRandomSupplier();
        showRandomSelectionSupplier();
    }

}
