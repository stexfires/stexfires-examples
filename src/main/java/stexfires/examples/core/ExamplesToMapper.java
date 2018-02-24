package stexfires.examples.core;

import stexfires.core.Record;
import stexfires.core.RecordStreams;
import stexfires.core.consumer.SystemOutConsumer;
import stexfires.core.mapper.RecordMapper;
import stexfires.core.mapper.to.ToEmptyMapper;
import stexfires.core.mapper.to.ToKeyValueMapper;
import stexfires.core.mapper.to.ToPairMapper;
import stexfires.core.mapper.to.ToSingleMapper;
import stexfires.core.mapper.to.ToStandardMapper;
import stexfires.core.record.EmptyRecord;
import stexfires.core.record.KeyValueRecord;
import stexfires.core.record.PairRecord;
import stexfires.core.record.SingleRecord;
import stexfires.core.record.StandardRecord;
import stexfires.util.Strings;

import java.util.stream.Stream;

public final class ExamplesToMapper {

    private ExamplesToMapper() {
    }

    private static Stream<Record> generateStream() {
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

    private static void showMapper(String title, RecordMapper<Record, ? extends Record> recordMapper) {
        System.out.println("--" + title);
        RecordStreams.mapAndConsume(generateStream(), recordMapper, new SystemOutConsumer<>());
    }

    private static void showToEmptyMapper() {
        System.out.println("-showToEmptyMapper---");

        showMapper("constructor", new ToEmptyMapper<>());
    }

    private static void showToKeyValueMapper() {
        System.out.println("-showToKeyValueMapper---");

        showMapper("constructor (0, 1, missing key)", new ToKeyValueMapper<>(0, 1, "missing key"));
    }

    private static void showToPairMapper() {
        System.out.println("-showToPairMapper---");

        showMapper("constructor", new ToPairMapper<>());
        showMapper("constructor (2, 0)", new ToPairMapper<>(2, 0));
    }

    private static void showToSingleMapper() {
        System.out.println("-showToSingleMapper---");

        showMapper("constructor", new ToSingleMapper<>());
        showMapper("constructor (2)", new ToSingleMapper<>(2));
    }

    private static void showToStandardMapper() {
        System.out.println("-showToStandardMapper---");

        showMapper("constructor", new ToStandardMapper<>());
    }

    public static void main(String[] args) {
        showToEmptyMapper();
        showToKeyValueMapper();
        showToPairMapper();
        showToSingleMapper();
        showToStandardMapper();
    }

}
