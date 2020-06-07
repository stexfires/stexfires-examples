package stexfires.examples.core;

import stexfires.core.Field;
import stexfires.core.Record;
import stexfires.core.Records;
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
                new Field(0, true, "value A"),
                new Field(0, false, "value B"),
                new Field(0, true, null),
                new Field(1, true, "value C"),
                new Field(1, true, ""),
                new Field(1, false, "value D")
        );
    }

    private static Stream<Record> generateRecordStream() {
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

    private static void printRecord(String title, Record record) {
        System.out.println("--" + title);
        System.out.println(record);
    }

    private static void showField() {
        System.out.println("-showField---");

        printFieldStream("getIndex", stream -> stream.map(Field::getIndex));
        printFieldStream("isFirst", stream -> stream.map(Field::isFirst));
        printFieldStream("isLast", stream -> stream.map(Field::isLast));
        printFieldStream("getValue", stream -> stream.map(Field::getValue));
        printFieldStream("getValueOrElse", stream -> stream.map(field -> field.getValueOrElse("<NULL>")));
        printFieldStream("getValueAsOptional", stream -> stream.map(Field::getValueAsOptional).map(optional -> optional.orElse("<NULL>")));
        printFieldStream("valueEquals", stream -> stream.map(field -> field.valueEquals("value A")));
        printFieldStream("valueIsNull", stream -> stream.map(Field::valueIsNull));
        printFieldStream("valueIsEmpty", stream -> stream.map(Field::valueIsEmpty));
        printFieldStream("valueIsNullOrEmpty", stream -> stream.map(Field::valueIsNullOrEmpty));
        printFieldStream("length", stream -> stream.map(Field::length));
        printFieldStream("stream", stream -> stream.flatMap(Field::stream));
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

        printRecord(".", Records.builder().build());
        printRecord(".category", Records.builder().category("category").build());
        printRecord(".recordId", Records.builder().recordId(1000L).build());
        printRecord(".category.recordId", Records.builder().category("category").recordId(0L).build());
        printRecord(".add null", Records.builder().add(null).build());
        printRecord(".add empty", Records.builder().add("").build());
        printRecord(".add value", Records.builder().add("value").build());
        printRecord(".add values", Records.builder().add("firstValue").add("lastValue").build());
        printRecord(".add values", Records.builder().add("firstValue")
                                          .addAll(List.of("secondValue", "thirdValue")).add("lastValue").build());
    }

    private static void showRecordStreams() {
        System.out.println("-RecordStreams---");
    }

    public static void main(String[] args) {
        showField();
        showFields();
        showRecord();
        showRecords();
        showRecordsBuilder();
        showRecordStreams();
    }

}
