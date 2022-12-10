package stexfires.examples.io;

import stexfires.examples.record.RecordSystemOutUtil;
import stexfires.io.RecordFiles;
import stexfires.io.WritableRecordFileSpec;
import stexfires.io.delimited.simple.SimpleDelimitedFieldSpec;
import stexfires.io.delimited.simple.SimpleDelimitedFileSpec;
import stexfires.io.singlevalue.SingleValueFileSpec;
import stexfires.record.TextRecord;
import stexfires.record.TextRecords;
import stexfires.record.ValueRecord;
import stexfires.record.consumer.ConsumerException;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.producer.ProducerException;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings({"CallToPrintStackTrace", "UseOfSystemOutOrSystemErr", "MagicNumber", "SpellCheckingInspection"})
public final class ExamplesSimpleDelimitedFile {

    private ExamplesSimpleDelimitedFile() {
    }

    @SuppressWarnings("UnnecessaryUnicodeEscape")
    private static Stream<TextRecord> generateStream() {
        return Stream.of(
                new ManyFieldsRecord(null, 0L, "a", "0", "AAAA", "ä"),
                new ManyFieldsRecord(null, 1L, "b", "1", "BB", "€"),
                new ManyFieldsRecord(null, 2L, "c", "2", "\uD83D\uDE00o\u0308", ""),
                new ManyFieldsRecord(null, 3L, "d", "3.33", "dDDDDd", null),
                new ManyFieldsRecord(null, 4L, "e", "004", "eEEEEEEEEEEEEEEEEEe", "    "),
                new ManyFieldsRecord(null, 5L, "f", null, "    ", "fff"),
                new ManyFieldsRecord(null, 6L, null, null, null, null),
                new ManyFieldsRecord(null, 7L, "", "", "", ""),
                new ManyFieldsRecord(null, 8L, "  ", "  ", "  ", "  "),
                new ManyFieldsRecord(null, 9L, "j", "9", "k", "l", "m", "n", "o"),
                TextRecords.empty(),
                new ManyFieldsRecord(null, 11L),
                new ManyFieldsRecord(null, 12L, "---")
        );
    }

    private static void test1(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test1---");

        List<SimpleDelimitedFieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        var fileSpec =
                new SimpleDelimitedFileSpec(
                        CharsetCoding.UTF_8_REPORTING,
                        lineSeparator,
                        "Column0,Column1,Column2,Column3",
                        WritableRecordFileSpec.DEFAULT_TEXT_AFTER,
                        ",",
                        fieldSpecs,
                        1,
                        0,
                        false,
                        false);

        // Write
        System.out.println("write: " + path);
        RecordFiles.writeStreamIntoFile(fileSpec, generateStream(), path);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.readAndConsumeFile(fileSpec, RecordSystemOutUtil.RECORD_CONSUMER, path);
    }

    private static void test2(Path path, LineSeparator lineSeparator) throws ProducerException, ConsumerException, IOException {
        System.out.println("-test2---");

        var singleValueFileWriteSpec =
                SingleValueFileSpec.write(
                        CharsetCoding.UTF_8_REPORTING,
                        lineSeparator,
                        SingleValueFileSpec.DEFAULT_LINE_PREFIX,
                        WritableRecordFileSpec.DEFAULT_TEXT_BEFORE,
                        WritableRecordFileSpec.DEFAULT_TEXT_AFTER,
                        false);

        // Write
        System.out.println("write (prepare read with SingleValueFile): " + path);
        Stream<ValueRecord> oneValueRecordStream = Stream.of(
                new ValueFieldRecord("Header"),
                new ValueFieldRecord("------"),
                new ValueFieldRecord("A1"),
                new ValueFieldRecord(""),
                new ValueFieldRecord(" "),
                new ValueFieldRecord("B1,B2"),
                new ValueFieldRecord(","),
                new ValueFieldRecord(" , "),
                new ValueFieldRecord("C1,C2,C3,"),
                new ValueFieldRecord(",,,"),
                new ValueFieldRecord(" , , ,"),
                new ValueFieldRecord("Footer")
        );
        RecordFiles.writeStreamIntoFile(singleValueFileWriteSpec, oneValueRecordStream, path);

        List<SimpleDelimitedFieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        fieldSpecs.add(new SimpleDelimitedFieldSpec());
        var simpleDelimitedFileSpec =
                SimpleDelimitedFileSpec.read(
                        CharsetCoding.UTF_8_REPORTING,
                        ",",
                        fieldSpecs,
                        2,
                        1,
                        true,
                        true);

        // Read / log
        System.out.println("read/log: " + path);
        RecordFiles.readAndConsumeFile(simpleDelimitedFileSpec, RecordSystemOutUtil.RECORD_CONSUMER, path);
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
            test1(Path.of(args[0], "SimpleDelimitedFile_1.csv"), LineSeparator.systemLineSeparator());
            test2(Path.of(args[0], "SimpleDelimitedFile_2.csv"), LineSeparator.systemLineSeparator());
        } catch (ProducerException | ConsumerException | IOException e) {
            e.printStackTrace();
        }
    }

}
