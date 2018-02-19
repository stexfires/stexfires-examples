package stexfires.examples.core;

import stexfires.core.Record;
import stexfires.core.RecordStreams;
import stexfires.core.consumer.NullConsumer;
import stexfires.core.filter.ClassFilter;
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

    private static void showLogger(String title, RecordLogger<Record> recordLogger) {
        System.out.println("--" + title);
        RecordStreams.log(generateStream(), recordLogger)
                     .forEachOrdered(new NullConsumer<>().asConsumer());
    }

    private static void showAppendableLogger() {
        System.out.println("-showAppendableLogger---");

        StringBuilder builder = new StringBuilder(10);
        showLogger("constructor StringBuilder",
                new AppendableLogger<>(builder, new SizeMessage<>()));
        System.out.println(builder.toString());

        try {
            try (StringWriter writer = new StringWriter(10)) {
                showLogger("constructor StringWriter",
                        new AppendableLogger<>(writer, new SizeMessage<>()));
                System.out.println(writer.toString());
            }
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        }
    }

    private static void showConditionalLogger() {
        System.out.println("-showConditionalLogger---");

        showLogger("constructor",
                new ConditionalLogger<>(
                        ClassFilter.equalTo(StandardRecord.class),
                        new SystemOutLogger<>(),
                        new NullLogger<>()));
    }

    private static void showDispatcherLogger() {
        System.out.println("-showDispatcherLogger---");

        List<SystemOutLogger<Record>> recordLoggersSize = new ArrayList<>();
        recordLoggersSize.add(new SystemOutLogger<>("Size 0: "));
        recordLoggersSize.add(new SystemOutLogger<>("Size 1: "));
        recordLoggersSize.add(new SystemOutLogger<>("Size 2: "));

        showLogger("bySize",
                DispatcherLogger.bySize(recordLoggersSize));

        List<ClassFilter<Record>> recordFilters = new ArrayList<>();
        recordFilters.add(ClassFilter.equalTo(EmptyRecord.class));
        recordFilters.add(ClassFilter.equalTo(SingleRecord.class));
        recordFilters.add(ClassFilter.equalTo(PairRecord.class));

        List<RecordLogger<? super Record>> recordLoggersFilter = new ArrayList<>();
        recordLoggersFilter.add(new SystemOutLogger<>("Filter EmptyRecord: "));
        recordLoggersFilter.add(new SystemOutLogger<>("Filter SingleRecord: "));
        recordLoggersFilter.add(new SystemOutLogger<>("Filter PairRecord: "));

        showLogger("byFilters",
                DispatcherLogger.byFilters(recordFilters, recordLoggersFilter));
    }

    private static void showLimitedLogger() {
        System.out.println("-showLimitedLogger---");

        showLogger("constructor 0",
                new LimitedLogger<>(new SystemOutLogger<>(), 0));
        showLogger("constructor 2",
                new LimitedLogger<>(new SystemOutLogger<>(), 2));
    }

    private static void showNullLogger() {
        System.out.println("-showNullLogger---");

        showLogger("constructor",
                new NullLogger<>());
    }

    private static void showPrintStreamLogger() {
        System.out.println("-showPrintStreamLogger---");

        showLogger("constructor",
                new PrintStreamLogger<>(System.out, new SizeMessage<>()));
    }

    private static void showRecordLogger() {
        System.out.println("-showRecordLogger---");

        showLogger("concat 2\"",
                RecordLogger.concat(new SystemOutLogger<>(), new NullLogger<>()));
        showLogger("concat 3",
                RecordLogger.concat(new SystemOutLogger<>(), new NullLogger<>(), new SystemOutLogger<>()));
        showLogger("andThen",
                new NullLogger<>().andThen(new SystemOutLogger<>()));
    }

    private static void showSystemOutLogger() {
        System.out.println("-showSystemOutLogger---");

        showLogger("constructor prefix",
                new SystemOutLogger<>("--"));
        showLogger("constructor SizeMessage",
                new SystemOutLogger<>(new SizeMessage<>()));
    }

    private static void showSystemErrLogger() {
        System.out.println("-showSystemErrLogger---");

        showLogger("constructor prefix",
                new SystemErrLogger<>("--"));
        showLogger("constructor SizeMessage",
                new SystemErrLogger<>(new SizeMessage<>()));
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
