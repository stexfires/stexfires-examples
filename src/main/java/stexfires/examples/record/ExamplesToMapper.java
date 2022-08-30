package stexfires.examples.record;

import stexfires.record.TextRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.consumer.SystemOutConsumer;
import stexfires.record.impl.EmptyRecord;
import stexfires.record.impl.KeyValueCommentRecord;
import stexfires.record.impl.KeyValueRecord;
import stexfires.record.impl.ManyValuesRecord;
import stexfires.record.impl.OneValueRecord;
import stexfires.record.impl.TwoValuesRecord;
import stexfires.record.mapper.RecordMapper;
import stexfires.record.mapper.ToEmptyRecordMapper;
import stexfires.record.mapper.ToKeyValueCommentRecordMapper;
import stexfires.record.mapper.ToKeyValueRecordMapper;
import stexfires.record.mapper.ToManyValuesRecordMapper;
import stexfires.record.mapper.ToOneValueRecordMapper;
import stexfires.record.mapper.ToTwoValuesRecordMapper;
import stexfires.util.Strings;

import java.util.stream.Stream;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public final class ExamplesToMapper {

    private ExamplesToMapper() {
    }

    private static Stream<TextRecord> generateStream() {
        return Stream.of(
                new EmptyRecord(),
                new KeyValueRecord("key", "value"),
                new KeyValueRecord("category", 0L, "key", "value"),
                new KeyValueCommentRecord("category", 0L, "key", "value", "comment"),
                new TwoValuesRecord("first", "second"),
                new TwoValuesRecord("category", 0L, "first", "second"),
                new OneValueRecord("value"),
                new OneValueRecord("category", 0L, "value"),
                new ManyValuesRecord(),
                new ManyValuesRecord(Strings.list("value1", "value2", "value3")),
                new ManyValuesRecord("category", Strings.list("value1", "value2", "value3")),
                new ManyValuesRecord("category", 0L, Strings.list("value1", "value2", "value3")),
                new ManyValuesRecord("category", 0L, Strings.list("value1", "value2", "value3", "value4"))
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

        printRecordMapper("constructor (0, 1, missing key)", new ToKeyValueRecordMapper<>(0, 1, "missing key"));
    }

    private static void showToKeyValueCommentMapper() {
        System.out.println("-showToKeyValueCommentMapper---");

        printRecordMapper("constructor (0, 1, missing key)", new ToKeyValueCommentRecordMapper<>(0, 1, 3, "missing key"));
    }

    private static void showToPairMapper() {
        System.out.println("-showToPairMapper---");

        printRecordMapper("constructor", new ToTwoValuesRecordMapper<>());
        printRecordMapper("constructor (2, 0)", new ToTwoValuesRecordMapper<>(2, 0));
    }

    private static void showToSingleMapper() {
        System.out.println("-showToSingleMapper---");

        printRecordMapper("constructor", new ToOneValueRecordMapper<>());
        printRecordMapper("constructor (2)", new ToOneValueRecordMapper<>(2));
    }

    private static void showToStandardMapper() {
        System.out.println("-showToStandardMapper---");

        printRecordMapper("constructor", new ToManyValuesRecordMapper<>());
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
