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

    private static void showFieldStream(String title, Function<Stream<Field>, Stream<Object>> fieldStreamFunction) {
        System.out.println("--" + title);
        System.out.println(fieldStreamFunction.apply(generateFieldStream()).collect(Collectors.toList()));
    }

    private static void showRecord(String title, Record record) {
        System.out.println("--" + title);
        System.out.println(record);
    }

    private static void showField() {
        System.out.println("-showField---");

        showFieldStream("getIndex", stream -> stream.map(Field::getIndex));
        showFieldStream("isFirst", stream -> stream.map(Field::isFirst));
        showFieldStream("isLast", stream -> stream.map(Field::isLast));
        showFieldStream("getValue", stream -> stream.map(Field::getValue));
        showFieldStream("getValueOrElse", stream -> stream.map(field -> field.getValueOrElse("<NULL>")));
        showFieldStream("getValueAsOptional", stream -> stream.map(Field::getValueAsOptional).map(optional -> optional.orElse("<NULL>")));
        showFieldStream("valueEquals", stream -> stream.map(field -> field.valueEquals("value A")));
        showFieldStream("valueIsNull", stream -> stream.map(Field::valueIsNull));
        showFieldStream("valueIsEmpty", stream -> stream.map(Field::valueIsEmpty));
        showFieldStream("valueIsNullOrEmpty", stream -> stream.map(Field::valueIsNullOrEmpty));
        showFieldStream("length", stream -> stream.map(Field::length));
        showFieldStream("stream", stream -> stream.flatMap(Field::stream));
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

        showRecord(".", Records.builder().build());
        showRecord(".category", Records.builder().category("category").build());
        showRecord(".recordId", Records.builder().recordId(1000L).build());
        showRecord(".category.recordId", Records.builder().category("category").recordId(0L).build());
        showRecord(".add null", Records.builder().add(null).build());
        showRecord(".add empty", Records.builder().add("").build());
        showRecord(".add value", Records.builder().add("value").build());
        showRecord(".add values", Records.builder().add("firstValue").add("lastValue").build());
        showRecord(".add values", Records.builder().add("firstValue")
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
