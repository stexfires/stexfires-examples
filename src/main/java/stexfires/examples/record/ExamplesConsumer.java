package stexfires.examples.record;

import stexfires.record.KeyRecord;
import stexfires.record.TextRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.ValueRecord;
import stexfires.record.consumer.AppendableConsumer;
import stexfires.record.consumer.CollectionConsumer;
import stexfires.record.consumer.ConditionalConsumer;
import stexfires.record.consumer.DispatcherConsumer;
import stexfires.record.consumer.LoggerConsumer;
import stexfires.record.consumer.MapConsumer;
import stexfires.record.consumer.NullConsumer;
import stexfires.record.consumer.PrintStreamConsumer;
import stexfires.record.consumer.RecordConsumer;
import stexfires.record.consumer.StringWriterConsumer;
import stexfires.record.consumer.SystemErrConsumer;
import stexfires.record.consumer.SystemOutConsumer;
import stexfires.record.consumer.WriterConsumer;
import stexfires.record.filter.CategoryFilter;
import stexfires.record.filter.ClassFilter;
import stexfires.record.impl.EmptyRecord;
import stexfires.record.impl.KeyValueRecord;
import stexfires.record.impl.PairRecord;
import stexfires.record.impl.SingleRecord;
import stexfires.record.impl.StandardRecord;
import stexfires.record.logger.SystemOutLogger;
import stexfires.record.message.SizeMessage;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesConsumer {

    private ExamplesConsumer() {
    }

    private static Stream<TextRecord> generateStream() {
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

    private static void printConsumer(String title, RecordConsumer<TextRecord> recordConsumer) {
        System.out.println("--" + title);
        TextRecordStreams.consume(generateStream(), recordConsumer);
    }

    private static void printConsumerSingleRecord(String title, RecordConsumer<? super SingleRecord> recordConsumer) {
        System.out.println("--" + title);
        TextRecordStreams.consume(generateStreamSingleRecord(), recordConsumer);
    }

    private static void printConsumerKeyValueRecord(String title, RecordConsumer<? super KeyValueRecord> recordConsumer) {
        System.out.println("--" + title);
        TextRecordStreams.consume(generateStreamKeyValueRecord(), recordConsumer);
    }

    private static void showAppendableConsumer() {
        System.out.println("-showAppendableConsumer---");

        StringBuilder builder = new StringBuilder(10);
        printConsumer("constructor StringBuilder",
                new AppendableConsumer<>(builder, new SizeMessage<>()));
        System.out.println(builder);

        StringBuffer buffer = new StringBuffer(10);
        printConsumer("constructor StringBuffer",
                new AppendableConsumer<>(buffer, new SizeMessage<>()));
        System.out.println(buffer);

        StringWriter writer = new StringWriter(10);
        printConsumer("constructor StringWriter",
                new AppendableConsumer<>(writer, new SizeMessage<>()));
        System.out.println(writer);
    }

    private static void showCollectionConsumer() {
        System.out.println("-showCollectionConsumer---");

        List<String> constructor = new ArrayList<>();
        printConsumer("constructor toString",
                new CollectionConsumer<>(constructor, TextRecord::toString));
        System.out.println(constructor);

        List<String> values = new ArrayList<>();
        printConsumerSingleRecord("constructor values",
                new CollectionConsumer<>(values, ValueRecord::valueOfValueField));
        System.out.println(values);

        List<String> keys = new ArrayList<>();
        printConsumerKeyValueRecord("constructor keys",
                new CollectionConsumer<>(keys, KeyRecord::valueOfKeyField));
        System.out.println(keys);
    }

    private static void showConditionalConsumer() {
        System.out.println("-showConditionalConsumer---");

        printConsumer("constructor SingleRecord",
                new ConditionalConsumer<>(
                        ClassFilter.equalTo(SingleRecord.class),
                        new SystemOutConsumer<>(),
                        new NullConsumer<>()));

        printConsumerKeyValueRecord("constructor category null",
                new ConditionalConsumer<>(
                        CategoryFilter.isNull(),
                        new SystemOutConsumer<>(),
                        new NullConsumer<>()));
    }

    private static void showDispatcherConsumer() {
        System.out.println("-showDispatcherConsumer---");

        List<SystemOutConsumer<TextRecord>> recordConsumersSize = new ArrayList<>();
        recordConsumersSize.add(new SystemOutConsumer<>("Size 0: "));
        recordConsumersSize.add(new SystemOutConsumer<>("Size 1: "));
        recordConsumersSize.add(new SystemOutConsumer<>("Size 2: "));
        recordConsumersSize.add(new SystemOutConsumer<>("Size 3: "));

        printConsumer("bySize",
                DispatcherConsumer.bySize(recordConsumersSize));

        printConsumerSingleRecord("bySize",
                DispatcherConsumer.bySize(recordConsumersSize));

        printConsumerKeyValueRecord("bySize",
                DispatcherConsumer.bySize(recordConsumersSize));

        List<ClassFilter<TextRecord>> recordFilters = new ArrayList<>();
        recordFilters.add(ClassFilter.equalTo(EmptyRecord.class));
        recordFilters.add(ClassFilter.equalTo(SingleRecord.class));
        recordFilters.add(ClassFilter.equalTo(PairRecord.class));

        List<RecordConsumer<? super TextRecord>> recordConsumersFilter = new ArrayList<>();
        recordConsumersFilter.add(new SystemOutConsumer<>("Filter EmptyRecord:  "));
        recordConsumersFilter.add(new SystemOutConsumer<>("Filter SingleRecord: "));
        recordConsumersFilter.add(new SystemOutConsumer<>("Filter PairRecord:   "));

        printConsumer("byFilters",
                DispatcherConsumer.byFilters(recordFilters, recordConsumersFilter));

        printConsumerSingleRecord("byFilters",
                DispatcherConsumer.byFilters(recordFilters, recordConsumersFilter));

        printConsumerKeyValueRecord("byFilters",
                DispatcherConsumer.byFilters(recordFilters, recordConsumersFilter));
    }

    private static void showLoggerConsumer() {
        System.out.println("-showLoggerConsumer---");

        printConsumer("constructor",
                new LoggerConsumer<>(new SystemOutLogger<>()));

        printConsumerKeyValueRecord("constructor",
                new LoggerConsumer<>(new SystemOutLogger<>()));
    }

    private static void showMapConsumer() {
        System.out.println("-showMapConsumer---");

        Map<String, String> keyValueMap = new HashMap<>();
        printConsumerKeyValueRecord("constructor",
                new MapConsumer<>(keyValueMap, KeyValueRecord::valueOfKeyField, KeyValueRecord::valueOfValueField));
        System.out.println(keyValueMap);
    }

    private static void showNullConsumer() {
        System.out.println("-showNullConsumer---");

        printConsumer("constructor",
                new NullConsumer<>());

        printConsumerKeyValueRecord("constructor",
                new NullConsumer<>());
    }

    private static void showPrintStreamConsumer() {
        System.out.println("-showPrintStreamConsumer---");

        printConsumer("constructor false",
                new PrintStreamConsumer<>(System.out, new SizeMessage<>(), false));
        System.out.println();
        printConsumer("constructor true",
                new PrintStreamConsumer<>(System.out, new SizeMessage<>()));
    }

    private static void showRecordConsumer() {
        System.out.println("-showRecordConsumer---");

        printConsumer("concat 2",
                RecordConsumer.concat(new SystemOutConsumer<>(), new NullConsumer<>()));
        printConsumer("concat 3",
                RecordConsumer.concat(new SystemOutConsumer<>(), new NullConsumer<>(), new SystemOutConsumer<>()));
        printConsumer("andThen",
                new NullConsumer<>().andThen(new SystemOutConsumer<>()));
    }

    private static void showStringWriterConsumer() {
        System.out.println("-showStringWriterConsumer---");

        StringWriterConsumer<TextRecord> consumer = new StringWriterConsumer<>(new SizeMessage<>());
        printConsumer("constructor",
                consumer);
        System.out.println(consumer.getString());
    }

    private static void showSystemErrConsumer() {
        System.out.println("-showSystemErrConsumer---");

        printConsumer("constructor",
                new SystemErrConsumer<>());
        printConsumer("constructor prefix",
                new SystemErrConsumer<>("--"));
        printConsumer("constructor SizeMessage",
                new SystemErrConsumer<>(new SizeMessage<>()));
        printConsumer("constructor SizeMessage false",
                new SystemErrConsumer<>(new SizeMessage<>(), false));
        System.err.println();
    }

    private static void showSystemOutConsumer() {
        System.out.println("-showSystemOutConsumer---");

        printConsumer("constructor",
                new SystemOutConsumer<>());
        printConsumer("constructor prefix",
                new SystemOutConsumer<>("--"));
        printConsumer("constructor SizeMessage",
                new SystemOutConsumer<>(new SizeMessage<>()));
        printConsumer("constructor SizeMessage false",
                new SystemOutConsumer<>(new SizeMessage<>(), false));
        System.out.println();
    }

    private static void showWriterConsumer() {
        System.out.println("-showWriterConsumer---");

        try {
            try (WriterConsumer<SingleRecord, StringWriter> consumer = new WriterConsumer<>(new StringWriter(), new SizeMessage<>())) {
                printConsumerSingleRecord("constructor",
                        consumer);
                System.out.println(consumer.getWriter());
            }
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        }
    }

    public static void main(String... args) {
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
        showSystemErrConsumer();
        showSystemOutConsumer();
        showWriterConsumer();
    }

}
