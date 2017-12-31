package stexfires.examples.util;

import stexfires.util.NumberCheckType;
import stexfires.util.NumberComparisonType;
import stexfires.util.supplier.RandomBooleanSupplier;
import stexfires.util.supplier.RepeatingPatternBooleanSupplier;
import stexfires.util.supplier.SwitchingBooleanSupplier;

import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ExamplesBooleanSupplier {

    private static void printStream(String title, Stream<Boolean> stream) {
        System.out.println(title);
        stream.limit(10).forEachOrdered(System.out::println);
    }

    private static void showSwitchingBooleanSupplier() {
        System.out.println("-show SwitchingBooleanSupplier---");

        printStream("Switching: onlyOnce at index 3",
                Stream.generate(SwitchingBooleanSupplier.onlyOnce(false, 3)));
        printStream("Switching: everyTime",
                Stream.generate(SwitchingBooleanSupplier.everyTime(false)));
        printStream("Switching: EVEN",
                Stream.generate(SwitchingBooleanSupplier.check(true, NumberCheckType.EVEN)));
        printStream("Switching: MULTIPLE_OF 3",
                Stream.generate(SwitchingBooleanSupplier.compare(true, NumberComparisonType.MULTIPLE_OF, 3)));
    }

    private static void showRandomBooleanSupplier() {
        System.out.println("-show RandomBooleanSupplier---");

        printStream("Random: 100%",
                Stream.generate(new RandomBooleanSupplier(100)));
        printStream("Random: 90%",
                Stream.generate(new RandomBooleanSupplier(90)));
        printStream("Random: 50%",
                Stream.generate(new RandomBooleanSupplier(50)));
        printStream("Random:  1%",
                Stream.generate(new RandomBooleanSupplier(1)));
        printStream("Random:  0%",
                Stream.generate(new RandomBooleanSupplier(0)));

        System.out.println("Random: 50% 100000");
        System.out.println(Stream.generate(new RandomBooleanSupplier(50))
                .limit(100000).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
    }

    private static void showRepeatingPatternBooleanSupplier() {
        System.out.println("-show RepeatingPatternBooleanSupplier---");

        printStream("Pattern: true, true, false",
                Stream.generate(RepeatingPatternBooleanSupplier.primitiveBooleans(true, true, false)));
        printStream("Pattern: true, true, true, false",
                Stream.generate(RepeatingPatternBooleanSupplier.primitiveBooleans(true, true, true, false)));
        printStream("Pattern: List [TRUE]",
                Stream.generate(new RepeatingPatternBooleanSupplier(Collections.singletonList(Boolean.TRUE))));
    }

    public static void main(String[] args) {
        showSwitchingBooleanSupplier();
        showRandomBooleanSupplier();
        showRepeatingPatternBooleanSupplier();
    }

}
