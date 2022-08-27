package stexfires.examples.record;

import stexfires.record.TextRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.consumer.SystemOutConsumer;
import stexfires.record.impl.EmptyRecord;
import stexfires.record.impl.KeyValueRecord;
import stexfires.record.impl.PairRecord;
import stexfires.record.impl.SingleRecord;
import stexfires.record.impl.StandardRecord;
import stexfires.record.mapper.RecordMapper;
import stexfires.record.mapper.ToEmptyMapper;
import stexfires.record.mapper.ToKeyValueMapper;
import stexfires.record.mapper.ToPairMapper;
import stexfires.record.mapper.ToSingleMapper;
import stexfires.record.mapper.ToStandardMapper;
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
        TextRecordStreams.mapAndConsume(generateStream(), recordMapper, new SystemOutConsumer<>());
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

    public static void main(String... args) {
        showToEmptyMapper();
        showToKeyValueMapper();
        showToPairMapper();
        showToSingleMapper();
        showToStandardMapper();
    }

}
