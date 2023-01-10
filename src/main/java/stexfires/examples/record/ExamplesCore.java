package stexfires.examples.record;

import stexfires.record.TextField;
import stexfires.record.TextRecord;
import stexfires.record.TextRecords;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesCore {

    private ExamplesCore() {
    }

    private static Stream<TextField> generateFieldStream() {
        return Stream.of(
                new TextField(0, 0, "value A"),
                new TextField(0, 2, "value B"),
                new TextField(0, 0, null),
                new TextField(1, 1, "value C"),
                new TextField(1, 1, ""),
                new TextField(1, 2, "value D")
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

    private static void printFieldStream(String title, Function<Stream<TextField>, Stream<Object>> fieldStreamFunction) {
        System.out.println("--" + title);
        System.out.println(fieldStreamFunction.apply(generateFieldStream()).collect(Collectors.toList()));
    }

    private static void printRecord(String title, TextRecord record) {
        System.out.println("--" + title);
        System.out.println(record);
    }

    private static void showField() {
        System.out.println("-showField---");

        printFieldStream("index", stream -> stream.map(TextField::index));
        printFieldStream("maxIndex", stream -> stream.map(TextField::maxIndex));
        printFieldStream("isFirstField", stream -> stream.map(TextField::isFirstField));
        printFieldStream("isLastField", stream -> stream.map(TextField::isLastField));
        printFieldStream("recordSize", stream -> stream.map(TextField::recordSize));

        printFieldStream("text", stream -> stream.map(TextField::text));
        printFieldStream("orElse", stream -> stream.map(field -> field.orElse("<NULL>")));
        printFieldStream("asOptional", stream -> stream.map(TextField::asOptional).map(optional -> optional.orElse("<NULL>")));
        printFieldStream("isNotNull", stream -> stream.map(TextField::isNotNull));
        printFieldStream("isNull", stream -> stream.map(TextField::isNull));
        printFieldStream("isEmpty", stream -> stream.map(TextField::isEmpty));
        printFieldStream("isNullOrEmpty", stream -> stream.map(TextField::isNullOrEmpty));
        printFieldStream("length", stream -> stream.map(TextField::length));
        printFieldStream("stream", stream -> stream.flatMap(TextField::stream));

        printFieldStream("toString", stream -> stream.map(TextField::toString));
        printFieldStream("hashCode", stream -> stream.map(TextField::hashCode));

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

    private static void showStringList() {
        System.out.println("-showStringList---");

        System.out.println(TextRecords.toStringList(TextRecords.empty()));
        System.out.println(TextRecords.toStringList(new ValueFieldRecord("cat", 1L, "value")));
        System.out.println(TextRecords.toStringList(new ValueFieldRecord("value")));
        System.out.println(TextRecords.toStringList(new ValueFieldRecord(null)));

        List<String> stringList0 = new ArrayList<>();
        stringList0.add("cat");
        stringList0.add("1");
        stringList0.add("value");
        RecordSystemOutUtil.printlnRecord(TextRecords.fromStringList(stringList0));
        List<String> stringList1 = new ArrayList<>();
        stringList1.add(null);
        stringList1.add(null);
        stringList1.add("value");
        RecordSystemOutUtil.printlnRecord(TextRecords.fromStringList(stringList1));
        List<String> stringList2 = new ArrayList<>();
        stringList2.add(null);
        stringList2.add(null);
        RecordSystemOutUtil.printlnRecord(TextRecords.fromStringList(stringList2));
        List<String> stringList3 = new ArrayList<>();
        stringList3.add(null);
        stringList3.add("");
        stringList3.add("value0");
        stringList3.add(null);
        stringList3.add("value2");
        RecordSystemOutUtil.printlnRecord(TextRecords.fromStringList(stringList3));
    }

    public static void main(String... args) {
        showField();
        showFields();
        showRecord();
        showRecords();
        showRecordsBuilder();
        showRecordStreams();
        showStringList();
    }

}
