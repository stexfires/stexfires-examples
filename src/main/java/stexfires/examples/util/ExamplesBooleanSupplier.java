package stexfires.examples.util;

import stexfires.util.NumberCheckType;
import stexfires.util.NumberComparisonType;
import stexfires.util.supplier.RandomBooleanSupplier;
import stexfires.util.supplier.RepeatingPatternBooleanSupplier;
import stexfires.util.supplier.SwitchingBooleanSupplier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("MagicNumber")
public final class ExamplesBooleanSupplier {

    private ExamplesBooleanSupplier() {
    }

    private static void printStream(String title, Stream<Boolean> stream) {
        System.out.println(title);
        stream.limit(10L).forEachOrdered(System.out::println);
    }

    private static void printBoolean(String title, boolean value) {
        System.out.println(title);
        System.out.println(value);
    }

    private static void showRandomBooleanSupplier() {
        System.out.println("-showRandomBooleanSupplier---");

        printStream("Random: 101%",
                Stream.generate(
                        new RandomBooleanSupplier(101)));

        printStream("Random: 100%",
                Stream.generate(
                        new RandomBooleanSupplier(100)));

        printStream("Random: 90%",
                Stream.generate(
                        new RandomBooleanSupplier(75)));

        printStream("Random: 50%",
                Stream.generate(
                        new RandomBooleanSupplier(50)));

        printStream("Random:  1%",
                Stream.generate(
                        new RandomBooleanSupplier(1)));

        printStream("Random:  0%",
                Stream.generate(
                        new RandomBooleanSupplier(0)));

        printStream("Random:  -10%",
                Stream.generate(
                        new RandomBooleanSupplier(-10)));

        System.out.println("Random: 99% 1.000.000");
        System.out.println(Stream.generate(new RandomBooleanSupplier(99))
                                 .limit(1_000_000L)
                                 .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));

        System.out.println("Random: 50% 100.000");
        System.out.println(Stream.generate(new RandomBooleanSupplier(50))
                                 .limit(1_000_000L)
                                 .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));

        System.out.println("Random: 1% 1.000.000");
        System.out.println(Stream.generate(new RandomBooleanSupplier(1))
                                 .limit(1_000_000L)
                                 .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));

        printStream("Random: 50% Seed 1L",
                Stream.generate(
                        new RandomBooleanSupplier(50, 1L)));

        printStream("Random: ThreadLocalRandom Seed 1L",
                Stream.generate(
                        new RandomBooleanSupplier(50, ThreadLocalRandom.current())));

        printBoolean("Random: 50% primitive boolean",
                new RandomBooleanSupplier(50).asPrimitiveBooleanSupplier().getAsBoolean());
    }

    private static void showRepeatingPatternBooleanSupplier() {
        System.out.println("-showRepeatingPatternBooleanSupplier---");

        printStream("Pattern: List [TRUE]",
                Stream.generate(
                        new RepeatingPatternBooleanSupplier(Collections.singletonList(Boolean.TRUE))));

        List<Boolean> booleanList = new ArrayList<>();
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.TRUE);
        booleanList.add(null);
        booleanList.add(Boolean.FALSE);
        printStream("Pattern: List [TRUE, TRUE, null, FALSE]",
                Stream.generate(
                        new RepeatingPatternBooleanSupplier(booleanList)));

        printStream("Pattern: true, true, false",
                Stream.generate(
                        RepeatingPatternBooleanSupplier.primitiveBooleans(true, true, false)));

        printStream("Pattern: true, true, true, false",
                Stream.generate(
                        RepeatingPatternBooleanSupplier.primitiveBooleans(true, true, true, false)));

        printBoolean("Pattern: List [FALSE]  primitive boolean",
                new RepeatingPatternBooleanSupplier(List.of(Boolean.FALSE)).asPrimitiveBooleanSupplier().getAsBoolean());
    }

    private static void showSwitchingBooleanSupplier() {
        System.out.println("-showSwitchingBooleanSupplier---");

        printStream("Switching: Boolean.FALSE, SwitchingBooleanSupplier.DEFAULT_START_INDEX, i -> i == 2",
                Stream.generate(
                        new SwitchingBooleanSupplier(Boolean.FALSE, SwitchingBooleanSupplier.DEFAULT_START_INDEX, i -> i == 2)));

        printStream("Switching: onlyOnce at index 3",
                Stream.generate(
                        SwitchingBooleanSupplier.onlyOnce(false, 3)));

        printStream("Switching: everyTime",
                Stream.generate(
                        SwitchingBooleanSupplier.everyTime(false)));

        printStream("Switching: EVEN",
                Stream.generate(
                        SwitchingBooleanSupplier.check(true, NumberCheckType.EVEN)));

        printStream("Switching: MULTIPLE_OF 3",
                Stream.generate(
                        SwitchingBooleanSupplier.compare(true, NumberComparisonType.MULTIPLE_OF, 3)));

        printBoolean("Switching: everyTime primitive boolean",
                SwitchingBooleanSupplier.everyTime(true).asPrimitiveBooleanSupplier().getAsBoolean());
    }

    public static void main(String[] args) {
        showRandomBooleanSupplier();
        showRepeatingPatternBooleanSupplier();
        showSwitchingBooleanSupplier();
    }

}
