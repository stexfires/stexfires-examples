package stexfires.examples.core;

import stexfires.core.Record;
import stexfires.core.RecordStreams;
import stexfires.core.comparator.FieldComparators;
import stexfires.core.comparator.NULLS;
import stexfires.core.comparator.RecordComparators;
import stexfires.core.record.KeyValueRecord;
import stexfires.core.record.StandardRecord;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Stream;

@SuppressWarnings("MagicNumber")
public final class ExamplesComparator {

    private ExamplesComparator() {
    }

    private static Stream<StandardRecord> generateStream() {
        return Stream.of(
                new StandardRecord("C4", 6L, "value0"),
                new StandardRecord(null, 2L, "value0", "value1b"),
                new StandardRecord("C1", 5L, "value0", "value1a", "value2"),
                new StandardRecord("C2", 0L, "value0b"),
                new StandardRecord(null, 2L, "value0a"),
                new StandardRecord("C0", (Long) null, "value0"),
                new StandardRecord("C4", 7L),
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

    private static void showComparator(String title, Comparator<Record> recordComparator) {
        System.out.println("--" + title);
        RecordStreams.sort(generateStream(), recordComparator)
                     .forEachOrdered(System.out::println);
    }

    private static void showComparatorKeyValueRecord(String title, Comparator<KeyValueRecord> recordComparator) {
        System.out.println("--" + title);
        RecordStreams.sort(generateStreamKeyValueRecord(), recordComparator)
                     .forEachOrdered(System.out::println);
    }

    private static void showCategory() {
        System.out.println("-showCategory---");

        showComparator("category Comparator reverse NULLS LAST", RecordComparators.category(Comparator.reverseOrder(), NULLS.LAST));
        showComparator("category Comparator", RecordComparators.category(Comparator.naturalOrder()));
        showComparator("category NULLS LAST", RecordComparators.category(NULLS.LAST));
        showComparator("category", RecordComparators.category());
        showComparator("category Collator NULLS LAST", RecordComparators.category(Collator.getInstance(Locale.ENGLISH), NULLS.LAST));
        showComparator("category Collator", RecordComparators.category(Collator.getInstance(Locale.ENGLISH)));
    }

    private static void showRecordId() {
        System.out.println("-showRecordId---");

        showComparator("recordId Comparator reverse NULLS LAST", RecordComparators.recordId(Comparator.reverseOrder(), NULLS.LAST));
        showComparator("recordId Comparator", RecordComparators.recordId(Comparator.naturalOrder()));
        showComparator("recordId NULLS LAST", RecordComparators.recordId(NULLS.LAST));
        showComparator("recordId", RecordComparators.recordId());
    }

    private static void showSize() {
        System.out.println("-showSize---");

        showComparator("size", RecordComparators.size());
    }

    private static void showField() {
        System.out.println("-showField---");

        showComparator("firstField length NULLS LAST", RecordComparators.firstField(FieldComparators.length(), NULLS.LAST));
        showComparator("lastField length NULLS LAST", RecordComparators.lastField(FieldComparators.length(), NULLS.LAST));
        showComparator("fieldAt 1 length NULLS LAST", RecordComparators.fieldAt(1, FieldComparators.length(), NULLS.LAST));
    }

    private static void showValue() {
        System.out.println("-showValue---");

        showComparator("firstValue NULLS LAST", RecordComparators.firstValue(Comparator.naturalOrder(), NULLS.LAST));
        showComparator("lastValue NULLS LAST", RecordComparators.lastValue(Comparator.naturalOrder(), NULLS.LAST));
        showComparator("valueAt 0 NULLS LAST", RecordComparators.valueAt(0, Comparator.naturalOrder(), NULLS.LAST));
        showComparator("valueAt 1 NULLS FIRST", RecordComparators.valueAt(1, Comparator.naturalOrder(), NULLS.FIRST));
    }

    private static void showValueOfField() {
        System.out.println("-showValueOfField---");

        showComparatorKeyValueRecord("valueOfKeyField", RecordComparators.valueOfKeyField(Comparator.naturalOrder()));
        showComparatorKeyValueRecord("valueOfValueField NULLS LAST", RecordComparators.valueOfValueField(Comparator.naturalOrder(), NULLS.LAST));
    }

    private static void showRecordComparators() {
        System.out.println("-showRecordComparators---");

        showComparator("category reversed and then recordId NULLS LAST",
                RecordComparators.category()
                                 .reversed()
                                 .thenComparing(RecordComparators.recordId(NULLS.LAST)));
        showComparator("firstValue and then category",
                RecordComparators.firstValue(Comparator.naturalOrder(), NULLS.FIRST)
                                 .thenComparing(RecordComparators.category()));
    }

    private static void showFieldComparators() {
        System.out.println("-showFieldComparators---");

        System.out.println("--index");
        generateStream().map(Record::streamOfFields)
                        .flatMap(Function.identity())
                        .sorted(FieldComparators.index())
                        .forEachOrdered(System.out::println);
        System.out.println("--length");
        generateStream().map(Record::streamOfFields)
                        .flatMap(Function.identity())
                        .sorted(FieldComparators.length())
                        .forEachOrdered(System.out::println);
        System.out.println("--value");
        generateStream().map(Record::streamOfFields)
                        .flatMap(Function.identity())
                        .sorted(FieldComparators.value(Comparator.naturalOrder(), NULLS.LAST))
                        .forEachOrdered(System.out::println);
        System.out.println("--index reversed and then length");
        generateStream().map(Record::streamOfFields)
                        .flatMap(Function.identity())
                        .sorted(FieldComparators.index().reversed().thenComparing(FieldComparators.length()))
                        .forEachOrdered(System.out::println);
    }

    public static void main(String[] args) {
        showCategory();
        showRecordId();
        showSize();
        showField();
        showValue();
        showValueOfField();
        showRecordComparators();
        showFieldComparators();
    }

}
