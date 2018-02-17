package stexfires.examples.core;

import stexfires.core.Record;
import stexfires.core.RecordStreams;
import stexfires.core.consumer.LoggerConsumer;
import stexfires.core.filter.ClassFilter;
import stexfires.core.filter.RecordFilter;
import stexfires.core.logger.AppendableLogger;
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
import stexfires.core.record.KeyValueRecord;
import stexfires.core.record.PairRecord;
import stexfires.core.record.SingleRecord;
import stexfires.core.record.StandardRecord;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class ExamplesLogger {

    private ExamplesLogger() {
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

    private static void showAppendableLogger() {
        System.out.println("-showAppendableLogger---");

        System.out.println("constructor StringBuilder");
        StringBuilder builder = new StringBuilder(10);
        RecordStreams.consume(generateStream(), new LoggerConsumer<>(
                new AppendableLogger<>(builder, new SizeMessage<>())));
        System.out.println(builder.toString());

        System.out.println("constructor StringWriter");
        try {
            try (StringWriter writer = new StringWriter(10)) {
                RecordStreams.consume(generateStream(), new LoggerConsumer<>(
                        new AppendableLogger<>(writer, new SizeMessage<>())));
                System.out.println(writer.toString());
            }
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        }
    }

    private static void showConditionalLogger() {
        System.out.println("-showConditionalLogger---");

        RecordStreams.consume(generateStream(), new LoggerConsumer<>(
                new ConditionalLogger<>(ClassFilter.equalTo(StandardRecord.class),
                        new SystemOutLogger<>(), new NullLogger<>())));
    }

    private static void showDispatcherLogger() {
        System.out.println("-showDispatcherLogger---");

        System.out.println("-bySize---");
        List<RecordLogger<? super Record>> recordLoggersSize = new ArrayList<>();
        recordLoggersSize.add(new SystemOutLogger<>("Size 0: "));
        recordLoggersSize.add(new SystemOutLogger<>("Size 1: "));
        recordLoggersSize.add(new SystemOutLogger<>("Size 2: "));
        RecordStreams.consume(generateStream(), new LoggerConsumer<>(
                DispatcherLogger.bySize(recordLoggersSize)));

        System.out.println("-byFilters---");
        List<RecordFilter<? super Record>> recordFilters = new ArrayList<>();
        recordFilters.add(ClassFilter.equalTo(EmptyRecord.class));
        recordFilters.add(ClassFilter.equalTo(SingleRecord.class));
        recordFilters.add(ClassFilter.equalTo(PairRecord.class));
        List<RecordLogger<? super Record>> recordLoggersFilter = new ArrayList<>();
        recordLoggersFilter.add(new SystemOutLogger<>("Filter EmptyRecord: "));
        recordLoggersFilter.add(new SystemOutLogger<>("Filter SingleRecord: "));
        recordLoggersFilter.add(new SystemOutLogger<>("Filter PairRecord: "));
        RecordStreams.consume(generateStream(), new LoggerConsumer<>(
                DispatcherLogger.byFilters(recordFilters, recordLoggersFilter)));
    }

    private static void showLimitedLogger() {
        System.out.println("-showLimitedLogger---");

        RecordStreams.consume(generateStream(), new LoggerConsumer<>(
                new LimitedLogger<>(new SystemOutLogger<>(), 2)));
    }

    private static void showNullLogger() {
        System.out.println("-showNullLogger---");

        RecordStreams.consume(generateStream(), new LoggerConsumer<>(
                new NullLogger<>()));
    }

    private static void showPrintStreamLogger() {
        System.out.println("-showPrintStreamLogger---");

        RecordStreams.consume(generateStream(), new LoggerConsumer<>(
                new PrintStreamLogger<>(System.out, new SizeMessage<>())));
    }

    private static void showRecordLogger() {
        System.out.println("-showRecordLogger---");

        System.out.println("concat 2");
        RecordStreams.consume(generateStream(), new LoggerConsumer<>(
                RecordLogger.concat(new SystemOutLogger<>(), new NullLogger<>())));

        System.out.println("concat 3");
        RecordStreams.consume(generateStream(), new LoggerConsumer<>(
                RecordLogger.concat(new SystemOutLogger<>(), new NullLogger<>(), new SystemOutLogger<>())));

        System.out.println("andThen");
        RecordStreams.consume(generateStream(), new LoggerConsumer<>(
                new NullLogger<>().andThen(new SystemOutLogger<>())));
    }

    private static void showSystemOutLogger() {
        System.out.println("-showSystemOutLogger---");

        System.out.println("constructor prefix");
        RecordStreams.consume(generateStream(), new LoggerConsumer<>(
                new SystemOutLogger<>("--")));

        System.out.println("constructor SizeMessage");
        RecordStreams.consume(generateStream(), new LoggerConsumer<>(
                new SystemOutLogger<>(new SizeMessage<>())));
    }

    private static void showSystemErrLogger() {
        System.out.println("-showSystemErrLogger---");

        System.out.println("constructor prefix");
        RecordStreams.consume(generateStream(), new LoggerConsumer<>(
                new SystemErrLogger<>("--")));

        System.out.println("constructor SizeMessage");
        RecordStreams.consume(generateStream(), new LoggerConsumer<>(
                new SystemErrLogger<>(new SizeMessage<>())));
    }

    public static void main(String[] args) {
        showAppendableLogger();
        showConditionalLogger();
        showDispatcherLogger();
        showLimitedLogger();
        showNullLogger();
        showPrintStreamLogger();
        showRecordLogger();
        showSystemOutLogger();
        showSystemErrLogger();
    }

}
