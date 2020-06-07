package stexfires.examples.core;

import stexfires.core.RecordStreams;
import stexfires.core.TextRecord;
import stexfires.core.consumer.NullConsumer;
import stexfires.core.filter.CategoryFilter;
import stexfires.core.filter.ClassFilter;
import stexfires.core.logger.AppendableLogger;
import stexfires.core.logger.CollectionLogger;
import stexfires.core.logger.ConditionalLogger;
import stexfires.core.logger.DispatcherLogger;
import stexfires.core.logger.LimitedLogger;
import stexfires.core.logger.NullLogger;
import stexfires.core.logger.PrintStreamLogger;
import stexfires.core.logger.RecordLogger;
import stexfires.core.logger.SystemErrLogger;
import stexfires.core.logger.SystemOutLogger;
import stexfires.core.message.SizeMessage;
import stexfires.core.record.EmptyRecord;
import stexfires.core.record.KeyRecord;
import stexfires.core.record.KeyValueRecord;
import stexfires.core.record.PairRecord;
import stexfires.core.record.SingleRecord;
import stexfires.core.record.StandardRecord;
import stexfires.core.record.ValueRecord;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings({"MagicNumber", "UseOfSystemOutOrSystemErr"})
public final class ExamplesLogger {

    private ExamplesLogger() {
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

    private static void printLogger(String title, RecordLogger<TextRecord> recordLogger) {
        System.out.println("--" + title);
        RecordStreams.log(generateStream(), recordLogger)
                     .forEachOrdered(new NullConsumer<>().asConsumer());
    }

    private static void printLoggerSingleRecord(String title, RecordLogger<? super SingleRecord> recordLogger) {
        System.out.println("--" + title);
        RecordStreams.log(generateStreamSingleRecord(), recordLogger)
                     .forEachOrdered(new NullConsumer<>().asConsumer());
    }

    private static void printLoggerKeyValueRecord(String title, RecordLogger<? super KeyValueRecord> recordLogger) {
        System.out.println("--" + title);
        RecordStreams.log(generateStreamKeyValueRecord(), recordLogger)
                     .forEachOrdered(new NullConsumer<>().asConsumer());
    }

    @SuppressWarnings("resource")
    private static void showAppendableLogger() {
        System.out.println("-showAppendableLogger---");

        StringBuilder builder = new StringBuilder(10);
        printLogger("constructor StringBuilder",
                new AppendableLogger<>(builder, new SizeMessage<>()));
        System.out.println(builder.toString());

        StringBuffer buffer = new StringBuffer(10);
        printLogger("constructor StringBuffer",
                new AppendableLogger<>(buffer, new SizeMessage<>()));
        System.out.println(buffer.toString());

        StringWriter writer = new StringWriter(10);
        printLogger("constructor StringWriter",
                new AppendableLogger<>(writer, new SizeMessage<>()));
        System.out.println(writer.toString());
    }

    private static void showCollectionLogger() {
        System.out.println("-showCollectionLogger---");

        List<String> constructor = new ArrayList<>();
        printLogger("constructor",
                new CollectionLogger<>(constructor, TextRecord::toString));
        System.out.println(constructor);

        List<String> values = new ArrayList<>();
        printLoggerSingleRecord("constructor values",
                new CollectionLogger<>(values, ValueRecord::getValueOfValueField));
        System.out.println(values);

        List<String> keys = new ArrayList<>();
        printLoggerKeyValueRecord("constructor keys",
                new CollectionLogger<>(keys, KeyRecord::getValueOfKeyField));
        System.out.println(keys);
    }

    private static void showConditionalLogger() {
        System.out.println("-showConditionalLogger---");

        printLogger("constructor",
                new ConditionalLogger<>(
                        ClassFilter.equalTo(StandardRecord.class),
                        new SystemOutLogger<>(),
                        new NullLogger<>()));

        printLoggerKeyValueRecord("constructor category null",
                new ConditionalLogger<>(
                        CategoryFilter.isNull(),
                        new SystemOutLogger<>(),
                        new NullLogger<>()));
    }

    private static void showDispatcherLogger() {
        System.out.println("-showDispatcherLogger---");

        List<SystemOutLogger<TextRecord>> recordLoggersSize = new ArrayList<>();
        recordLoggersSize.add(new SystemOutLogger<>("Size 0: "));
        recordLoggersSize.add(new SystemOutLogger<>("Size 1: "));
        recordLoggersSize.add(new SystemOutLogger<>("Size 2: "));
        recordLoggersSize.add(new SystemOutLogger<>("Size 3: "));

        printLogger("bySize",
                DispatcherLogger.bySize(recordLoggersSize));

        printLoggerSingleRecord("bySize",
                DispatcherLogger.bySize(recordLoggersSize));

        printLoggerKeyValueRecord("bySize",
                DispatcherLogger.bySize(recordLoggersSize));

        List<ClassFilter<TextRecord>> recordFilters = new ArrayList<>();
        recordFilters.add(ClassFilter.equalTo(EmptyRecord.class));
        recordFilters.add(ClassFilter.equalTo(SingleRecord.class));
        recordFilters.add(ClassFilter.equalTo(PairRecord.class));

        List<RecordLogger<? super TextRecord>> recordLoggersFilter = new ArrayList<>();
        recordLoggersFilter.add(new SystemOutLogger<>("Filter EmptyRecord: "));
        recordLoggersFilter.add(new SystemOutLogger<>("Filter SingleRecord: "));
        recordLoggersFilter.add(new SystemOutLogger<>("Filter PairRecord: "));

        printLogger("byFilters",
                DispatcherLogger.byFilters(recordFilters, recordLoggersFilter));

        printLoggerSingleRecord("byFilters",
                DispatcherLogger.byFilters(recordFilters, recordLoggersFilter));

        printLoggerKeyValueRecord("byFilters",
                DispatcherLogger.byFilters(recordFilters, recordLoggersFilter));
    }

    private static void showLimitedLogger() {
        System.out.println("-showLimitedLogger---");

        printLogger("constructor 0",
                new LimitedLogger<>(new SystemOutLogger<>(), 0));
        printLogger("constructor 2",
                new LimitedLogger<>(new SystemOutLogger<>(), 2));
    }

    private static void showNullLogger() {
        System.out.println("-showNullLogger---");

        printLogger("constructor",
                new NullLogger<>());

        printLoggerKeyValueRecord("constructor",
                new NullLogger<>());
    }

    private static void showPrintStreamLogger() {
        System.out.println("-showPrintStreamLogger---");

        printLogger("constructor false",
                new PrintStreamLogger<>(System.out, new SizeMessage<>(), false));
        System.out.println();
        printLogger("constructor true",
                new PrintStreamLogger<>(System.out, new SizeMessage<>()));
    }

    private static void showRecordLogger() {
        System.out.println("-showRecordLogger---");

        printLogger("concat 2",
                RecordLogger.concat(new SystemOutLogger<>(), new NullLogger<>()));
        printLogger("concat 3",
                RecordLogger.concat(new SystemOutLogger<>(), new NullLogger<>(), new SystemOutLogger<>()));
        printLogger("andThen",
                new NullLogger<>().andThen(new SystemOutLogger<>()));
    }

    private static void showSystemErrLogger() {
        System.out.println("-showSystemErrLogger---");

        printLogger("constructor",
                new SystemErrLogger<>());
        printLogger("constructor prefix",
                new SystemErrLogger<>("--"));
        printLogger("constructor SizeMessage",
                new SystemErrLogger<>(new SizeMessage<>()));
        printLogger("constructor SizeMessage false",
                new SystemErrLogger<>(new SizeMessage<>(), false));
        System.err.println();
    }

    private static void showSystemOutLogger() {
        System.out.println("-showSystemOutLogger---");

        printLogger("constructor",
                new SystemOutLogger<>());
        printLogger("constructor prefix",
                new SystemOutLogger<>("--"));
        printLogger("constructor SizeMessage",
                new SystemOutLogger<>(new SizeMessage<>()));
        printLogger("constructor SizeMessage false",
                new SystemOutLogger<>(new SizeMessage<>(), false));
        System.out.println();
    }

    public static void main(String[] args) {
        showAppendableLogger();
        showCollectionLogger();
        showConditionalLogger();
        showDispatcherLogger();
        showLimitedLogger();
        showNullLogger();
        showPrintStreamLogger();
        showRecordLogger();
        showSystemErrLogger();
        showSystemOutLogger();
    }

}
