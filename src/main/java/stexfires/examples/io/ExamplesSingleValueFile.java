package stexfires.examples.io;

import stexfires.core.TextRecordStreams;
import stexfires.core.consumer.ConsumerException;
import stexfires.core.logger.SystemOutLogger;
import stexfires.core.producer.ProducerException;
import stexfires.core.record.SingleRecord;
import stexfires.io.RecordFiles;
import stexfires.io.RecordIOStreams;
import stexfires.io.singlevalue.SingleValueConsumer;
import stexfires.io.singlevalue.SingleValueFile;
import stexfires.io.singlevalue.SingleValueFileSpec;
import stexfires.io.singlevalue.SingleValueProducer;
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

    private static Stream<SingleRecord> generateStream() {
        return Stream.of(
                new SingleRecord("Start---"),

                new SingleRecord("A"),
                new SingleRecord(null),
                new SingleRecord("C"),
                new SingleRecord(""),
                new SingleRecord("    "),
                new SingleRecord("F"),
                new SingleRecord("//value1"),
                new SingleRecord("value2 value2 value2"),
                new SingleRecord(" value3 "),
                new SingleRecord("\t"),
                new SingleRecord("\tvalue4\t\tvalue4\t"),

                new SingleRecord(LineSeparator.LF.string()),
                new SingleRecord(LineSeparator.CR.string()),
                new SingleRecord(LineSeparator.CR_LF.string()),
                new SingleRecord(LineSeparator.LF.string() + "value11"),
                new SingleRecord("value12.1" + LineSeparator.LF.string() + "value12.2" + LineSeparator.CR.string() + "value12.3\fvalue12.4"),

                new SingleRecord("\\ =:#! \u000B \u0004 \u0015"),
                new SingleRecord("\""),
                new SingleRecord("<entry>"),
                new SingleRecord("\u20AC \u0178"),
                new SingleRecord("\u00A6 \u00BC \u00B4 \u00B8"),
                new SingleRecord("\u007E \u007F \u0080 \u009F \u00A0"),
                new SingleRecord("äÄáß@{[²µ^°1234567890ß\"§$%&/()?`+*'-_.,;<>|~"),

                new SingleRecord("End---")
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
        RecordFiles.writeFile(new SingleRecord("1. Test"), singleValueFile);
        RecordFiles.writeFile(new SingleRecord(""), singleValueFile, StandardOpenOption.APPEND);
        RecordFiles.writeFile(new SingleRecord(null), singleValueFile, StandardOpenOption.APPEND);
        RecordFiles.writeFile(new SingleRecord("4. Test"), singleValueFile, StandardOpenOption.APPEND);

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

    public static void main(String[] args) {
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
