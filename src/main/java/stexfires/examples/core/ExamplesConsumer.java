package stexfires.examples.core;

import stexfires.core.RecordStreams;
import stexfires.core.TextRecord;
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
import stexfires.core.filter.CategoryFilter;
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
        RecordStreams.consume(generateStream(), recordConsumer);
    }

    private static void printConsumerSingleRecord(String title, RecordConsumer<? super SingleRecord> recordConsumer) {
        System.out.println("--" + title);
        RecordStreams.consume(generateStreamSingleRecord(), recordConsumer);
    }

    private static void printConsumerKeyValueRecord(String title, RecordConsumer<? super KeyValueRecord> recordConsumer) {
        System.out.println("--" + title);
        RecordStreams.consume(generateStreamKeyValueRecord(), recordConsumer);
    }

    @SuppressWarnings("resource")
    private static void showAppendableConsumer() {
        System.out.println("-showAppendableConsumer---");

        StringBuilder builder = new StringBuilder(10);
        printConsumer("constructor StringBuilder",
                new AppendableConsumer<>(builder, new SizeMessage<>()));
        System.out.println(builder.toString());

        StringBuffer buffer = new StringBuffer(10);
        printConsumer("constructor StringBuffer",
                new AppendableConsumer<>(buffer, new SizeMessage<>()));
        System.out.println(buffer.toString());

        StringWriter writer = new StringWriter(10);
        printConsumer("constructor StringWriter",
                new AppendableConsumer<>(writer, new SizeMessage<>()));
        System.out.println(writer.toString());
    }

    private static void showCollectionConsumer() {
        System.out.println("-showCollectionConsumer---");

        List<String> constructor = new ArrayList<>();
        printConsumer("constructor toString",
                new CollectionConsumer<>(constructor, TextRecord::toString));
        System.out.println(constructor);

        List<String> values = new ArrayList<>();
        printConsumerSingleRecord("constructor values",
                new CollectionConsumer<>(values, ValueRecord::getValueOfValueField));
        System.out.println(values);

        List<String> keys = new ArrayList<>();
        printConsumerKeyValueRecord("constructor keys",
                new CollectionConsumer<>(keys, KeyRecord::getValueOfKeyField));
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
                new MapConsumer<>(keyValueMap, KeyValueRecord::getValueOfKeyField, KeyValueRecord::getValueOfValueField));
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
        showSystemErrConsumer();
        showSystemOutConsumer();
        showWriterConsumer();
    }

}
