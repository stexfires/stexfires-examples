package stexfires.examples.io;

import stexfires.examples.record.RecordSystemOutUtil;
import stexfires.io.RecordFiles;
import stexfires.io.RecordIOStreams;
import stexfires.io.WritableRecordFileSpec;
import stexfires.io.singlevalue.SingleValueConsumer;
import stexfires.io.singlevalue.SingleValueFileSpec;
import stexfires.io.singlevalue.SingleValueProducer;
import stexfires.record.TextRecordStreams;
import stexfires.record.ValueRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.impl.KeyValueCommentFieldsRecord;
import stexfires.record.impl.KeyValueFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.producer.ProducerException;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

import static stexfires.util.CommonCharsetNames.ISO_8859_1;
import static stexfires.util.CommonCharsetNames.US_ASCII;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr"})
public final class ExamplesSingleValueFile {

    private ExamplesSingleValueFile() {
    }

    private static Stream<ValueRecord> generateStream() {
        return Stream.of(
                new ValueFieldRecord("Start---"),

                new ValueFieldRecord("A"),
                new ValueFieldRecord(null),
                new ValueFieldRecord("C"),
                new ValueFieldRecord(""),
                new ValueFieldRecord("    "),
                new ValueFieldRecord("F"),
                new ValueFieldRecord("//value1"),
                new ValueFieldRecord("value2 value2 value2"),
                new ValueFieldRecord(" value3 "),
                new ValueFieldRecord("\t"),
                new ValueFieldRecord("\tvalue4\t\tvalue4\t"),

                new ValueFieldRecord(LineSeparator.LF.string()),
                new ValueFieldRecord(LineSeparator.CR.string()),
                new ValueFieldRecord(LineSeparator.CR_LF.string()),
                new ValueFieldRecord(LineSeparator.LF.string() + "value11"),
                new ValueFieldRecord("value12.1" + LineSeparator.LF.string() + "value12.2" + LineSeparator.CR.string() + "value12.3\fvalue12.4"),

                new ValueFieldRecord("\\ =:#! \u000B \u0004 \u0015"),
                new ValueFieldRecord("\""),
                new ValueFieldRecord("<entry>"),
                new ValueFieldRecord("\u20AC \u0178"),
                new ValueFieldRecord("\u00A6 \u00BC \u00B4 \u00B8"),
                new ValueFieldRecord("\u007E \u007F \u0080 \u009F \u00A0"),
                new ValueFieldRecord("\uD83D\uDE00, o\u0308, A\u030a"),
                new ValueFieldRecord("äÄáß@{[²µ^°1234567890ß\"§$%&/()?`+*'-_.,;<>|~"),
                new KeyValueCommentFieldsRecord("Category", 0L, "Key", "Value", "Comment"),
                new ValueFieldRecord("End---")
        );
    }

    private static void test1(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test1---");

        var fileSpec =
                new SingleValueFileSpec(
                        CharsetCoding.reportingErrors(US_ASCII),
                        lineSeparator,
                        null,
                        null,
                        true,
                        0,
                        0,
                        false);

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeFile(new ValueFieldRecord("1. Test"), fileSpec, path);
        RecordFiles.writeFile(new ValueFieldRecord(""), fileSpec, path, StandardOpenOption.APPEND);
        RecordFiles.writeFile(new ValueFieldRecord(null), fileSpec, path, StandardOpenOption.APPEND);
        RecordFiles.writeFile(new ValueFieldRecord("4. Test"), fileSpec, path, StandardOpenOption.APPEND);
        RecordFiles.writeFile(new KeyValueFieldsRecord("Key", "5. Test"), fileSpec, path, StandardOpenOption.APPEND);
        RecordFiles.writeFile(new KeyValueCommentFieldsRecord("Key", "6. Test", "Comment"), fileSpec, path, StandardOpenOption.APPEND);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.readAndConsumeFile(fileSpec, RecordSystemOutUtil.RECORD_CONSUMER, path);
    }

    private static void test2(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test2---");

        var fileSpecWrite =
                SingleValueFileSpec.write(
                        CharsetCoding.replacingErrors(ISO_8859_1, "?", "?"),
                        lineSeparator,
                        WritableRecordFileSpec.DEFAULT_TEXT_BEFORE,
                        WritableRecordFileSpec.DEFAULT_TEXT_AFTER,
                        true);

        var fileSpecRead =
                SingleValueFileSpec.read(
                        CharsetCoding.replacingErrors(US_ASCII, "?", "?"),
                        false,
                        1,
                        1);

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeFile(generateStream(), fileSpecWrite, path);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.readAndConsumeFile(fileSpecRead, RecordSystemOutUtil.RECORD_CONSUMER, path);
    }

    private static void test3(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test3---");

        var fileSpec =
                new SingleValueFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        lineSeparator,
                        "Header---",
                        "Footer---",
                        false,
                        1,
                        1,
                        true);

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeFile(generateStream(), fileSpec, path);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.readAndConsumeFile(fileSpec, RecordSystemOutUtil.RECORD_CONSUMER, path);
    }

    private static void test4(Path path, LineSeparator lineSeparator) throws IOException {
        System.out.println("-test4---");

        var fileSpec =
                new SingleValueFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        lineSeparator,
                        WritableRecordFileSpec.DEFAULT_TEXT_BEFORE,
                        WritableRecordFileSpec.DEFAULT_TEXT_AFTER,
                        false,
                        0,
                        0,
                        false);

        // Write
        System.out.println("write: " + path);
        try (var singleValueConsumer = fileSpec.openFileAsConsumer(path)) {
            RecordIOStreams.writeStream(singleValueConsumer, generateStream());
        }

        // Read / log
        System.out.println("read/log: " + path);
        try (var singleValueProducer = fileSpec.openFileAsProducer(path)) {
            RecordIOStreams.readAndConsume(singleValueProducer, RecordSystemOutUtil.RECORD_CONSUMER);
        }
    }

    @SuppressWarnings("OverlyBroadThrowsClause")
    private static void test5(Path path, LineSeparator lineSeparator) throws IOException {
        System.out.println("-test5---");

        var fileSpec = new SingleValueFileSpec(
                CharsetCoding.UTF_8_REPORTING,
                lineSeparator,
                WritableRecordFileSpec.DEFAULT_TEXT_BEFORE,
                WritableRecordFileSpec.DEFAULT_TEXT_AFTER,
                false,
                0,
                0,
                false);

        // Write
        System.out.println("write: " + path);
        try (SingleValueConsumer singleValueConsumer = fileSpec.consumer(new FileOutputStream(path.toFile()))) {
            RecordIOStreams.writeStream(singleValueConsumer, TextRecordStreams.empty());
        }

        // Read / log
        System.out.println("read/log: " + path);
        try (SingleValueProducer singleValueProducer = fileSpec.producer(new FileInputStream(path.toFile()))) {
            RecordIOStreams.readAndConsume(singleValueProducer, RecordSystemOutUtil.RECORD_CONSUMER);
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
