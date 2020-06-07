package stexfires.examples.core;

import stexfires.core.Field;
import stexfires.core.Fields;
import stexfires.core.RecordStreams;
import stexfires.core.TextRecord;
import stexfires.core.comparator.FieldComparators;
import stexfires.core.comparator.NULLS;
import stexfires.core.comparator.RecordComparators;
import stexfires.core.comparator.StringComparators;
import stexfires.core.consumer.SystemOutConsumer;
import stexfires.core.record.KeyValueRecord;
import stexfires.core.record.StandardRecord;
import stexfires.util.StringUnaryOperatorType;

import java.text.Collator;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesComparator {

    private ExamplesComparator() {
    }

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
            "\u212b"
    );

    private static Stream<StandardRecord> generateStreamStandardRecord() {
        return Stream.of(
                new StandardRecord("C4", 6L, "value0"),
                new StandardRecord(null, 2L, "value0", "value1b"),
                new StandardRecord("C1", 5L, "value0", "value1a", "value2"),
                new StandardRecord("C2", 0L, "value0b"),
                new StandardRecord(null, 2L, "value0a"),
                new StandardRecord(null, 1L, "value0c"),
                new StandardRecord("C0", (Long) null, "value0"),
                new StandardRecord("C4", 7L),
                new StandardRecord("C4", 8L, null, null, null, null),
                new StandardRecord()
        );
    }

    private static Stream<KeyValueRecord> generateStreamKeyValueRecord() {
        return Stream.of(
                new KeyValueRecord("key4", "value5"),
                new KeyValueRecord("key2", "value2"),
                new KeyValueRecord("key0", "value1"),
                new KeyValueRecord("key2", "value3"),
                new KeyValueRecord("key5", null),
                new KeyValueRecord("key1", "value0")
        );
    }

    private static void printComparatorRecord(String title, Comparator<? super StandardRecord> recordComparator) {
        System.out.println("--" + title);
        RecordStreams.sortAndConsume(generateStreamStandardRecord(), recordComparator, new SystemOutConsumer<>());
    }

    private static void printComparatorKeyValueRecord(String title, Comparator<? super KeyValueRecord> recordComparator) {
        System.out.println("--" + title);
        RecordStreams.sortAndConsume(generateStreamKeyValueRecord(), recordComparator, new SystemOutConsumer<>());
    }

    private static void printComparatorField(String title, Comparator<Field> fieldComparator) {
        System.out.println("--" + title);
        Fields.printLines(Fields.sortFields(generateStreamStandardRecord().flatMap(TextRecord::streamOfFields), fieldComparator));
    }

    private static void showRecordComparators() {
        System.out.println("-showRecordComparators---");

        // category
        printComparatorRecord("category: nullsFirst(naturalOrder)", RecordComparators.category(Comparator.nullsFirst(Comparator.naturalOrder())));
        printComparatorRecord("category: reverseOrder, NULLS.LAST", RecordComparators.category(Comparator.reverseOrder(), NULLS.LAST));

        // recordId
        printComparatorRecord("recordId: nullsFirst(naturalOrder)", RecordComparators.recordId(Comparator.nullsFirst(Comparator.naturalOrder())));
        printComparatorRecord("recordId: reverseOrder, NULLS.LAST", RecordComparators.recordId(Comparator.reverseOrder(), NULLS.LAST));
        printComparatorRecord("recordId: NULLS.LAST", RecordComparators.recordId(NULLS.LAST));

        // size
        printComparatorRecord("size", RecordComparators.size());

        // field
        printComparatorRecord("field: getFieldAt(1), length, NULLS.LAST", RecordComparators.field(record -> record.getFieldAt(1), FieldComparators.length(), NULLS.LAST));
        printComparatorRecord("fieldAt: 1, length, NULLS.LAST", RecordComparators.fieldAt(1, FieldComparators.length(), NULLS.LAST));
        printComparatorRecord("firstField: length, NULLS.LAST", RecordComparators.firstField(FieldComparators.length(), NULLS.LAST));
        printComparatorRecord("lastField: length, NULLS.LAST", RecordComparators.lastField(FieldComparators.length(), NULLS.LAST));

        // value
        printComparatorRecord("value: getValueAt(1), naturalOrder(), NULLS.LAST", RecordComparators.value(record -> record.getValueAt(1), Comparator.naturalOrder(), NULLS.LAST));
        printComparatorRecord("valueAt: 1, naturalOrder(), NULLS.LAST", RecordComparators.valueAt(1, Comparator.naturalOrder(), NULLS.LAST));
        printComparatorRecord("firstValue: naturalOrder(), NULLS.LAST", RecordComparators.firstValue(Comparator.naturalOrder(), NULLS.LAST));
        printComparatorRecord("lastValue: naturalOrder(), NULLS.LAST", RecordComparators.lastValue(Comparator.naturalOrder(), NULLS.LAST));

        // KeyValueRecord
        printComparatorKeyValueRecord("valueOfKeyField: naturalOrder()", RecordComparators.valueOfKeyField(Comparator.naturalOrder()));
        printComparatorKeyValueRecord("valueOfValueField: naturalOrder(), NULLS.LAST", RecordComparators.valueOfValueField(Comparator.naturalOrder(), NULLS.LAST));

        // Combined
        printComparatorRecord("Combined: category(reversed(), NULLS.LAST), recordId(NULLS.LAST)", RecordComparators.category(Comparator.reverseOrder(), NULLS.LAST).thenComparing(RecordComparators.recordId(NULLS.LAST)));
    }

    private static void showFieldComparators() {
        System.out.println("-showFieldComparators---");

        printComparatorField("index", FieldComparators.index());
        printComparatorField("first", FieldComparators.first());
        printComparatorField("last", FieldComparators.last());
        printComparatorField("value: nullsLast(naturalOrder())", FieldComparators.value(Comparator.nullsLast(Comparator.naturalOrder())));
        printComparatorField("value: naturalOrder(), NULLS.LAST", FieldComparators.value(Comparator.naturalOrder(), NULLS.LAST));
        printComparatorField("length", FieldComparators.length());
    }

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

    public static void main(String[] args) {
        showRecordComparators();
        showFieldComparators();
        showStringComparators();
        showStringCollators();
    }

}
