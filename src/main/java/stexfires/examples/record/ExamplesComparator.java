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
import stexfires.util.NULLS;

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
        printComparatorRecord("category: reverseOrder, NULLS.LAST", RecordComparators.category(Comparator.reverseOrder(), NULLS.LAST));

        // recordId
        printComparatorRecord("recordId: nullsFirst(naturalOrder)", RecordComparators.recordId(Comparator.nullsFirst(Comparator.naturalOrder())));
        printComparatorRecord("recordId: reverseOrder, NULLS.LAST", RecordComparators.recordId(Comparator.reverseOrder(), NULLS.LAST));
        printComparatorRecord("recordId: NULLS.LAST", RecordComparators.recordId(NULLS.LAST));

        // size
        printComparatorRecord("size", RecordComparators.size());

        // field
        printComparatorRecord("field: getFieldAt(1), length, NULLS.LAST", RecordComparators.field(record -> record.fieldAt(1), FieldComparators.valueLength(), NULLS.LAST));
        printComparatorRecord("fieldAt: 1, length, NULLS.LAST", RecordComparators.fieldAt(1, FieldComparators.valueLength(), NULLS.LAST));
        printComparatorRecord("firstField: length, NULLS.LAST", RecordComparators.firstField(FieldComparators.valueLength(), NULLS.LAST));
        printComparatorRecord("lastField: length, NULLS.LAST", RecordComparators.lastField(FieldComparators.valueLength(), NULLS.LAST));

        // value
        printComparatorRecord("value: getValueAt(1), naturalOrder(), NULLS.LAST", RecordComparators.value(record -> record.valueAt(1), Comparator.naturalOrder(), NULLS.LAST));
        printComparatorRecord("valueAt: 1, naturalOrder(), NULLS.LAST", RecordComparators.valueAt(1, Comparator.naturalOrder(), NULLS.LAST));
        printComparatorRecord("firstValue: naturalOrder(), NULLS.LAST", RecordComparators.valueOfFirstField(Comparator.naturalOrder(), NULLS.LAST));
        printComparatorRecord("lastValue: naturalOrder(), NULLS.LAST", RecordComparators.valueOfLastField(Comparator.naturalOrder(), NULLS.LAST));

        // KeyValueRecord
        printComparatorKeyValueRecord("valueOfKeyField: naturalOrder()", RecordComparators.valueOfKeyField(Comparator.naturalOrder()));
        printComparatorKeyValueRecord("valueOfValueField: naturalOrder(), NULLS.LAST", RecordComparators.valueOfValueField(Comparator.naturalOrder(), NULLS.LAST));

        // Combined
        printComparatorRecord("Combined: category(reversed(), NULLS.LAST), recordId(NULLS.LAST)", RecordComparators.category(Comparator.reverseOrder(), NULLS.LAST).thenComparing(RecordComparators.recordId(NULLS.LAST)));
    }

    private static void showFieldComparators() {
        System.out.println("-showFieldComparators---");

        printComparatorField("index", FieldComparators.index());
        printComparatorField("first", FieldComparators.isFirstField());
        printComparatorField("last", FieldComparators.isLastField());
        printComparatorField("value: nullsLast(naturalOrder())", FieldComparators.value(Comparator.nullsLast(Comparator.naturalOrder())));
        printComparatorField("value: naturalOrder(), NULLS.LAST", FieldComparators.value(Comparator.naturalOrder(), NULLS.LAST));
        printComparatorField("length", FieldComparators.valueLength());
    }

    public static void main(String... args) {
        showRecordComparators();
        showFieldComparators();
    }

}
