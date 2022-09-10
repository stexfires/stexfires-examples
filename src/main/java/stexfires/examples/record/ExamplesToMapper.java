package stexfires.examples.record;

import stexfires.record.TextRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.TextRecords;
import stexfires.record.consumer.SystemOutConsumer;
import stexfires.record.impl.KeyValueCommentFieldsRecord;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.TwoFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.mapper.RecordMapper;
import stexfires.record.mapper.impl.ToEmptyRecordMapper;
import stexfires.record.mapper.impl.ToKeyValueCommentFieldsRecordMapper;
import stexfires.record.mapper.impl.ToKeyValueFieldsRecordMapper;
import stexfires.record.mapper.impl.ToManyFieldsRecordMapper;
import stexfires.record.mapper.impl.ToTwoFieldsRecordMapper;
import stexfires.record.mapper.impl.ToValueFieldRecordMapper;
import stexfires.util.Strings;

import java.util.stream.Stream;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public final class ExamplesToMapper {

    private ExamplesToMapper() {
    }

    private static Stream<TextRecord> generateStream() {
        return Stream.of(
                TextRecords.empty(),
                new KeyValueFieldsRecord("key", "value"),
                new KeyValueFieldsRecord("category", 0L, "key", "value"),
                new KeyValueCommentFieldsRecord("category", 0L, "key", "value", "comment"),
                new TwoFieldsRecord("first", "second"),
                new TwoFieldsRecord("category", 0L, "first", "second"),
                new ValueFieldRecord("value"),
                new ValueFieldRecord("category", 0L, "value"),
                new ManyFieldsRecord(),
                new ManyFieldsRecord(Strings.list("value1", "value2", "value3")),
                new ManyFieldsRecord("category", 0L, Strings.list("value1", "value2", "value3")),
                new ManyFieldsRecord("category", 0L, Strings.list("value1", "value2", "value3", "value4"))
        );
    }

    private static void printRecordMapper(String title, RecordMapper<TextRecord, ? extends TextRecord> recordMapper) {
        System.out.println("--" + title);
        TextRecordStreams.mapAndConsume(generateStream(), recordMapper, new SystemOutConsumer<>());
    }

    private static void showToEmptyMapper() {
        System.out.println("-showToEmptyMapper---");

        printRecordMapper("constructor", new ToEmptyRecordMapper<>());
    }

    private static void showToKeyValueMapper() {
        System.out.println("-showToKeyValueMapper---");

        printRecordMapper("constructor (0, 1, missing key)", new ToKeyValueFieldsRecordMapper<>(0, 1, "missing key"));
    }

    private static void showToKeyValueCommentMapper() {
        System.out.println("-showToKeyValueCommentMapper---");

        printRecordMapper("constructor (0, 1, missing key)", new ToKeyValueCommentFieldsRecordMapper<>(0, 1, 3, "missing key"));
    }

    private static void showToPairMapper() {
        System.out.println("-showToPairMapper---");

        printRecordMapper("constructor", new ToTwoFieldsRecordMapper<>(0, 1));
        printRecordMapper("constructor (2, 0)", new ToTwoFieldsRecordMapper<>(2, 0));
    }

    private static void showToSingleMapper() {
        System.out.println("-showToSingleMapper---");

        printRecordMapper("constructor", new ToValueFieldRecordMapper<>(0));
        printRecordMapper("constructor (2)", new ToValueFieldRecordMapper<>(2));
    }

    private static void showToStandardMapper() {
        System.out.println("-showToStandardMapper---");

        printRecordMapper("constructor", new ToManyFieldsRecordMapper<>());
    }

    public static void main(String... args) {
        showToEmptyMapper();
        showToKeyValueMapper();
        showToKeyValueCommentMapper();
        showToPairMapper();
        showToSingleMapper();
        showToStandardMapper();
    }

}
