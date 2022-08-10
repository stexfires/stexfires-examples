package stexfires.examples.core;

import stexfires.core.Field;
import stexfires.core.TextRecord;
import stexfires.core.TextRecords;
import stexfires.core.record.EmptyRecord;
import stexfires.core.record.KeyValueRecord;
import stexfires.core.record.SingleRecord;
import stexfires.core.record.StandardRecord;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesCore {

    private ExamplesCore() {
    }

    private static Stream<Field> generateFieldStream() {
        return Stream.of(
                new Field(0, 0, "value A"),
                new Field(0, 2, "value B"),
                new Field(0, 0, null),
                new Field(1, 1, "value C"),
                new Field(1, 1, ""),
                new Field(1, 2, "value D")
        );
    }

    private static Stream<TextRecord> generateRecordStream() {
        return Stream.of(
                new SingleRecord("value1"),
                new SingleRecord("value2"),
                new KeyValueRecord("key", "value"),
                new StandardRecord("S", "t", "a", "n", "d", "a", "r", "d"),
                new StandardRecord(),
                new EmptyRecord()
        );
    }

    private static void printFieldStream(String title, Function<Stream<Field>, Stream<Object>> fieldStreamFunction) {
        System.out.println("--" + title);
        System.out.println(fieldStreamFunction.apply(generateFieldStream()).collect(Collectors.toList()));
    }

    private static void printRecord(String title, TextRecord record) {
        System.out.println("--" + title);
        System.out.println(record);
    }

    private static void showField() {
        System.out.println("-showField---");

        printFieldStream("index", stream -> stream.map(Field::index));
        printFieldStream("maxIndex", stream -> stream.map(Field::maxIndex));
        printFieldStream("value", stream -> stream.map(Field::value));

        printFieldStream("toString", stream -> stream.map(Field::toString));
        printFieldStream("hashCode", stream -> stream.map(Field::hashCode));

        printFieldStream("isFirstField", stream -> stream.map(Field::isFirstField));
        printFieldStream("isLastField", stream -> stream.map(Field::isLastField));
        printFieldStream("valueOrElse", stream -> stream.map(field -> field.valueOrElse("<NULL>")));
        printFieldStream("valueAsOptional", stream -> stream.map(Field::valueAsOptional).map(optional -> optional.orElse("<NULL>")));
        printFieldStream("valueEquals", stream -> stream.map(field -> field.valueEquals("value A")));
        printFieldStream("valueIsNull", stream -> stream.map(Field::valueIsNull));
        printFieldStream("valueIsEmpty", stream -> stream.map(Field::valueIsEmpty));
        printFieldStream("valueIsNullOrEmpty", stream -> stream.map(Field::valueIsNullOrEmpty));
        printFieldStream("valueLength", stream -> stream.map(Field::valueLength));
        printFieldStream("valueAsStream", stream -> stream.flatMap(Field::valueAsStream));
    }

    private static void showFields() {
        System.out.println("-showFields---");
    }

    private static void showRecord() {
        System.out.println("-showRecord---");
    }

    private static void showRecords() {
        System.out.println("-showRecords---");
    }

    private static void showRecordsBuilder() {
        System.out.println("-showRecordsBuilder---");

        printRecord(".", TextRecords.builder().build());
        printRecord(".category", TextRecords.builder().category("category").build());
        printRecord(".recordId", TextRecords.builder().recordId(1000L).build());
        printRecord(".category.recordId", TextRecords.builder().category("category").recordId(0L).build());
        printRecord(".add null", TextRecords.builder().add(null).build());
        printRecord(".add empty", TextRecords.builder().add("").build());
        printRecord(".add value", TextRecords.builder().add("value").build());
        printRecord(".add values", TextRecords.builder().add("firstValue").add("lastValue").build());
        printRecord(".add values", TextRecords.builder().add("firstValue")
                                              .addAll(List.of("secondValue", "thirdValue")).add("lastValue").build());
    }

    private static void showRecordStreams() {
        System.out.println("-RecordStreams---");
    }

    public static void main(String... args) {
        showField();
        showFields();
        showRecord();
        showRecords();
        showRecordsBuilder();
        showRecordStreams();
    }

}
