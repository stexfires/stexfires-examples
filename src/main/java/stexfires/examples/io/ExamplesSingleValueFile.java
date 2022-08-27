package stexfires.examples.io;

import stexfires.io.RecordFiles;
import stexfires.io.RecordIOStreams;
import stexfires.io.singlevalue.SingleValueConsumer;
import stexfires.io.singlevalue.SingleValueFile;
import stexfires.io.singlevalue.SingleValueFileSpec;
import stexfires.io.singlevalue.SingleValueProducer;
import stexfires.record.TextRecordStreams;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.impl.OneValueRecord;
import stexfires.record.logger.SystemOutLogger;
import stexfires.record.producer.ProducerException;
import stexfires.util.LineSeparator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

import static stexfires.util.CommonCharsetNames.ISO_8859_1;
import static stexfires.util.CommonCharsetNames.US_ASCII;
import static stexfires.util.CommonCharsetNames.UTF_8;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr"})
public final class ExamplesSingleValueFile {

    private ExamplesSingleValueFile() {
    }

    private static Stream<OneValueRecord> generateStream() {
        return Stream.of(
                new OneValueRecord("Start---"),

                new OneValueRecord("A"),
                new OneValueRecord(null),
                new OneValueRecord("C"),
                new OneValueRecord(""),
                new OneValueRecord("    "),
                new OneValueRecord("F"),
                new OneValueRecord("//value1"),
                new OneValueRecord("value2 value2 value2"),
                new OneValueRecord(" value3 "),
                new OneValueRecord("\t"),
                new OneValueRecord("\tvalue4\t\tvalue4\t"),

                new OneValueRecord(LineSeparator.LF.string()),
                new OneValueRecord(LineSeparator.CR.string()),
                new OneValueRecord(LineSeparator.CR_LF.string()),
                new OneValueRecord(LineSeparator.LF.string() + "value11"),
                new OneValueRecord("value12.1" + LineSeparator.LF.string() + "value12.2" + LineSeparator.CR.string() + "value12.3\fvalue12.4"),

                new OneValueRecord("\\ =:#! \u000B \u0004 \u0015"),
                new OneValueRecord("\""),
                new OneValueRecord("<entry>"),
                new OneValueRecord("\u20AC \u0178"),
                new OneValueRecord("\u00A6 \u00BC \u00B4 \u00B8"),
                new OneValueRecord("\u007E \u007F \u0080 \u009F \u00A0"),
                new OneValueRecord("äÄáß@{[²µ^°1234567890ß\"§$%&/()?`+*'-_.,;<>|~"),

                new OneValueRecord("End---")
        );
    }

    private static void test1(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test1---");

        SingleValueFile singleValueFile = new SingleValueFileSpec(
                US_ASCII.charset(), CodingErrorAction.REPORT,
                true, 0, 0,
                lineSeparator, false).file(path);

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeFile(new OneValueRecord("1. Test"), singleValueFile);
        RecordFiles.writeFile(new OneValueRecord(""), singleValueFile, StandardOpenOption.APPEND);
        RecordFiles.writeFile(new OneValueRecord(null), singleValueFile, StandardOpenOption.APPEND);
        RecordFiles.writeFile(new OneValueRecord("4. Test"), singleValueFile, StandardOpenOption.APPEND);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.logFile(singleValueFile, new SystemOutLogger<>());
    }

    private static void test2(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test2---");

        SingleValueFile singleValueFileWrite = SingleValueFileSpec
                .write(
                        ISO_8859_1.charset(),
                        CodingErrorAction.REPLACE,
                        lineSeparator,
                        true)
                .file(path);

        SingleValueFile singleValueFileRead = SingleValueFileSpec
                .read(
                        US_ASCII.charset(),
                        CodingErrorAction.REPLACE,
                        false,
                        1, 1)
                .file(path);

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeFile(generateStream(), singleValueFileWrite);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.logFile(singleValueFileRead, new SystemOutLogger<>());
    }

    private static void test3(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test3---");

        SingleValueFile singleValueFile = new SingleValueFileSpec(
                UTF_8.charset(), CodingErrorAction.REPORT,
                false, 0, 0,
                lineSeparator, false).file(path);

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeFile(generateStream(), singleValueFile);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.logFile(singleValueFile, new SystemOutLogger<>());
    }

    @SuppressWarnings("OverlyBroadThrowsClause")
    private static void test4(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test4---");

        SingleValueFileSpec singleValueFileSpec = new SingleValueFileSpec(
                UTF_8.charset(), CodingErrorAction.REPORT,
                false, 0, 0,
                lineSeparator, false);

        // Write
        System.out.println("write: " + path);
        try (SingleValueConsumer singleValueConsumer = singleValueFileSpec.consumer(new FileOutputStream(path.toFile()))) {
            RecordIOStreams.write(generateStream(), singleValueConsumer);
        }

        // Read / log
        System.out.println("read/log: " + path);
        try (SingleValueProducer singleValueProducer = singleValueFileSpec.producer(new FileInputStream(path.toFile()))) {
            RecordIOStreams.log(singleValueProducer, new SystemOutLogger<>());
        }
    }

    @SuppressWarnings("OverlyBroadThrowsClause")
    private static void test5(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test5---");

        SingleValueFileSpec singleValueFileSpec = new SingleValueFileSpec(
                UTF_8.charset(), CodingErrorAction.REPORT,
                false, 0, 0,
                lineSeparator, false);

        // Write
        System.out.println("write: " + path);
        try (SingleValueConsumer singleValueConsumer = singleValueFileSpec.consumer(new FileOutputStream(path.toFile()))) {
            RecordIOStreams.write(TextRecordStreams.empty(), singleValueConsumer);
        }

        // Read / log
        System.out.println("read/log: " + path);
        try (SingleValueProducer singleValueProducer = singleValueFileSpec.producer(new FileInputStream(path.toFile()))) {
            RecordIOStreams.log(singleValueProducer, new SystemOutLogger<>());
        }
    }

    public static void main(String... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Missing valid output directory parameter!");
        }
        File outputDirectory = new File(args[0]);
        if (!outputDirectory.exists() || !outputDirectory.isDirectory()) {
            throw new IllegalArgumentException("Missing valid output directory parameter! " + outputDirectory);
        }

        try {
            test1(Path.of(args[0], "SingleValueFile_1.txt"), LineSeparator.systemLineSeparator());
            test2(Path.of(args[0], "SingleValueFile_2.txt"), LineSeparator.systemLineSeparator());
            test3(Path.of(args[0], "SingleValueFile_3.txt"), LineSeparator.systemLineSeparator());
            test4(Path.of(args[0], "SingleValueFile_4.txt"), LineSeparator.systemLineSeparator());
            test5(Path.of(args[0], "SingleValueFile_5.txt"), LineSeparator.systemLineSeparator());
        } catch (ProducerException | ConsumerException | IOException e) {
            e.printStackTrace();
        }
    }

}
