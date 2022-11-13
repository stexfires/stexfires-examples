package stexfires.examples.io;

import stexfires.io.RecordFiles;
import stexfires.io.fixedwidth.FixedWidthFieldSpec;
import stexfires.io.fixedwidth.FixedWidthFileSpec;
import stexfires.record.TextRecord;
import stexfires.record.TextRecordStreams;
import stexfires.record.ValueRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.logger.SystemOutLogger;
import stexfires.record.message.JoinedTextsMessage;
import stexfires.record.message.ShortMessage;
import stexfires.record.producer.ProducerException;
import stexfires.util.Alignment;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr", "MagicNumber"})
public final class ExamplesFixedWidthFile {

    private ExamplesFixedWidthFile() {
    }

    private static Stream<TextRecord> generateStream1() {
        return Stream.of(
                new ManyFieldsRecord(null, -1L, "######", "######", "######", "######"),
                new ManyFieldsRecord(null, 0L, "a", "1", "AAAA", "ä", "zu viele Werte"),
                new ManyFieldsRecord(null, 1L, "b", "2", "BB", "€µ"),
                new ManyFieldsRecord(null, 2L, "c", "3", "C", ""),
                new ManyFieldsRecord(null, 3L, "d", "4", "dDDDDd", null),
                new ManyFieldsRecord(null, 4L, "e", "05", "eEEEEEEEEEEEEEEEEEe"),
                new ManyFieldsRecord(null, 5L),
                new ManyFieldsRecord(null, 6L, null, null, null, null),
                new ManyFieldsRecord(null, 7L, "", "", "", ""),
                new ManyFieldsRecord(null, 8L, " ", " ", " ", " "),
                new ManyFieldsRecord(null, 9L, ".", ".", ".", "."),
                new ManyFieldsRecord(null, 10L, "-", "-", "-", "-"),
                new ManyFieldsRecord(null, 11L, "_", "_", "_", "_")
        );
    }

    private static Stream<ValueRecord> generateStream2() {
        return Stream.of(
                new ValueFieldRecord("abcdefghijklmnopqrstuvwxyz"),
                new ValueFieldRecord(""),
                new ValueFieldRecord(" "),
                new ValueFieldRecord("                                                      "),
                new ValueFieldRecord("_"),
                new ValueFieldRecord("______________________________________________________"),
                new ValueFieldRecord("."),
                new ValueFieldRecord("......................................................"),
                new ValueFieldRecord("-"),
                new ValueFieldRecord("------------------------------------------------------"),
                new ValueFieldRecord("_...------_____##___")
        );
    }

    private static void test1(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test1---");

        List<FixedWidthFieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(new FixedWidthFieldSpec(0, 1, null, null));
        fieldSpecs.add(new FixedWidthFieldSpec(1, 3, Alignment.END, '.'));
        fieldSpecs.add(new FixedWidthFieldSpec(4, 6, Alignment.CENTER, '-'));
        fieldSpecs.add(new FixedWidthFieldSpec(15, 2, null, '#'));
        var fixedWidthFile =
                new FixedWidthFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        lineSeparator,
                        20,
                        true,
                        Alignment.START,
                        '_',
                        fieldSpecs,
                        1,
                        0,
                        true,
                        true)
                        .file(path);

        List<FixedWidthFieldSpec> fieldSpecsAppend1 = new ArrayList<>();
        fieldSpecsAppend1.add(new FixedWidthFieldSpec(0, 26, null, null));
        var fixedWidthFileAppend1 =
                FixedWidthFileSpec.write(
                                          CharsetCoding.UTF_8_REPORTING,
                                          lineSeparator,
                                          30,
                                          true,
                                          Alignment.START,
                                          ' ',
                                          fieldSpecsAppend1)
                                  .file(path);

        List<FixedWidthFieldSpec> fieldSpecsAppend2 = new ArrayList<>();
        fieldSpecsAppend2.add(new FixedWidthFieldSpec(0, 1, null, null));
        var fixedWidthFileAppend2 =
                FixedWidthFileSpec.write(
                                          CharsetCoding.UTF_8_REPORTING,
                                          lineSeparator,
                                          1,
                                          true,
                                          Alignment.START,
                                          '_',
                                          fieldSpecsAppend2)
                                  .file(path);

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeFile(generateStream1(), fixedWidthFile);

        // Write
        System.out.println("write APPEND: " + path);
        RecordFiles.writeFile(generateStream2(), fixedWidthFileAppend1, StandardOpenOption.APPEND);

        // Write
        System.out.println("write APPEND: " + path);
        RecordFiles.writeFile(generateStream2(), fixedWidthFileAppend2, StandardOpenOption.APPEND);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.logFile(fixedWidthFile, new SystemOutLogger<>(
                new ShortMessage<>().append(" [", new JoinedTextsMessage<>(", ")).append("]")));
    }

    private static void test2(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test2---");

        List<FixedWidthFieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(new FixedWidthFieldSpec(0, 1, null, null));
        fieldSpecs.add(new FixedWidthFieldSpec(1, 3, Alignment.END, '.'));
        fieldSpecs.add(new FixedWidthFieldSpec(4, 6, Alignment.CENTER, '-'));
        fieldSpecs.add(new FixedWidthFieldSpec(15, 2, null, '#'));
        var fixedWidthFile =
                new FixedWidthFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        lineSeparator,
                        20,
                        false,
                        Alignment.START,
                        ' ',
                        fieldSpecs,
                        0,
                        0,
                        false,
                        false)
                        .file(path);

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeFile(TextRecordStreams.concat(generateStream1(), generateStream2()), fixedWidthFile);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.logFile(fixedWidthFile, new SystemOutLogger<>(
                new ShortMessage<>().append(" [", new JoinedTextsMessage<>(", ")).append("]")));
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
            test1(Path.of(args[0], "FixedWidthFile_1.txt"), LineSeparator.systemLineSeparator());
            test2(Path.of(args[0], "FixedWidthFile_2.txt"), LineSeparator.systemLineSeparator());
        } catch (ProducerException | IOException | ConsumerException e) {
            e.printStackTrace();
        }
    }

}
