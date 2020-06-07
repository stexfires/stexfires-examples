package stexfires.examples.core;

import stexfires.core.RecordStreams;
import stexfires.core.TextRecord;
import stexfires.core.consumer.SystemOutConsumer;
import stexfires.core.mapper.RecordMapper;
import stexfires.core.mapper.ToEmptyMapper;
import stexfires.core.mapper.ToKeyValueMapper;
import stexfires.core.mapper.ToPairMapper;
import stexfires.core.mapper.ToSingleMapper;
import stexfires.core.mapper.ToStandardMapper;
import stexfires.core.record.EmptyRecord;
import stexfires.core.record.KeyValueRecord;
import stexfires.core.record.PairRecord;
import stexfires.core.record.SingleRecord;
import stexfires.core.record.StandardRecord;
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
                new PairRecord("first", "second"),
                new PairRecord("category", 0L, "first", "second"),
                new SingleRecord("value"),
                new SingleRecord("category", 0L, "value"),
                new StandardRecord(),
                new StandardRecord(Strings.list("value1", "value2", "value3")),
                new StandardRecord("category", Strings.list("value1", "value2", "value3")),
                new StandardRecord("category", 0L, Strings.list("value1", "value2", "value3"))
        );
    }

    private static void printRecordMapper(String title, RecordMapper<TextRecord, ? extends TextRecord> recordMapper) {
        System.out.println("--" + title);
        RecordStreams.mapAndConsume(generateStream(), recordMapper, new SystemOutConsumer<>());
    }

    private static void showToEmptyMapper() {
        System.out.println("-showToEmptyMapper---");

        printRecordMapper("constructor", new ToEmptyMapper<>());
    }

    private static void showToKeyValueMapper() {
        System.out.println("-showToKeyValueMapper---");

        printRecordMapper("constructor (0, 1, missing key)", new ToKeyValueMapper<>(0, 1, "missing key"));
    }

    private static void showToPairMapper() {
        System.out.println("-showToPairMapper---");

        printRecordMapper("constructor", new ToPairMapper<>());
        printRecordMapper("constructor (2, 0)", new ToPairMapper<>(2, 0));
    }

    private static void showToSingleMapper() {
        System.out.println("-showToSingleMapper---");

        printRecordMapper("constructor", new ToSingleMapper<>());
        printRecordMapper("constructor (2)", new ToSingleMapper<>(2));
    }

    private static void showToStandardMapper() {
        System.out.println("-showToStandardMapper---");

        printRecordMapper("constructor", new ToStandardMapper<>());
    }

    public static void main(String[] args) {
        showToEmptyMapper();
        showToKeyValueMapper();
        showToPairMapper();
        showToSingleMapper();
        showToStandardMapper();
    }

}
