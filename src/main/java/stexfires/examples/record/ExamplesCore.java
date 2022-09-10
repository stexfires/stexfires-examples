package stexfires.examples.record;

import stexfires.record.Field;
import stexfires.record.TextRecord;
import stexfires.record.TextRecords;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;

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
                new ValueFieldRecord("value1"),
                new ValueFieldRecord("value2"),
                new KeyValueFieldsRecord("key", "value"),
                new ManyFieldsRecord("S", "t", "a", "n", "d", "a", "r", "d"),
                new ManyFieldsRecord(),
                TextRecords.empty()
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
        printFieldStream("isFirstField", stream -> stream.map(Field::isFirstField));
        printFieldStream("isLastField", stream -> stream.map(Field::isLastField));
        printFieldStream("recordSize", stream -> stream.map(Field::recordSize));

        printFieldStream("text", stream -> stream.map(Field::text));
        printFieldStream("orElse", stream -> stream.map(field -> field.orElse("<NULL>")));
        printFieldStream("asOptional", stream -> stream.map(Field::asOptional).map(optional -> optional.orElse("<NULL>")));
        printFieldStream("isNotNull", stream -> stream.map(Field::isNotNull));
        printFieldStream("isNull", stream -> stream.map(Field::isNull));
        printFieldStream("isEmpty", stream -> stream.map(Field::isEmpty));
        printFieldStream("isNullOrEmpty", stream -> stream.map(Field::isNullOrEmpty));
        printFieldStream("length", stream -> stream.map(Field::length));
        printFieldStream("stream", stream -> stream.flatMap(Field::stream));

        printFieldStream("toString", stream -> stream.map(Field::toString));
        printFieldStream("hashCode", stream -> stream.map(Field::hashCode));

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
