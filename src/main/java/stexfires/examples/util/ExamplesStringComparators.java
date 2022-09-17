package stexfires.examples.util;

import stexfires.util.StringComparators;
import stexfires.util.function.StringUnaryOperatorType;

import java.text.Collator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@SuppressWarnings({"UseOfSystemOutOrSystemErr"})
public final class ExamplesStringComparators {

    private ExamplesStringComparators() {
    }

    @SuppressWarnings("StaticCollection")
    private static final List<String> STRING_VALUES = List.of(
            "!",
            "a",
            "A",
            "aa",
            "aA",
            "AA",
            "Aa",
            " a ",
            "s",
            "ss",
            "S",
            "SS",
            "\uD83D\uDE01",
            "\u00e0",
            "\u00e1",
            "\u00e2",
            "\u00e3",
            "\u00e4",
            "\u00e5",
            "\u00e6",
            "\u20ac",
            "\u00df",
            "\u1e9e",
            "\u00f6",
            "a\u0308",
            "A\u0308",
            "\u00e9",
            "e\u0301",
            "\ufb03",
            "ffi",
            "\u00e7",
            "c\u0327",
            "\u00c5",
            "A\u030a",
            "\u212b",
            "\uD83D\uDE00"
    );

    private static void showStringComparators() {
        System.out.println("-showStringComparators---");

        System.out.println("--compareTo");
        STRING_VALUES.stream()
                     .sorted(StringComparators.compareTo())
                     .forEachOrdered(System.out::println);

        System.out.println("--compareToIgnoreCase");
        STRING_VALUES.stream()
                     .sorted(StringComparators.compareToIgnoreCase())
                     .forEachOrdered(System.out::println);

        System.out.println("--collatorComparator default locale");
        STRING_VALUES.stream()
                     .sorted(StringComparators.collatorComparator(Collator.getInstance()))
                     .forEachOrdered(System.out::println);

        System.out.println("--collatorComparator Locale.ENGLISH");
        STRING_VALUES.stream()
                     .sorted(StringComparators.collatorComparator(Locale.ENGLISH))
                     .forEachOrdered(System.out::println);

        System.out.println("--collatorComparator Locale.GERMAN");
        STRING_VALUES.stream()
                     .sorted(StringComparators.collatorComparator(Locale.GERMAN))
                     .forEachOrdered(System.out::println);

        System.out.println("--collatorComparator Locale.GERMAN PRIMARY");
        STRING_VALUES.stream()
                     .sorted(StringComparators.collatorComparator(Locale.GERMAN, Collator.PRIMARY))
                     .forEachOrdered(System.out::println);

        System.out.println("--collatorComparator Locale.GERMAN SECONDARY");
        STRING_VALUES.stream()
                     .sorted(StringComparators.collatorComparator(Locale.GERMAN, Collator.SECONDARY))
                     .forEachOrdered(System.out::println);

        System.out.println("--collatorComparator Locale.GERMAN TERTIARY");
        STRING_VALUES.stream()
                     .sorted(StringComparators.collatorComparator(Locale.GERMAN, Collator.TERTIARY))
                     .forEachOrdered(System.out::println);

        System.out.println("--collatorComparator Locale.GERMAN IDENTICAL");
        STRING_VALUES.stream()
                     .sorted(StringComparators.collatorComparator(Locale.GERMAN, Collator.IDENTICAL))
                     .forEachOrdered(System.out::println);

        System.out.println("--collatorComparator normalizedComparator trim compareTo");
        STRING_VALUES.stream()
                     .sorted(StringComparators.normalizedComparator(String::trim, String::compareTo))
                     .forEachOrdered(System.out::println);

        System.out.println("--collatorComparator normalizedComparator NORMALIZE_NFKD compareTo");
        STRING_VALUES.stream()
                     .sorted(StringComparators.normalizedComparator(StringUnaryOperatorType.NORMALIZE_NFKD, String::compareTo))
                     .forEachOrdered(System.out::println);
    }

    private static void showStringCollators() {
        System.out.println("-showStringCollators---");

        System.out.println("--collator Locale.GERMAN PRIMARY");
        {
            Collator collator = Collator.getInstance(Locale.GERMAN);
            collator.setStrength(Collator.PRIMARY);
            STRING_VALUES.stream()
                         .collect(Collectors.collectingAndThen(Collectors.groupingBy(collator::getCollationKey), r -> r.values().stream()))
                         .forEachOrdered(System.out::println);
        }

        System.out.println("--collator Locale.GERMAN SECONDARY");
        {
            Collator collator = Collator.getInstance(Locale.GERMAN);
            collator.setStrength(Collator.SECONDARY);
            STRING_VALUES.stream()
                         .collect(Collectors.collectingAndThen(Collectors.groupingBy(collator::getCollationKey), r -> r.values().stream()))
                         .forEachOrdered(System.out::println);
        }

        System.out.println("--collator Locale.GERMAN TERTIARY");
        {
            Collator collator = Collator.getInstance(Locale.GERMAN);
            collator.setStrength(Collator.TERTIARY);
            STRING_VALUES.stream()
                         .collect(Collectors.collectingAndThen(Collectors.groupingBy(collator::getCollationKey), r -> r.values().stream()))
                         .forEachOrdered(System.out::println);
        }

        System.out.println("--collator Locale.GERMAN IDENTICAL");
        {
            Collator collator = Collator.getInstance(Locale.GERMAN);
            collator.setStrength(Collator.IDENTICAL);
            STRING_VALUES.stream()
                         .collect(Collectors.collectingAndThen(Collectors.groupingBy(collator::getCollationKey), r -> r.values().stream()))
                         .forEachOrdered(System.out::println);
        }
    }

    public static void main(String... args) {
        showStringComparators();
        showStringCollators();
    }

}
