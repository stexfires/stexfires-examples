package stexfires.examples.core;

import stexfires.core.Record;
import stexfires.core.RecordStreams;
import stexfires.core.consumer.AppendableConsumer;
import stexfires.core.consumer.CollectionConsumer;
import stexfires.core.consumer.ConditionalConsumer;
import stexfires.core.consumer.DispatcherConsumer;
import stexfires.core.consumer.LoggerConsumer;
import stexfires.core.consumer.MapConsumer;
import stexfires.core.consumer.NullConsumer;
import stexfires.core.consumer.PrintStreamConsumer;
import stexfires.core.consumer.RecordConsumer;
import stexfires.core.consumer.StringWriterConsumer;
import stexfires.core.consumer.SystemErrConsumer;
import stexfires.core.consumer.SystemOutConsumer;
import stexfires.core.consumer.WriterConsumer;
import stexfires.core.filter.ClassFilter;
import stexfires.core.logger.SystemOutLogger;
import stexfires.core.message.SizeMessage;
import stexfires.core.record.EmptyRecord;
import stexfires.core.record.KeyRecord;
import stexfires.core.record.KeyValueRecord;
import stexfires.core.record.PairRecord;
import stexfires.core.record.SingleRecord;
import stexfires.core.record.StandardRecord;
import stexfires.core.record.ValueRecord;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


@SuppressWarnings("MagicNumber")
public final class ExamplesConsumer {

    private ExamplesConsumer() {
    }

    private static Stream<Record> generateStream() {
        return Stream.of(
                new SingleRecord("value1"),
                new SingleRecord("value2"),
                new KeyValueRecord("key", "value"),
                new StandardRecord("S", "t", "a", "n", "d", "a", "r", "d"),
                new StandardRecord(),
                new EmptyRecord()
        );
    }

    private static Stream<SingleRecord> generateStreamSingleRecord() {
        return Stream.of(
                new SingleRecord("value1"),
                new SingleRecord(null, 2L, "value2"),
                new SingleRecord("category", 3L, "value3")
        );
    }

    private static Stream<KeyValueRecord> generateStreamKeyValueRecord() {
        return Stream.of(
                new KeyValueRecord("key1", "value1"),
                new KeyValueRecord(null, 2L, "key2", "value2"),
                new KeyValueRecord("category", 3L, "key3", "value3")
        );
    }

    private static void showConsumer(String title, RecordConsumer<Record> recordConsumer) {
        System.out.println("--" + title);
        RecordStreams.consume(generateStream(), recordConsumer);
    }

    private static void showConsumerSingleRecord(String title, RecordConsumer<SingleRecord> recordConsumer) {
        System.out.println("--" + title);
        RecordStreams.consume(generateStreamSingleRecord(), recordConsumer);
    }

    private static void showConsumerKeyValueRecord(String title, RecordConsumer<KeyValueRecord> recordConsumer) {
        System.out.println("--" + title);
        RecordStreams.consume(generateStreamKeyValueRecord(), recordConsumer);
    }

    private static void showAppendableConsumer() {
        System.out.println("-showAppendableConsumer---");

        StringBuilder builder = new StringBuilder(10);
        showConsumer("constructor StringBuilder",
                new AppendableConsumer<>(builder, new SizeMessage<>()));
        System.out.println(builder.toString());

        StringBuffer buffer = new StringBuffer(10);
        showConsumer("constructor StringBuffer",
                new AppendableConsumer<>(buffer, new SizeMessage<>()));
        System.out.println(buffer.toString());

        try {
            try (StringWriter writer = new StringWriter(10)) {
                showConsumer("constructor StringWriter",
                        new AppendableConsumer<>(writer, new SizeMessage<>()));
                System.out.println(writer.toString());
            }
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        }
    }

    private static void showCollectionConsumer() {
        System.out.println("-showCollectionConsumer---");

        List<String> constructor = new ArrayList<>();
        showConsumer("constructor",
                new CollectionConsumer<>(constructor, Record::toString));
        System.out.println(constructor);

        List<String> values = new ArrayList<>();
        showConsumerSingleRecord("constructor values",
                new CollectionConsumer<>(values, ValueRecord::getValueOfValueField));
        System.out.println(values);

        List<String> keys = new ArrayList<>();
        showConsumerKeyValueRecord("constructor keys",
                new CollectionConsumer<>(keys, KeyRecord::getValueOfKeyField));
        System.out.println(keys);
    }

    private static void showConditionalConsumer() {
        System.out.println("-showConditionalConsumer---");

        showConsumer("constructor",
                new ConditionalConsumer<>(
                        ClassFilter.equalTo(SingleRecord.class),
                        new SystemOutConsumer<>(),
                        new NullConsumer<>()));
    }

    private static void showDispatcherConsumer() {
        System.out.println("-showDispatcherConsumer---");

        List<SystemOutConsumer<Record>> recordConsumersSize = new ArrayList<>();
        recordConsumersSize.add(new SystemOutConsumer<>("Size 0: "));
        recordConsumersSize.add(new SystemOutConsumer<>("Size 1: "));
        recordConsumersSize.add(new SystemOutConsumer<>("Size 2: "));

        showConsumer("bySize",
                DispatcherConsumer.bySize(recordConsumersSize));

        List<ClassFilter<Record>> recordFilters = new ArrayList<>();
        recordFilters.add(ClassFilter.equalTo(EmptyRecord.class));
        recordFilters.add(ClassFilter.equalTo(SingleRecord.class));
        recordFilters.add(ClassFilter.equalTo(PairRecord.class));

        List<RecordConsumer<? super Record>> recordConsumersFilter = new ArrayList<>();
        recordConsumersFilter.add(new SystemOutConsumer<>("Filter EmptyRecord: "));
        recordConsumersFilter.add(new SystemOutConsumer<>("Filter SingleRecord: "));
        recordConsumersFilter.add(new SystemOutConsumer<>("Filter PairRecord: "));

        showConsumer("byFilters",
                DispatcherConsumer.byFilters(recordFilters, recordConsumersFilter));
    }

    private static void showLoggerConsumer() {
        System.out.println("-showLoggerConsumer---");

        showConsumer("constructor",
                new LoggerConsumer<>(new SystemOutLogger<>()));
    }

    private static void showMapConsumer() {
        System.out.println("-showMapConsumer---");

        Map<String, String> keyValueMap = new HashMap<>();
        showConsumerKeyValueRecord("constructor",
                new MapConsumer<>(keyValueMap));
        System.out.println(keyValueMap);
    }

    private static void showNullConsumer() {
        System.out.println("-showNullConsumer---");

        showConsumer("constructor",
                new NullConsumer<>());
    }

    private static void showPrintStreamConsumer() {
        System.out.println("-showPrintStreamConsumer---");

        showConsumer("constructor false",
                new PrintStreamConsumer<>(System.out, new SizeMessage<>(), false));
        System.out.println();
        showConsumer("constructor true",
                new PrintStreamConsumer<>(System.out, new SizeMessage<>()));
    }

    private static void showRecordConsumer() {
        System.out.println("-showRecordConsumer---");

        showConsumer("concat 2",
                RecordConsumer.concat(new SystemOutConsumer<>(), new NullConsumer<>()));
        showConsumer("concat 3",
                RecordConsumer.concat(new SystemOutConsumer<>(), new NullConsumer<>(), new SystemOutConsumer<>()));
        showConsumer("andThen",
                new NullConsumer<>().andThen(new SystemOutConsumer<>()));
    }

    private static void showStringWriterConsumer() {
        System.out.println("-showStringWriterConsumer---");

        try (StringWriterConsumer<Record> consumer = new StringWriterConsumer<>(new SizeMessage<>())) {
            showConsumer("constructor",
                    consumer);
            System.out.println(consumer.getString());
        }
    }

    private static void showWriterConsumer() {
        System.out.println("-showWriterConsumer---");

        try {
            try (WriterConsumer<SingleRecord, StringWriter> consumer = new WriterConsumer<>(new StringWriter(), new SizeMessage<>())) {
                showConsumerSingleRecord("constructor",
                        consumer);
                System.out.println(consumer.getWriter());
            }
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        }
    }

    private static void showSystemOutConsumer() {
        System.out.println("-showSystemOutConsumer---");

        showConsumer("constructor prefix",
                new SystemOutConsumer<>("--"));
        showConsumer("constructor SizeMessage",
                new SystemOutConsumer<>(new SizeMessage<>()));
    }

    private static void showSystemErrConsumer() {
        System.out.println("-showSystemErrConsumer---");

        showConsumer("constructor prefix",
                new SystemErrConsumer<>("--"));
        showConsumer("constructor SizeMessage",
                new SystemErrConsumer<>(new SizeMessage<>()));
    }

    public static void main(String[] args) {
        showAppendableConsumer();
        showCollectionConsumer();
        showConditionalConsumer();
        showDispatcherConsumer();
        showLoggerConsumer();
        showMapConsumer();
        showNullConsumer();
        showPrintStreamConsumer();
        showRecordConsumer();
        showStringWriterConsumer();
        showWriterConsumer();
        showSystemOutConsumer();
        showSystemErrConsumer();
    }

}
