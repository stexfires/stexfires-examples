package stexfires.examples.record;

import stexfires.record.Field;
import stexfires.record.Fields;
import stexfires.record.TextRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.comparator.FieldComparators;
import stexfires.record.comparator.RecordComparators;
import stexfires.record.consumer.SystemOutConsumer;
import stexfires.record.impl.KeyValueRecord;
import stexfires.record.impl.ManyValuesRecord;
import stexfires.util.SortNulls;

import java.util.Comparator;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesComparator {

    private ExamplesComparator() {
    }

    private static Stream<ManyValuesRecord> generateStreamManyValuesRecord() {
        return Stream.of(
                new ManyValuesRecord("C4", 6L, "value0"),
                new ManyValuesRecord(null, 2L, "value0", "value1b"),
                new ManyValuesRecord("C1", 5L, "value0", "value1a", "value2"),
                new ManyValuesRecord("C2", 0L, "value0b"),
                new ManyValuesRecord(null, 2L, "value0a"),
                new ManyValuesRecord(null, 1L, "value0c"),
                new ManyValuesRecord("C0", (Long) null, "value0"),
                new ManyValuesRecord("C4", 7L),
                new ManyValuesRecord("C4", 8L, null, null, null, null),
                new ManyValuesRecord()
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

    private static void printComparatorRecord(String title, Comparator<? super ManyValuesRecord> recordComparator) {
        System.out.println("--" + title);
        TextRecordStreams.sortAndConsume(generateStreamManyValuesRecord(), recordComparator, new SystemOutConsumer<>());
    }

    private static void printComparatorKeyValueRecord(String title, Comparator<? super KeyValueRecord> recordComparator) {
        System.out.println("--" + title);
        TextRecordStreams.sortAndConsume(generateStreamKeyValueRecord(), recordComparator, new SystemOutConsumer<>());
    }

    private static void printComparatorField(String title, Comparator<Field> fieldComparator) {
        System.out.println("--" + title);
        Fields.printLines(Fields.sortFields(generateStreamManyValuesRecord().flatMap(TextRecord::streamOfFields), fieldComparator));
    }

    private static void showRecordComparators() {
        System.out.println("-showRecordComparators---");

        // category
        printComparatorRecord("category: nullsFirst(naturalOrder)", RecordComparators.category(Comparator.nullsFirst(Comparator.naturalOrder())));
        printComparatorRecord("category: reverseOrder, SortNulls.LAST", RecordComparators.category(Comparator.reverseOrder(), SortNulls.LAST));

        // recordId
        printComparatorRecord("recordId: nullsFirst(naturalOrder)", RecordComparators.recordId(Comparator.nullsFirst(Comparator.naturalOrder())));
        printComparatorRecord("recordId: reverseOrder, SortNulls.LAST", RecordComparators.recordId(Comparator.reverseOrder(), SortNulls.LAST));
        printComparatorRecord("recordId: SortNulls.LAST", RecordComparators.recordId(SortNulls.LAST));

        // size
        printComparatorRecord("size", RecordComparators.size());

        // field
        printComparatorRecord("field: getFieldAt(1), length, SortNulls.LAST", RecordComparators.field(record -> record.fieldAt(1), FieldComparators.length(), SortNulls.LAST));
        printComparatorRecord("fieldAt: 1, length, SortNulls.LAST", RecordComparators.fieldAt(1, FieldComparators.length(), SortNulls.LAST));
        printComparatorRecord("firstField: length, SortNulls.LAST", RecordComparators.firstField(FieldComparators.length(), SortNulls.LAST));
        printComparatorRecord("lastField: length, SortNulls.LAST", RecordComparators.lastField(FieldComparators.length(), SortNulls.LAST));

        // value
        printComparatorRecord("value: getValueAt(1), naturalOrder(), SortNulls.LAST", RecordComparators.value(record -> record.valueAt(1), Comparator.naturalOrder(), SortNulls.LAST));
        printComparatorRecord("valueAt: 1, naturalOrder(), SortNulls.LAST", RecordComparators.valueAt(1, Comparator.naturalOrder(), SortNulls.LAST));
        printComparatorRecord("firstValue: naturalOrder(), SortNulls.LAST", RecordComparators.valueOfFirstField(Comparator.naturalOrder(), SortNulls.LAST));
        printComparatorRecord("lastValue: naturalOrder(), SortNulls.LAST", RecordComparators.valueOfLastField(Comparator.naturalOrder(), SortNulls.LAST));

        // KeyValueRecord
        printComparatorKeyValueRecord("key: naturalOrder()", RecordComparators.key(Comparator.naturalOrder()));
        printComparatorKeyValueRecord("value: naturalOrder(), SortNulls.LAST", RecordComparators.value(Comparator.naturalOrder(), SortNulls.LAST));

        // Combined
        printComparatorRecord("Combined: category(reversed(), SortNulls.LAST), recordId(SortNulls.LAST)", RecordComparators.category(Comparator.reverseOrder(), SortNulls.LAST).thenComparing(RecordComparators.recordId(SortNulls.LAST)));
    }

    private static void showFieldComparators() {
        System.out.println("-showFieldComparators---");

        printComparatorField("index", FieldComparators.index());
        printComparatorField("first", FieldComparators.isFirstField());
        printComparatorField("last", FieldComparators.isLastField());
        printComparatorField("value: nullsLast(naturalOrder())", FieldComparators.value(Comparator.nullsLast(Comparator.naturalOrder())));
        printComparatorField("value: naturalOrder(), SortNulls.LAST", FieldComparators.value(Comparator.naturalOrder(), SortNulls.LAST));
        printComparatorField("length", FieldComparators.length());
    }

    public static void main(String... args) {
        showRecordComparators();
        showFieldComparators();
    }

}
